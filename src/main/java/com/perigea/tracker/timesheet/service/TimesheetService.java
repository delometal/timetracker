package com.perigea.tracker.timesheet.service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.TimesheetEntryDto;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.enums.EMese;
import com.perigea.tracker.timesheet.enums.StatoRichiestaType;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.exception.TimeSheetException;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.TimesheetRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@Service
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
	private ApplicationDao applicationDao;
	
	@Transactional
	public Timesheet createTimesheet(List<TimesheetEntryDto> timesheetDataList, TimesheetInputDto timeDto, List<NotaSpeseDto> notaSpeseList) {
		try {
			Integer oreTotali = 0;
			Timesheet timesheet = DtoEntityMapper.INSTANCE.fromDtoToEntityMensile(timeDto);
			Utente utente = utenteRepository.findByCodicePersona(timeDto.getCodicePersona());
			timesheet.setUtente(utente);
			utente.addTimesheet(timesheet);
			TimesheetMensileKey tsKey = new TimesheetMensileKey(timeDto.getAnno(), timeDto.getMese(), timeDto.getCodicePersona());
			timesheet.setId(tsKey);
			timesheet.setStatoRichiesta(StatoRichiestaType.I);
	
			applicationDao.updateTimesheetStatus(new TimesheetMensileKey(2020, EMese.GEN.getMonthId(), "01"), StatoRichiestaType.C);

//			Map<TimesheetEntryKey, List<NotaSpeseDto>> map = new HashMap<>();
//			notaSpeseList.forEach(r-> {
//				TimesheetEntryKey entryKey = new TimesheetEntryKey(r.getAnno(), r.getMese(), r.getGiorno(), r.getCodicePersona(), r.getCodiceCommessa());
//				r.setAnno(entryKey.getAnno());
//				r.setMese(entryKey.getMese());
//				r.setGiorno(entryKey.getGiorno());
//				r.setCodicePersona(entryKey.getCodicePersona());
//				r.setCodiceCommessa(entryKey.getCodiceCommessa());
//				if(map.containsKey(entryKey)) {
//					map.get(entryKey).add(r);
//				} else {
//					map.put(entryKey, new ArrayList<>());
//					map.get(entryKey).add(r);
//				}
//			});
//			
//			for (TimesheetEntryDto dataDto : timesheetDataList) {
//				oreTotali += dataDto.getOre();
//				Commessa commessa = commessaRepository.findByCodiceCommessa(dataDto.getCodiceCommessa());
//				TimesheetEntry entry = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(dataDto);
//				TimesheetEntryKey entryKey = new TimesheetEntryKey(tsKey.getAnno(), tsKey.getMese(), dataDto.getGiorno(), tsKey.getCodicePersona(), dataDto.getCodiceCommessa());
//				entry.setId(entryKey);
//				entry.setCommessa(commessa);
//				entry.setTimesheet(timesheet);
//				entry.setTipoCommessa(commessa.getTipoCommessa());
//				timesheet.addTimesheet(entry);
//				timesheet.setOreTotali(oreTotali);
//				List<NotaSpese> list= DtoEntityMapper.INSTANCE.fromDtoToEntityNotaSpese(map.get(entryKey));
//				entry.setNotaSpese(list);			
//				}
//			timesheetRepository.save(timesheet);
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

	public void giornoDiRiferimento(Integer anno, EMese mese, TimesheetEntryDto timesheetData) {
		List<Festivita> festivi = festivitaRepository.findAll();
		LocalDate data = LocalDate.of(anno, mese.getMonthId(), timesheetData.getGiorno());
		for (Festivita f : festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek() == DayOfWeek.SUNDAY
					|| data.getDayOfWeek() == DayOfWeek.SATURDAY) {
				throw new FestivitaException("Il giorno inserito non è corretto");
			}
			logger.info("Il giorno inserito è corretto");
		}
	}
	
}