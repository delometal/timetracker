package com.perigea.tracker.timesheet.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import com.perigea.tracker.timesheet.repository.NotaSpeseRepository;
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

	@Autowired
	private FilterFactory<TimesheetEntry> filter;

	@Autowired
	private FilterFactory<Timesheet> timesheetFilter;

	@Autowired
	private NotaSpeseRepository notaSpeseRepository;

	/**
	 * creazione del timesheet
	 * 
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
			Utente utente = utenteRepository.findById(timeDto.getCodicePersona()).orElseThrow();
			List<TimesheetEntryDto> assunsioneDimissioneEntries = getAssunzioneDimissioneEntries(utente, timeDto);
			timesheetDataList.addAll(assunsioneDimissioneEntries);
			List<TimesheetEntryDto> validEntries = assertTimesheetIsValid(timesheetDataList, timeDto);
			Integer oreTotali = 0;
			Timesheet timesheet = dtoEntityMapper.dtoToEntity(timeDto);
						timesheet.setPersonale(utente.getPersonale());
			utente.getPersonale().addTimesheet(timesheet);

			timesheet.setId(tsKey);
			timesheet.setStatoRichiesta(ApprovalStatus.DRAFT);

			Map<TimesheetEntryKey, List<NotaSpese>> map = new HashMap<>();
			validEntries.forEach(entry -> {
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
			for (TimesheetEntryDto dataDto : validEntries) {
				oreTotali += dataDto.getOre();
				Commessa commessa = commessaRepository.findByCodiceCommessa(dataDto.getCodiceCommessa()).orElseThrow();
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
	
	

	/**
	 * ricerca di un timesheet mensile attraverso anno, mese e giorno
	 * 
	 * @param anno
	 * @param mese
	 * @param codicePersona
	 * @return
	 */
	public Timesheet getTimesheet(Integer anno, Integer mese, String codicePersona) {
		try {
			return timesheetRepository.findById(new TimesheetMensileKey(anno, mese, codicePersona)).orElseThrow();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}
	
	/**
	 * ricerca di tutti i timesheet dei sottoposti di un responsabile
	 * @param anno
	 * @param mese
	 * @param sottoposti
	 * @return
	 */
	public List<Timesheet> getTimesheetsByResponabile(Integer anno, Integer mese, List<Personale> sottoposti) {
		List<Timesheet> timesheets = new ArrayList<Timesheet>();
		for (Personale p : sottoposti) {
			Optional<Timesheet> optionalTimesheet = timesheetRepository
					.findById(new TimesheetMensileKey(anno, mese, p.getCodicePersona()));
			if (optionalTimesheet.isPresent()) {
				Timesheet timesheet = optionalTimesheet.get();
				timesheets.add(timesheet);
			}
		}
		return timesheets;
	}

	/**
	 * ricerca di un timesheet mensile attraverso la composite key
	 * 
	 * @param id
	 * @return
	 */
	public Timesheet getTimesheet(TimesheetMensileKey id) {
		try {
			return timesheetRepository.findById(id).orElseThrow();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}
	
	/**
	 * ricerca di tutti i timesheet
	 * @return
	 */
	public List<Timesheet> getAllTimesheet() {
		try {
			return timesheetRepository.findAll();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	/**
	 * ricerca di un singolo dato di un timesheet
	 * 
	 * @param id
	 * @return
	 */
	public TimesheetEntry getTimesheetEntry(TimesheetEntryKey id) {
		try {
			return timesheetDataRepository.findById(id).orElseThrow();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	/**
	 * ricerca di singoli dati attraverso una query nativa
	 * 
	 * @param giorno
	 * @param mese
	 * @param anno
	 * @param codicePersona
	 * @return
	 */
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

	/**
	 * ricerca di tutti i timesheet in un anno di un singolo utente
	 * 
	 * @param anno
	 * @param codicePersona
	 * @return
	 */
	public List<Timesheet> findAllByAnnoAndCodicePersona(Integer anno, String codicePersona) {
		try {
			return timesheetRepository.findAll(timesheetsBySpecification(anno, codicePersona));
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new TimesheetException(ex.getMessage());
		}
	}

	private Specification<Timesheet> timesheetsBySpecification(final Integer anno, final String codicePersona) {
		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("id.anno").value(anno).valueType(Integer.class).operator(Operator.eq)
				.build());
		conditions.add(Condition.builder().field("id.codicePersona").value(codicePersona).valueType(String.class)
				.operator(Operator.eq).build());
		return timesheetFilter.buildSpecification(conditions, false);
	}

	/**
	 * delete del timesheet mensile
	 * 
	 * @param anno
	 * @param mese
	 * @param codicePersona
	 * @return
	 */
	public Timesheet deleteTimesheet(Integer anno, Integer mese, String codicePersona) {
		try {
			Timesheet timesheet = getTimesheet(anno, mese, codicePersona);
			timesheet.getEntries().forEach(e -> notaSpeseRepository.deleteAll(e.getNoteSpesa()));
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

	/**
	 * delete di una singolo dato e dell'intero timesheet se in esso non ci sono più
	 * dati
	 * 
	 * @param entry
	 * @return
	 */
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

	/**
	 * update di un timesheet mensile
	 * 
	 * @param timesheetDataList
	 * @param timeDto
	 * @return
	 */
	public Timesheet updateTimesheet(List<TimesheetEntryDto> timesheetDataList, TimesheetRefDto timeDto) {
		try {
			TimesheetMensileKey key = new TimesheetMensileKey(timeDto.getAnno(), timeDto.getMese(),
					timeDto.getCodicePersona());
			assertTimesheetIsValid(timesheetDataList, timeDto);
			Integer oreTotali = 0;
			Timesheet timesheet = getTimesheet(key);
			Utente utente = utenteRepository.findByCodicePersona(timeDto.getCodicePersona()).orElseThrow();
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
					NotaSpese notaSpese;
					Optional<NotaSpese> notaOpt = notaSpeseRepository.findById(notaSpeseKey);
					if (notaOpt.isEmpty()) {
						notaSpese = new NotaSpese();
						notaSpese.setId(notaSpeseKey);
						notaSpese.setImporto(r.getImporto());
					} else {
						notaSpese = notaOpt.get();
					}
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
				Commessa commessa = commessaRepository.findByCodiceCommessa(dataDto.getCodiceCommessa()).orElseThrow();
				TimesheetEntryKey entryKey = new TimesheetEntryKey(timesheet.getId().getAnno(),
						timesheet.getId().getMese(), dataDto.getGiorno(), timesheet.getId().getCodicePersona(),
						dataDto.getCodiceCommessa());
				Optional<TimesheetEntry> entryOpt = timesheetDataRepository.findById(entryKey);
				if (entryOpt.isPresent()) {
					TimesheetEntry entry = entryOpt.get();

					entry.setOre(dataDto.getOre());
					entry.setTrasferta(dataDto.getTrasferta());
					entry.setId(entryKey);
					entry.setCommessa(commessa);
					entry.setTimesheet(timesheet);
					entry.setTipoCommessa(commessa.getTipoCommessa());
					timesheet.addTimesheet(entry);
					timesheet.setOreTotali(oreTotali);
					entry.setNoteSpesa(map.get(entryKey));
				} else {
					TimesheetEntry entry = dtoEntityMapper.dtoToEntity(dataDto);
					entry.setId(entryKey);
					entry.setCommessa(commessa);
					entry.setTimesheet(timesheet);
					entry.setTipoCommessa(commessa.getTipoCommessa());
					timesheet.addTimesheet(entry);
					timesheet.setOreTotali(oreTotali);
					entry.setNoteSpesa(map.get(entryKey));
				}
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

	/**
	 * metodo di approvazione di un singolo timesheet e workflow per l'invio della
	 * notifica all'utente
	 * 
	 * @param timesheetEvent
	 * @param newStatus
	 * @return
	 */
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

	/**
	 * metodo per l'approvazione di una lista di timesheet e invio delle notifiche
	 * ai singoli utenti
	 * 
	 * @param events
	 * @param newStatus
	 * @return
	 */
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

	/**
	 * metodo per il controllo delle festività
	 * 
	 * @param festivi
	 * @param timesheetData
	 * @param timesheetDto
	 */
	private boolean controlloFestivita(List<Festivita> festivi, TimesheetEntryDto timesheetData,
			TimesheetRefDto timesheetDto) {
		LocalDate data = LocalDate.of(timesheetDto.getAnno(), timesheetDto.getMese(), timesheetData.getGiorno());
		for (Festivita f : festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek() == DayOfWeek.SUNDAY
					|| data.getDayOfWeek() == DayOfWeek.SATURDAY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * metodo per il controllo della validità dei dati che vengono inseriti nel
	 * timesheet
	 * 
	 * @param timesheetDataList
	 * @param timesheetDto
	 * @throws TimesheetException
	 */
	private List<TimesheetEntryDto> assertTimesheetIsValid(List<TimesheetEntryDto> timesheetDataList, TimesheetRefDto timesheetDto)
			throws TimesheetException {
		List<TimesheetEntryDto> controlledList = new ArrayList<TimesheetEntryDto>();
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
				if(controlloFestivita(festivi, dto, timesheetDto)) {
					dto.setStraordinario(true);
				}
				controlledList.add(dto);
			}
			if (oreGiorno > 12) {
				throw new TimesheetException(String.format("numero ore giornaliere inserite non valido per il giorno %s", key));
			}
		}
		return controlledList;
	}
	
	/**
	 * metodo per il popolamento delle entries in base alle date di assunzione e dimissione
	 * @param utente
	 * @param refs
	 * @return
	 */
	private List<TimesheetEntryDto> getAssunzioneDimissioneEntries(Utente utente, TimesheetRefDto refs) {
		List<TimesheetEntryDto> list = new ArrayList<>();
		Commessa assunzione = commessaRepository.findByCodiceCommessa("c36e87a1-4af9-4a3b-b1a8-79490fe20082")
				.orElseThrow();
		Commessa dimissione = commessaRepository.findByCodiceCommessa("da8fbeff-fce6-4c7c-989d-95ec03c7dd92")
				.orElseThrow();
		Personale user = utente.getPersonale();
		LocalDate dataAssunzione = user.getDataAssunzione();
		LocalDate dataCessazione = user.getDataCessazione();
		if (refs.getMese() == dataAssunzione.getMonthValue() && refs.getAnno() == dataAssunzione.getYear()) {
			for (int i = 1; i < dataAssunzione.getDayOfMonth(); i++) {
				LocalDate date = LocalDate.of(refs.getAnno(), refs.getMese(), i);
				if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {	
				TimesheetEntryDto entryDto = TimesheetEntryDto.builder()
						.codiceCommessa(assunzione.getCodiceCommessa())
						.giorno(i)
						.ore(8)
						.trasferta(false)
						.tipoCommessa(assunzione.getTipoCommessa())
						.descrizioneCommessa(assunzione.getDescrizioneCommessa())
						.ragioneSociale(assunzione.getCliente().getRagioneSociale())
						.build();
				list.add(entryDto);
				}
			}			
		}
		if(dataCessazione != null && refs.getMese() == dataCessazione.getMonthValue() && refs.getAnno() == dataCessazione.getYear()) {
			for(int i=dataCessazione.getDayOfMonth()+1; i<EMese.getDays(refs.getMese(), refs.getAnno()); i++) {
				LocalDate date = LocalDate.of(refs.getAnno(), refs.getMese(), i);
				if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {	
				TimesheetEntryDto entryDto = TimesheetEntryDto.builder()
						.codiceCommessa(dimissione.getCodiceCommessa())
						.giorno(i)
						.ore(8)
						.trasferta(false)
						.tipoCommessa(dimissione.getTipoCommessa())
						.descrizioneCommessa(dimissione.getDescrizioneCommessa())
						.ragioneSociale(dimissione.getCliente().getRagioneSociale())
						.build();
				list.add(entryDto);
				} 
			}
		}
		return list;
	}
	
	/**
	 * metodo per il controllo della completezza del timesheet prima della richiesta
	 * @param list
	 * @param refs
	 */
	public void assertTimesheetIsComplete(List<TimesheetEntry> list, TimesheetRefDto refs) {
		Map<Integer, List<TimesheetEntry>> dataMap = new HashMap<Integer, List<TimesheetEntry>>();
		list.forEach(r -> {
			Integer giorno = r.getId().getGiorno();
			if (dataMap.containsKey(giorno)) {
				dataMap.get(giorno).add(r);
			} else {
				dataMap.put(giorno, new ArrayList<>());
				dataMap.get(giorno).add(r);
			}
		});
		List<LocalDate> giorniLavorativi = getGiorniLavorativi(refs.getMese(), refs.getAnno());
		giorniLavorativi.forEach(day -> {
			if(!dataMap.containsKey(day.getDayOfMonth())) {
				throw new TimesheetException(String.format("il timesheet non è completo mancano i dati del giorno %s", day.getDayOfMonth()));
			}
		});			
		}		
	
		/**
		 * metodo per ottenere i giorni lavorativi in un mese
		 * @param mese
		 * @param anno
		 * @return
		 */
		private List<LocalDate> getGiorniLavorativi(Integer mese, Integer anno) {
			List<LocalDate> giorniLavorativi = new ArrayList<LocalDate>();
			List<Festivita> festivi = festivitaRepository.findAll();
			Boolean festivo = false;
			for (int i = 1; i <= EMese.getDays(mese, anno); i++) {
				LocalDate data = LocalDate.of(anno, mese, i);
				for (Festivita f : festivi) {
					if (f.getData().isEqual(data) || data.getDayOfWeek() == DayOfWeek.SUNDAY
							|| data.getDayOfWeek() == DayOfWeek.SATURDAY) {
						festivo = true;
						break;
					}
				}
				if (festivo.equals(false)) {
					giorniLavorativi.add(data);
				}
			}
			return giorniLavorativi;
		}

	/**
	 * download di un file excel relativo al timesheet mensile di un utente
	 * 
	 * @param anno
	 * @param mese
	 * @param angrafica
	 * @param infoAuto
	 * @return
	 */
	public byte[] downloadExcelTimesheet(Integer anno, Integer mese, UtenteDto angrafica, InfoAutoDto infoAuto) {
		Timesheet timesheet = getTimesheet(anno, mese, angrafica.getCodicePersona());
		TimesheetExcelWrapper timesheetExcelWrapper = getExcelWrapper(timesheet, angrafica, infoAuto);
		return excelTimesheetService.createExcelTimesheet(timesheetExcelWrapper);
	}

	/**
	 * generazione di una mappa di chiave filename e di valore l'excel del timesheet
	 * relativo ad un utente, a partire da una lista di timesheet relativi allo
	 * stesso mese
	 * 
	 * @param anno
	 * @param mese
	 * @return
	 */
	public Map<String, byte[]> getExcelTimesheetsMap(Integer anno, Integer mese) {
		try {
			Map<String, byte[]> excelTimesheetsMap = new HashMap<String, byte[]>();
			List<Timesheet> timesheets = timesheetRepository.findAllByIdAnnoAndIdMese(anno, mese);
			for (Timesheet timesheet : timesheets) {
				String username = timesheet.getPersonale().getUtente().getUsername();
				String filename = username + "_timesheet" + Utils.EXCEL_EXT;
				UtenteDto utenteDto = dtoEntityMapper.entityToDto(timesheet.getPersonale().getUtente());
				InfoAutoDto infoAuto = getInfoAuto(timesheet.getPersonale().getUtente());
				byte[] bArray = downloadExcelTimesheet(anno, mese, utenteDto, infoAuto);
				excelTimesheetsMap.put(filename, bArray);
			}
			return excelTimesheetsMap;
		} catch (Exception e) {
			throw new TimesheetException(e.getMessage());
		}
	}

	/**
	 * generazione di una mappa di chiave filename e di valore l'excel del timesheet
	 * relativo ad un utente, a partire da una lista di timesheet di un anno
	 * relativi allo stesso utente
	 * 
	 * @param anno
	 * @param codicePersona
	 * @return
	 */
	public Map<String, byte[]> getExcelTimesheetsMap(Integer anno, String codicePersona) {
		try {
			Map<String, byte[]> excelTimesheetsMap = new HashMap<String, byte[]>();
			List<Timesheet> timesheets = timesheetRepository.findAllByIdAnnoAndIdCodicePersona(anno, codicePersona);
			for (Timesheet timesheet : timesheets) {
				String username = timesheet.getPersonale().getUtente().getUsername();
				String filename = username + "_timesheet" + Utils.EXCEL_EXT;
				UtenteDto utenteDto = dtoEntityMapper.entityToDto(timesheet.getPersonale().getUtente());
				InfoAutoDto infoAuto = getInfoAuto(timesheet.getPersonale().getUtente());
				byte[] bArray = downloadExcelTimesheet(anno, timesheet.getId().getMese(), utenteDto, infoAuto);
				excelTimesheetsMap.put(filename, bArray);
			}
			return excelTimesheetsMap;
		} catch (Exception e) {
			throw new TimesheetException(e.getMessage());
		}
	}

	/**
	 * generazione di una mappa di chiave filename e di valore l'excel del timesheet
	 * relativo ad un utente, a partire da una lista di utenti che hanno tutti lo
	 * stesso responsabile
	 * 
	 * @param anno
	 * @param mese
	 * @param sottoposti
	 * @return
	 */
	public Map<String, byte[]> getExcelTimesheetsMap(Integer anno, Integer mese, List<Personale> sottoposti) {
		try {
			Map<String, byte[]> excelTimesheetsMap = new HashMap<String, byte[]>();
			for (Personale p : sottoposti) {
				Optional<Timesheet> optionalTimesheet = timesheetRepository
						.findById(new TimesheetMensileKey(anno, mese, p.getCodicePersona()));
				if (optionalTimesheet.isPresent()) {
					Timesheet timesheet = optionalTimesheet.get();
					String username = p.getUtente().getUsername();
					String filename = username + "_timesheet" + Utils.EXCEL_EXT;
					UtenteDto utenteDto = dtoEntityMapper.entityToDto(p.getUtente());
					InfoAutoDto infoAuto = getInfoAuto(p.getUtente());
					TimesheetExcelWrapper timesheetExcelWrapper = getExcelWrapper(timesheet, utenteDto, infoAuto);
					byte[] bArray = excelTimesheetService.createExcelTimesheet(timesheetExcelWrapper);
					excelTimesheetsMap.put(filename, bArray);
				} else {
					logger.info("Timesheet relativo al'utente non presente");
				}
			}
			return excelTimesheetsMap;
		} catch (Exception e) {
			throw new TimesheetException(e.getMessage());
		}
	}

	/**
	 * metodo per la creazione delle InfoAuto relative ad un Dipendente/Consulente
	 * 
	 * @param utente
	 * @return
	 */
	public InfoAutoDto getInfoAuto(Utente utente) {
		InfoAutoDto infoAuto = null;
		if (utente.getPersonale().getClass().isAssignableFrom(Dipendente.class)) {
			Dipendente dipendente = (Dipendente) utente.getPersonale();
			DatiEconomiciDipendente economics = dipendente.getEconomics();
			if (economics==null) {
				infoAuto = new InfoAutoDto("", 0.0f, 0.0f);
			} else {
				infoAuto = new InfoAutoDto(economics.getModelloAuto(), economics.getRimborsoPerKm(),
						economics.getKmPerGiorno());
			}
		} else if (utente.getPersonale().getClass().isAssignableFrom(Consulente.class)) {
			infoAuto = new InfoAutoDto("", 0.0f, 0.0f);
		} else {
			throw new TimesheetException("Tipo utente non valido");
		}
		return infoAuto;
	}

	/**
	 * metodo per creare un timesheetExcelWrapper
	 * 
	 * @param timesheet
	 * @return
	 */
	public TimesheetExcelWrapper getExcelWrapper(Timesheet timesheet, UtenteDto utenteDto, InfoAutoDto infoAuto) {
		TimesheetResponseDto timesheetResponseDto = dtoEntityMapper.entityToDto(timesheet);
		return new TimesheetExcelWrapper(timesheetResponseDto, utenteDto, infoAuto);
	}

	/**
	 * metodo per ottenere le ore totali lavorate per una commessa in un mese
	 * 
	 * @param codiceCommessa
	 * @param anno
	 * @param mese
	 * @param codicePersona
	 * @return
	 */
	public Integer getOreTotaliPerCommessa(String codiceCommessa, Integer anno, Integer mese, String codicePersona) {
		try {
			List<TimesheetEntry> entries = timesheetDataRepository
					.findAll(entriesBySpecification(anno, mese, codiceCommessa, codicePersona));
			Integer oreTotali = 0;
			for (TimesheetEntry entry : entries) {
				oreTotali += entry.getOre();
			}
			return oreTotali;
		} catch (Exception e) {
			throw new TimesheetException(e.getMessage());
		}
	}

	private Specification<TimesheetEntry> entriesBySpecification(final Integer anno, final Integer mese,
			final String codiceCommessa, final String codicePersona) {

		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("id.anno").value(anno).valueType(Integer.class).operator(Operator.eq)
				.build());
		conditions.add(Condition.builder().field("id.mese").value(mese).valueType(Integer.class).operator(Operator.eq)
				.build());
		conditions.add(Condition.builder().field("id.codiceCommessa").value(codiceCommessa).valueType(String.class)
				.operator(Operator.eq).build());
		conditions.add(Condition.builder().field("id.codicePersona").value(codicePersona).valueType(String.class)
				.operator(Operator.eq).build());
		return filter.buildSpecification(conditions, false);
	}

	/**
	 * metodo per ottenere le ore totali lavorate per una commessa in un anno
	 * 
	 * @param codiceCommessa
	 * @param anno
	 * @param codicePersona
	 * @return
	 */
	public Integer getOreTotaliPerCommessa(String codiceCommessa, Integer anno, String codicePersona) {
		try {
			List<TimesheetEntry> entries = timesheetDataRepository
					.findAll(entriesBySpecification(anno, codiceCommessa, codicePersona));
			Integer oreTotali = 0;
			for (TimesheetEntry entry : entries) {
				oreTotali += entry.getOre();
			}
			return oreTotali;
		} catch (Exception e) {
			throw new TimesheetException(e.getMessage());
		}
	}

	private Specification<TimesheetEntry> entriesBySpecification(final Integer anno, final String codiceCommessa,
			final String codicePersona) {

		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("id.anno").value(anno).valueType(Integer.class).operator(Operator.eq)
				.build());
		conditions.add(Condition.builder().field("id.codiceCommessa").value(codiceCommessa).valueType(String.class)
				.operator(Operator.eq).build());
		conditions.add(Condition.builder().field("id.codicePersona").value(codicePersona).valueType(String.class)
				.operator(Operator.eq).build());
		return filter.buildSpecification(conditions, false);
	}

}