package com.perigea.tracker.timesheet.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.InfoAutoDto;
import com.perigea.tracker.commons.dto.TimesheetEntryDto;
import com.perigea.tracker.commons.dto.TimesheetEventDto;
import com.perigea.tracker.commons.dto.TimesheetRefDto;
import com.perigea.tracker.commons.dto.TimesheetResponseDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.dto.wrapper.TimesheetExcelWrapper;
import com.perigea.tracker.commons.enums.ApprovalStatus;
import com.perigea.tracker.commons.enums.EMese;
import com.perigea.tracker.commons.enums.RichiestaType;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.FestivitaException;
import com.perigea.tracker.commons.exception.TimesheetException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.approval.flow.TimesheetApprovalWorkflow;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.Personale;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.TimesheetDataRepository;
import com.perigea.tracker.timesheet.repository.TimesheetRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.search.Condition;
import com.perigea.tracker.timesheet.search.FilterFactory;
import com.perigea.tracker.timesheet.search.Operator;

@Service
@Transactional
public class TimesheetService {

	@Autowired
	private Logger logger;

	@Autowired
	private FilterFactory<TimesheetEntry> filter;

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private CommessaRepository commessaRepository;

	@Autowired
	private TimesheetRepository timesheetRepository;

	@Autowired
	private TimesheetDataRepository timesheetDataRepository;

	@Autowired
	private ApplicationDao applicationDao;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@Autowired
	private ExcelTimesheetService excelTimesheetService;

	@Autowired
	private TimesheetApprovalWorkflow timesheetApprovalWorkflow;

	/**
	 * @param timesheetDataList
	 * @param timeDto
	 * @return
	 */
	public Timesheet createTimesheet(List<TimesheetEntryDto> timesheetDataList, TimesheetRefDto timeDto) {
		try {
			TimesheetMensileKey tsKey = new TimesheetMensileKey(timeDto.getAnno(), timeDto.getMese(),
					timeDto.getCodicePersona());
			if (timesheetRepository.findById(tsKey).isPresent()) {
				return updateTimesheet(timesheetDataList, timeDto);
			}
			assertTimesheetIsValid(timesheetDataList, timeDto);
			Integer oreTotali = 0;
			Timesheet timesheet = dtoEntityMapper.dtoToEntity(timeDto);
			Utente utente = utenteRepository.findByCodicePersona(timeDto.getCodicePersona()).get();
			timesheet.setPersonale(utente.getPersonale());
			utente.getPersonale().addTimesheet(timesheet);

			timesheet.setId(tsKey);
			timesheet.setStatoRichiesta(ApprovalStatus.DRAFT);

			Map<TimesheetEntryKey, List<NotaSpese>> map = new HashMap<>();
			timesheetDataList.forEach(entry -> {
				entry.getNoteSpesa().forEach(r -> {
					TimesheetEntryKey entryKey = new TimesheetEntryKey(timesheet.getId().getAnno(),
							timesheet.getId().getMese(), entry.getGiorno(), timesheet.getId().getCodicePersona(),
							entry.getCodiceCommessa());
					NotaSpeseKey notaSpeseKey = new NotaSpeseKey(entryKey.getAnno(), entryKey.getMese(),
							entryKey.getGiorno(), entryKey.getCodicePersona(), entryKey.getCodiceCommessa(),
							r.getCostoNotaSpese());
					NotaSpese notaSpese = new NotaSpese();
					notaSpese.setId(notaSpeseKey);
					notaSpese.setImporto(r.getImporto());
					if (map.containsKey(entryKey)) {
						map.get(entryKey).add(notaSpese);
					} else {
						map.put(entryKey, new ArrayList<>());
						map.get(entryKey).add(notaSpese);
					}
				});
			});
			for (TimesheetEntryDto dataDto : timesheetDataList) {
				oreTotali += dataDto.getOre();
				Commessa commessa = commessaRepository.findByCodiceCommessa(dataDto.getCodiceCommessa()).get();
				TimesheetEntry entry = dtoEntityMapper.dtoToEntity(dataDto);
				TimesheetEntryKey entryKey = new TimesheetEntryKey(timesheet.getId().getAnno(),
						timesheet.getId().getMese(), dataDto.getGiorno(), timesheet.getId().getCodicePersona(),
						dataDto.getCodiceCommessa());
				entry.setId(entryKey);
				entry.setCommessa(commessa);
				entry.setTimesheet(timesheet);
				entry.setTipoCommessa(commessa.getTipoCommessa());
				timesheet.addTimesheet(entry);
				timesheet.setOreTotali(oreTotali);
				entry.setNoteSpesa(map.get(entryKey));
			}

			Richiesta approvalRequest = Richiesta.builder().richiedente(utente).tipo(RichiestaType.TIMESHEET).build();
			RichiestaHistory history = RichiestaHistory.builder().responsabile(utente.getPersonale().getResponsabile())
					.stato(ApprovalStatus.DRAFT).richiesta(approvalRequest).build();
			history.setRichiesta(approvalRequest);
			approvalRequest.addRichiestaHistory(history);
			approvalRequest.addTimesheet(timesheet);
			timesheet.setRichiesta(approvalRequest);
			timesheetRepository.save(timesheet);

			logger.info("Timesheet salvato");
			return timesheet;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public Timesheet getTimesheet(Integer anno, EMese mese, String codicePersona) {
		try {
			return timesheetRepository.findById(new TimesheetMensileKey(anno, mese.getMonthId(), codicePersona)).get();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public Timesheet getTimesheet(TimesheetMensileKey id) {
		try {
			return timesheetRepository.findById(id).get();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public TimesheetEntry getTimesheetEntry(TimesheetEntryKey id) {
		try {
			return timesheetDataRepository.findById(id).get();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public Specification<TimesheetEntry> entriesByDateAndCodicePersona(final Integer giorno, final Integer mese,
			final Integer anno, final String codicePersona) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(Condition.builder().field("id.giorno").value(giorno).valueType(Integer.class)
				.valueTo(TimesheetEntry.class).operator(Operator.eq).build());
		conditions.add(Condition.builder().field("id.mese").value(mese).valueType(Integer.class)
				.valueTo(TimesheetEntry.class).operator(Operator.eq).build());
		conditions.add(Condition.builder().field("id.anno").value(anno).valueType(Integer.class)
				.valueTo(TimesheetEntry.class).operator(Operator.eq).build());
		conditions.add(Condition.builder().field("id.codicePersona").value(giorno).valueType(String.class)
				.valueTo(TimesheetEntry.class).operator(Operator.eq).build());
		return filter.buildSpecification(conditions, false);
	}

	public List<TimesheetEntry> findAllBySpecification(Specification<TimesheetEntry> specification) {
		try {
			return timesheetDataRepository.findAll(specification);
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public List<TimesheetEntry> findByQueryNative(Integer giorno, Integer mese, Integer anno, String codicePersona) {
		try {
			return timesheetDataRepository.findAllByIdGiornoAndIdMeseAndIdAnnoAndIdCodicePersona(giorno, mese, anno,
					codicePersona);
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public Timesheet deleteTimesheet(Integer anno, EMese mese, String codicePersona) {
		try {
			Timesheet timesheet = getTimesheet(anno, mese, codicePersona);
			timesheet.getEntries().forEach(e -> e.getNoteSpesa().clear());
			timesheet.getEntries().clear();
			timesheetRepository.delete(timesheet);
			return timesheet;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public TimesheetEntry deleteTimesheetEntry(TimesheetEntry entry) {
		try {
			Timesheet timesheet = getTimesheet(new TimesheetMensileKey(entry.getId().getAnno(), entry.getId().getMese(),
					entry.getId().getCodicePersona()));
			List<TimesheetEntry> entries = timesheet.getEntries();
			if (entries.contains(entry)) {
				entries.remove(entry);
			}
			timesheetDataRepository.delete(entry);

			if (timesheet.getEntries().isEmpty()) {
				timesheetRepository.delete(timesheet);
			}
			return entry;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}

	}

	public Timesheet updateTimesheet(List<TimesheetEntryDto> timesheetDataList, TimesheetRefDto timeDto) {
		try {
			TimesheetMensileKey key = new TimesheetMensileKey(timeDto.getAnno(), timeDto.getMese(),
					timeDto.getCodicePersona());
			assertTimesheetIsValid(timesheetDataList, timeDto);
			Integer oreTotali = 0;
			Timesheet timesheet = getTimesheet(key);
			Utente utente = utenteRepository.findByCodicePersona(timeDto.getCodicePersona()).get();
			timesheet.setPersonale(utente.getPersonale());
			utente.getPersonale().addTimesheet(timesheet);
			TimesheetMensileKey tsKey = new TimesheetMensileKey(timeDto.getAnno(), timeDto.getMese(),
					timeDto.getCodicePersona());
			timesheet.setId(tsKey);

			Map<TimesheetEntryKey, List<NotaSpese>> map = new HashMap<>();
			timesheetDataList.forEach(entry -> {
				entry.getNoteSpesa().forEach(r -> {
					TimesheetEntryKey entryKey = new TimesheetEntryKey(timesheet.getId().getAnno(),
							timesheet.getId().getMese(), entry.getGiorno(), timesheet.getId().getCodicePersona(),
							entry.getCodiceCommessa());
					NotaSpeseKey notaSpeseKey = new NotaSpeseKey(entryKey.getAnno(), entryKey.getMese(),
							entryKey.getGiorno(), entryKey.getCodicePersona(), entryKey.getCodiceCommessa(),
							r.getCostoNotaSpese());
					NotaSpese notaSpese = new NotaSpese();
					notaSpese.setId(notaSpeseKey);
					notaSpese.setImporto(r.getImporto());
					if (map.containsKey(entryKey)) {
						map.get(entryKey).add(notaSpese);
					} else {
						map.put(entryKey, new ArrayList<>());
						map.get(entryKey).add(notaSpese);
					}
				});
			});
			for (TimesheetEntryDto dataDto : timesheetDataList) {
				oreTotali += dataDto.getOre();
				Commessa commessa = commessaRepository.findByCodiceCommessa(dataDto.getCodiceCommessa()).get();
				TimesheetEntry entry = dtoEntityMapper.dtoToEntity(dataDto);
				TimesheetEntryKey entryKey = new TimesheetEntryKey(timesheet.getId().getAnno(),
						timesheet.getId().getMese(), dataDto.getGiorno(), timesheet.getId().getCodicePersona(),
						dataDto.getCodiceCommessa());
				entry.setId(entryKey);
				entry.setCommessa(commessa);
				entry.setTimesheet(timesheet);
				entry.setTipoCommessa(commessa.getTipoCommessa());
				timesheet.addTimesheet(entry);
				timesheet.setOreTotali(oreTotali);
				entry.setNoteSpesa(map.get(entryKey));
			}

			timesheetRepository.save(timesheet);

			logger.info("Timesheet aggiornato");
			return timesheet;
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	public Boolean editTimesheetStatus(TimesheetEventDto timesheetEvent, ApprovalStatus newStatus) {
		try {
			TimesheetMensileKey key = new TimesheetMensileKey(timesheetEvent.getTimesheet().getAnno(),
					timesheetEvent.getTimesheet().getMese(), timesheetEvent.getTimesheet().getCodicePersona());
			if (applicationDao.updateTimesheetStatus(key, newStatus) == 1) {
				Timesheet timesheet = getTimesheet(key);

				Richiesta richiesta = timesheet.getRichiesta();
				RichiestaHistory history = RichiestaHistory.builder()
						.responsabile(timesheet.getPersonale().getResponsabile()).stato(newStatus).richiesta(richiesta)
						.build();

				timesheetEvent.setApprovalStatus(newStatus);

				timesheetApprovalWorkflow.approveTimesheet(timesheet, richiesta, history, timesheetEvent);
				return true;
			}
			return false;
		} catch (Exception ex) {
			throw new TimesheetException(ex.getMessage());
		}
	}

	public Boolean approveMultiTimesheet(List<TimesheetEventDto> events, ApprovalStatus newStatus) {
		try {
			Boolean statusUpdate = false;
			for (TimesheetEventDto event : events) {
				statusUpdate = editTimesheetStatus(event, newStatus);
			}
			return statusUpdate;
		} catch (Exception ex) {
			throw new TimesheetException(ex.getMessage());
		}
	}

	private void controlloFestivita(List<Festivita> festivi, TimesheetEntryDto timesheetData,
			TimesheetRefDto timesheetDto) {
		LocalDate data = LocalDate.of(timesheetDto.getAnno(), timesheetDto.getMese(), timesheetData.getGiorno());
		for (Festivita f : festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek() == DayOfWeek.SUNDAY
					|| data.getDayOfWeek() == DayOfWeek.SATURDAY) {
				throw new FestivitaException("Il giorno inserito non è corretto");
			}
		}
	}

	private void assertTimesheetIsValid(List<TimesheetEntryDto> timesheetDataList, TimesheetRefDto timesheetDto)
			throws TimesheetException {
		List<Festivita> festivi = festivitaRepository.findAll();
		Map<Integer, List<TimesheetEntryDto>> dataMap = new HashMap<Integer, List<TimesheetEntryDto>>();
		timesheetDataList.forEach(r -> {
			Integer giorno = r.getGiorno();
			if (dataMap.containsKey(giorno)) {
				dataMap.get(giorno).add(r);
			} else {
				dataMap.put(giorno, new ArrayList<>());
				dataMap.get(giorno).add(r);
			}
		});
		for (Integer key : dataMap.keySet()) {
			List<TimesheetEntryDto> list = dataMap.get(key);
			int oreGiorno = 0;
			for (TimesheetEntryDto dto : list) {
				oreGiorno = oreGiorno + dto.getOre();
				controlloFestivita(festivi, dto, timesheetDto);
			}
			if (oreGiorno > 8) {
				throw new TimesheetException("numero ore giornaliere inserite non valido");
			}
		}
	}
	/**
	 * download di un dile excel relativo al timesheet mensile di un utente
	 * @param anno
	 * @param mese
	 * @param angrafica
	 * @param infoAuto
	 * @return
	 */
	public byte[] downloadExcelTimesheet(Integer anno, EMese mese, UtenteDto angrafica, InfoAutoDto infoAuto) {
		Timesheet timesheet = getTimesheet(anno, mese, angrafica.getCodicePersona());
		TimesheetResponseDto timesheetResponseDto = dtoEntityMapper.entityToDto(timesheet);
		TimesheetExcelWrapper timesheetExcelWrapper = new TimesheetExcelWrapper(timesheetResponseDto, angrafica,
				infoAuto);
		return excelTimesheetService.createExcelTimesheet(timesheetExcelWrapper);
	}

	public Map<String, byte[]> getExcelTimesheetsMap(Integer anno, Integer mese) {
		try {
			Map<String, byte[]> excelTimesheetsMap = new HashMap<String, byte[]>();
			List<Timesheet> timesheets = timesheetRepository.findAllByIdAnnoAndIdMese(anno, mese);
			for (Timesheet timesheet : timesheets) {
				String username = timesheet.getPersonale().getUtente().getUsername();
				String filename = username + "_timesheet" + Utils.EXCEL_EXT;
				UtenteDto utenteDto = dtoEntityMapper.entityToDto(timesheet.getPersonale().getUtente());
				InfoAutoDto infoAuto = getInfoAuto(timesheet.getPersonale().getUtente());
				byte[] bArray = downloadExcelTimesheet(anno, EMese.getByMonthId(mese), utenteDto, infoAuto);
				excelTimesheetsMap.put(filename, bArray);
			}
			return excelTimesheetsMap;
		} catch (Exception e) {
			throw new TimesheetException(e.getMessage());
		}
	}
	
	public Map<String, byte[]> getExcelTimesheetsMap(Integer anno, Integer mese, List<Personale> sottoposti) {
		try {
			Map<String, byte[]> excelTimesheetsMap = new HashMap<String, byte[]>();
			for (Personale p : sottoposti) {
				if (timesheetRepository.findById(new TimesheetMensileKey(anno, mese, p.getCodicePersona()))
						.isPresent()) {
					String username = p.getUtente().getUsername();
					String filename = username + "_timesheet" + Utils.EXCEL_EXT;
					UtenteDto utenteDto = dtoEntityMapper.entityToDto(p.getUtente());
					InfoAutoDto infoAuto = getInfoAuto(p.getUtente());
					byte[] bArray = downloadExcelTimesheet(anno, EMese.getByMonthId(mese), utenteDto, infoAuto);
					excelTimesheetsMap.put(filename, bArray);
				}
			}
			return excelTimesheetsMap;
		} catch (Exception e) {
			throw new TimesheetException(e.getMessage());
		}
	}
	
	
	
	
		
	public InfoAutoDto getInfoAuto(Utente utente) {
		InfoAutoDto infoAuto = null;
		if (utente.getPersonale().getClass().isAssignableFrom(Dipendente.class)) {
			Dipendente dipendente = (Dipendente) utente.getPersonale();
			DatiEconomiciDipendente economics = dipendente.getEconomics();
			infoAuto = new InfoAutoDto(economics.getModelloAuto(), economics.getRimborsoPerKm(),
					economics.getKmPerGiorno());
		} else if (utente.getPersonale().getClass().isAssignableFrom(Consulente.class)) {
			infoAuto = new InfoAutoDto("", 0.0f, 0.0f);
		} else {
			throw new TimesheetException("Tipo utente non valido");
		}
		return infoAuto;
	}

}