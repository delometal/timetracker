package com.perigea.tracker.timesheet.service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.TimeSheetDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetKey;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.exception.TimeSheetException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.TimeSheetRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;



@Service
public class TimesheetService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private TimeSheetRepository timesheetRepository;
	
	@Autowired
	private FestivitaRepository festivitaRepository;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private CommessaRepository commessaRepo;

	public TimeSheetDto createTimeSheet(String codicePersona, String codiceCommessa, TimeSheetDto timeSheetParam) {
		try {
			Utente utente = utenteRepository.findByCodicePersona(codicePersona);
			Commessa commessa = commessaRepo.findByCodiceCommessa(codiceCommessa);
			Timesheet timeSheetEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
			giornoDiRiferimento(timeSheetParam);
			timeSheetEntity.setUtenteTimesheet(utente);
			utente.addTimeSheet(timeSheetEntity);
			timeSheetEntity.setCommessaTimesheet(commessa);
			TimesheetKey id = new TimesheetKey(timeSheetParam.getAnnoDiRiferimento(), timeSheetParam.getMeseDiRiferimento(), 
				timeSheetParam.getGiornoDiRiferimento(), codicePersona, codiceCommessa);
			timeSheetEntity.setId(id);
			timesheetRepository.save(timeSheetEntity);
			logger.info("TimeSheet creato e aggiunto a database");
			TimeSheetDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
			return dto;
		}catch(Exception ex) {
			throw new TimeSheetException("Timesheet non creato " + ex.getMessage());	
		}
	}

	public TimeSheetDto editTimeSheet(TimeSheetDto timeSheetParam, Commessa commessa, Utente utente) {
		try {
			Timesheet timeSheetEntity=timesheetRepository.findByUtenteTimesheet(utente);
			if(timeSheetEntity != null) {
				timesheetRepository.delete(timeSheetEntity);
				timeSheetEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
				timeSheetEntity.setUtenteTimesheet(utente);
				timeSheetEntity.setCommessaTimesheet(commessa);
				timesheetRepository.save(timeSheetEntity);
				logger.info("Timesheet modificato");
			}
			TimeSheetDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
			return dto;
		}catch(Exception ex) {
			throw new EntityNotFoundException("Timesheet non trovato "+ ex.getMessage());	
		}
	}
	
	public void editStatusTimeSheet(TimeSheetDto timeSheetParam) {
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
	
	public void giornoDiRiferimento(TimeSheetDto timeSheetParam) {
		List<Festivita> festivi = festivitaRepository.findAll();
		LocalDate data = LocalDate.of(timeSheetParam.getAnnoDiRiferimento(), timeSheetParam.getMeseDiRiferimento(), timeSheetParam.getGiornoDiRiferimento());
		for (Festivita f:festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek()== DayOfWeek.SUNDAY || data.getDayOfWeek()== DayOfWeek.SATURDAY ) {
				throw new FestivitaException ("Il giorno inserito non è corretto");
			} 
			logger.info("Il giorno inserito è corretto");
		}
	}
}