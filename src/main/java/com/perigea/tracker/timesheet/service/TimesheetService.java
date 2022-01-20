package com.perigea.tracker.timesheet.service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;



import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.TimesheetEntryDto;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.enums.EMese;
import com.perigea.tracker.timesheet.enums.StatoRichiestaType;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.exception.TimeSheetException;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.NotaSpeseRepository;
import com.perigea.tracker.timesheet.repository.TimesheetRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@Service
public class TimesheetService {

	@Autowired
	private Logger logger;

//	@Autowired
//	private TimesheetDataRepository timesheetDataRepository;

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private CommessaRepository commessaRepository;

	@Autowired
	private TimesheetRepository timesheetRepository;
	
	@Autowired
	private NotaSpeseRepository notaSpeseRepository;

	@Transactional
	public Timesheet createTimesheet(List<TimesheetEntryDto> timesheetDataList, TimesheetInputDto timeDto) {
		try {
			assertTimesheetIsValid(timesheetDataList, timeDto);
			Integer oreTotali = 0;
			Timesheet timesheet = DtoEntityMapper.INSTANCE.fromDtoToEntityMensile(timeDto);
			Utente utente = utenteRepository.findByCodicePersona(timeDto.getCodicePersona());
			timesheet.setUtente(utente);
			utente.addTimesheet(timesheet);
			TimesheetMensileKey tsKey = new TimesheetMensileKey(timeDto.getAnno(), timeDto.getMese(), timeDto.getCodicePersona());
			timesheet.setId(tsKey);
			timesheet.setStatoRichiesta(StatoRichiestaType.I);

			Map<TimesheetEntryKey, List<NotaSpese>> map = new HashMap<>();
			timesheetDataList.forEach(entry-> {
				entry.getExpenseReport().forEach(r-> {
					TimesheetEntryKey entryKey = new TimesheetEntryKey(timesheet.getId().getAnno(), timesheet.getId().getMese(), entry.getGiorno(), timesheet.getId().getCodicePersona(), entry.getCodiceCommessa());
					NotaSpeseKey notaSpeseKey=new NotaSpeseKey(entryKey.getAnno(),entryKey.getMese(),entryKey.getGiorno(),entryKey.getCodicePersona(),entryKey.getCodiceCommessa(),r.getCostoNotaSpese());
					NotaSpese notaSpese = new NotaSpese();
					notaSpese.setId(notaSpeseKey);
					notaSpese.setImporto(r.getImporto());
					if(map.containsKey(entryKey)) {
						map.get(entryKey).add(notaSpese);
					} else {
						map.put(entryKey, new ArrayList<>());
						map.get(entryKey).add(notaSpese);
					}
				});
			});
			for (TimesheetEntryDto dataDto : timesheetDataList) {
				oreTotali += dataDto.getOre();
				Commessa commessa = commessaRepository.findByCodiceCommessa(dataDto.getCodiceCommessa());
				TimesheetEntry entry = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(dataDto);
				TimesheetEntryKey entryKey = new TimesheetEntryKey(timesheet.getId().getAnno(), timesheet.getId().getMese(), dataDto.getGiorno(), timesheet.getId().getCodicePersona(), dataDto.getCodiceCommessa());
				entry.setId(entryKey);
				entry.setCommessa(commessa);
				entry.setTimesheet(timesheet);
				entry.setTipoCommessa(commessa.getTipoCommessa());
				timesheet.addTimesheet(entry);
				timesheet.setOreTotali(oreTotali);
				entry.setNotaSpese(map.get(entryKey));	
			}
			timesheetRepository.save(timesheet);
			return timesheet;
		} catch (Exception e) {
			throw new TimeSheetException(e.getMessage());
		}
	}
	
	public Timesheet getTimesheet(Integer anno, EMese mese, String codicePersona) {
		try {
			Optional<Timesheet> optTimesheet = timesheetRepository.findById(new TimesheetMensileKey(anno, mese.getMonthId(), codicePersona));
			Timesheet timesheet = optTimesheet.get();
			return timesheet;
		} catch(Exception e) {
			throw new TimeSheetException(e.getMessage());
		}
	}
	
	public Timesheet deleteTimesheet(Integer anno, EMese mese, String codicePersona) {
		try {
			Optional<Timesheet> optTimeSheet = timesheetRepository.findById(new TimesheetMensileKey(anno, mese.getMonthId(), codicePersona));
			Timesheet timesheet = optTimeSheet.get();
			timesheetRepository.delete(timesheet);
			return timesheet;
		}catch(Exception ex) {
			throw new TimeSheetException(ex.getMessage());
		}
	}

//
//	public TimeSheetDataDto editTimeSheet(TimeSheetDataDto timeSheetParam, Commessa commessa, Utente utente) {
//		try {
//			TimeSheetData timeSheetEntity=timesheetRepository.findByUtenteTimesheet(utente);
//			if(timeSheetEntity != null) {
//				timesheetRepository.delete(timeSheetEntity);
//				timeSheetEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
//				timeSheetEntity.setUtenteTimesheet(utente);
//				timeSheetEntity.setCommessaTimesheet(commessa);
//				timesheetRepository.save(timeSheetEntity);
//				logger.info("Timesheet modificato");
//			}
//			TimeSheetDataDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
//			return dto;
//		}catch(Exception ex) {
//			throw new EntityNotFoundException("Timesheet non trovato "+ ex.getMessage());	
//		}
//	}
//	
	public void editStatusTimeSheet(TimesheetEntryDto timeSheetParam) {
		// if(mapEditUser.containsKey(key)) {
		// TimeSheet
		// timeSheetEntity=timeSheetRepo.findByCodicePersona(timeSheetParam.getCodiceCommessa());
		// if(timeSheetEntity != null) {
		// timeSheetEntity.setStatoType(timeSheetParam.getStatoType().toString());
		// timeSheetRepo.save(timeSheetEntity);
		// } else {
		// LOGGER.info("CodicePersona non trovato");
		// }
		// } else {
		// LOGGER.info("CreateUser non trovato");
		// }
	}

	public void controlloFestivita(List<Festivita> festivi, TimesheetEntryDto timesheetData, TimesheetInputDto timesheetDto) {
		LocalDate data = LocalDate.of(timesheetDto.getAnno(), timesheetDto.getMese(), timesheetData.getGiorno());
		for (Festivita f : festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek() == DayOfWeek.SUNDAY
					|| data.getDayOfWeek() == DayOfWeek.SATURDAY) {
				throw new FestivitaException("Il giorno inserito non è corretto");
			}
		}
	}
	
	private void assertTimesheetIsValid(List<TimesheetEntryDto> timesheetDataList, TimesheetInputDto timesheetDto) throws TimeSheetException {
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
				controlloFestivita(festivi, dto,timesheetDto);
			}
			if(oreGiorno>8) {
				throw new TimeSheetException("numero ore giornaliere inserite non valido");
			}
		}
	}
	
}