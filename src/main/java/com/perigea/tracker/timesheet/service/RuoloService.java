package com.perigea.tracker.timesheet.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perigea.tracker.timesheet.controller.UtenteController;
import com.perigea.tracker.timesheet.dto.RuoliDto;
import com.perigea.tracker.timesheet.entity.Ruoli;
import com.perigea.tracker.timesheet.exception.RuoloException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.RuoliRepository;



@Service
public class RuoloService {

	@Autowired
	private Logger logger;
	
	@Autowired
	private RuoliRepository roleRepo;

	//Metodo per creare un nuovo ruolo
	public RuoliDto createRole(RuoliDto roleParam) {
		try {
			Ruoli role = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(roleParam);
			logger.info("Role creato");
			roleRepo.save(role);
			logger.info("Role aggiunto a database");
			RuoliDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(role);
			return dto;
		}catch(Exception ex) {
			throw new RuoloException("Ruolo non creato");	
		}
	}

	//Metodo per leggere le informazioni specifiche di un ruolo
	public RuoliDto readRole(String roleParam) {
		try {
			Ruoli entity=roleRepo.findByRuoloType(roleParam);
			RuoliDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		}catch(Exception ex) {
			throw new EntityNotFoundException("Ruolo non trovato");	
		}
	}

	//Metodo per aggiornare i dati di un ruolo già esistente
	public RuoliDto updateRole(RuoliDto roleParam) {
		try {
			Ruoli entity=roleRepo.findByRuoloType(roleParam.getRuoloType().toString());	
			if(entity != null) {
				roleRepo.delete(entity);
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(roleParam);
				roleRepo.save(entity);
			}
			RuoliDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		}catch(Exception ex) {
			throw new EntityNotFoundException("Ruolo non trovato");	
		}
	}

	//Metodo per eliminare un ruolo da database
	// @ TODO FIXME ruoloType è enum, roleParam è stringa, confronto mele con pere
	public void deleteRole(String roleParam) {
		try {
			List<Ruoli> entity= roleRepo.findAll();
			for(Ruoli r: entity) {
				if(r.getRuoloType().equals(roleParam)) { 
					roleRepo.delete(r);
				} 
			}
		}catch(Exception ex) {
			throw new EntityNotFoundException("Ruolo non trovato");	
		}
	}
}
