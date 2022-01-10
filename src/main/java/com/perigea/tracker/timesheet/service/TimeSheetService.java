package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perigea.tracker.timesheet.controller.UtenteController;
import com.perigea.tracker.timesheet.dto.TimeSheetDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.TimeSheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.exception.TimeSheetException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.TimeSheetRepository;



@Service
public class TimeSheetService{
	
	@Autowired
	private Logger logger;

	@Autowired
	private TimeSheetRepository timeSheetRepo;

	public TimeSheetDto createTimeSheet(Utente utente,Commessa commessa,TimeSheetDto timeSheetParam) {
		try {
			TimeSheet timeSheetEntity=DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
			timeSheetEntity.setUtenteTime(utente);
			timeSheetEntity.setCommessaTimeSheed(commessa);
			timeSheetRepo.save(timeSheetEntity);
			logger.info("TimeSheet creato e aggiunto a database");
			TimeSheetDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
			return dto;
		}catch(Exception ex) {
			throw new TimeSheetException("Timesheet non creato");	
		}
	}

	public TimeSheetDto editTimeSheet(TimeSheetDto timeSheetParam, Commessa commessa, Utente utente) {
		try {
			TimeSheet timeSheetEntity=timeSheetRepo.findByUtenteTimeSheet(utente);
			if(timeSheetEntity != null) {
				timeSheetRepo.delete(timeSheetEntity);
				timeSheetEntity=DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
				timeSheetEntity.setUtenteTime(utente);
				timeSheetEntity.setCommessaTimeSheed(commessa);
				timeSheetRepo.save(timeSheetEntity);
				logger.info("TimeSheet modificato");
			}
			TimeSheetDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
			return dto;
		}catch(Exception ex) {
			throw new EntityNotFoundException("Timesheet non trovato");	
		}
	}
}
