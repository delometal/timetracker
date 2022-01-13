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
import com.perigea.tracker.timesheet.entity.TimeSheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.RelazioneIdTimeSheetKey;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.exception.TimeSheetException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.TimeSheetRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;





@Service
public class TimeSheetService{
	
	@Autowired
	private Logger logger;

	@Autowired
	private TimeSheetRepository timeSheetRepo;
	
	@Autowired
	private FestivitaRepository festiviRepo;
	
	@Autowired
	private UtenteRepository utenteRepo;
	
	@Autowired
	private CommessaRepository commessaRepo;

	public TimeSheetDto createTimeSheet(String codicePersona, String codiceCommessa, TimeSheetDto timeSheetParam) {
		try {
			Utente utente = utenteRepo.findByCodicePersona(codicePersona);
			Commessa commessa = commessaRepo.findByCodiceCommessa(codiceCommessa);
			TimeSheet timeSheetEntity=DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
			giornoDiRiferimento(timeSheetParam);
			timeSheetEntity.setUtenteTimeSheet(utente);
			utente.addTimeSheet(timeSheetEntity);
			timeSheetEntity.setCommessaTimeSheet(commessa);
			commessa.addCommessa(timeSheetEntity);
			RelazioneIdTimeSheetKey id = new RelazioneIdTimeSheetKey();
			id.setAnnoDiRiferimento(timeSheetParam.getAnnoDiRiferimento());
			id.setCodiceCommessa(codiceCommessa);
			id.setCodicePersona(codicePersona);
			id.setGiornoDiRiferimento(timeSheetParam.getGiornoDiRiferimento());
			id.setMeseDiRiferimento(timeSheetParam.getMeseDiRiferimento());
			timeSheetEntity.setId(id);
			timeSheetRepo.save(timeSheetEntity);
			logger.info("TimeSheet creato e aggiunto a database");
			TimeSheetDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
			return dto;
		}catch(Exception ex) {
			throw new TimeSheetException("Timesheet non creato " + ex.getMessage());	
		}
	}

	public TimeSheetDto editTimeSheet(TimeSheetDto timeSheetParam, Commessa commessa, Utente utente) {
		try {
			TimeSheet timeSheetEntity=timeSheetRepo.findByUtenteTimeSheet(utente);
			if(timeSheetEntity != null) {
				timeSheetRepo.delete(timeSheetEntity);
				timeSheetEntity=DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
				timeSheetEntity.setUtenteTimeSheet(utente);
				timeSheetEntity.setCommessaTimeSheet(commessa);
				timeSheetRepo.save(timeSheetEntity);
				logger.info("TimeSheet modificato");
			}
			TimeSheetDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
			return dto;
		}catch(Exception ex) {
			throw new EntityNotFoundException("Timesheet non trovato "+ ex.getMessage());	
		}
	}
	
	public void giornoDiRiferimento(TimeSheetDto timeSheetParam) {
		List<Festivita> festivi = festiviRepo.findAll();
		LocalDate data = LocalDate.of(timeSheetParam.getAnnoDiRiferimento(), timeSheetParam.getMeseDiRiferimento(), timeSheetParam.getGiornoDiRiferimento());
		for (Festivita f:festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek()== DayOfWeek.SUNDAY || data.getDayOfWeek()== DayOfWeek.SATURDAY ) {
				throw new FestivitaException ("Il giorno inserito non è corretto");
			} 
			logger.info("Il giorno inserito è corretto");
		}
	}
}