package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.RuoliDto;
import com.perigea.tracker.timesheet.entity.Ruoli;
import com.perigea.tracker.timesheet.enumerator.RuoloType;
import com.perigea.tracker.timesheet.exception.RuoloException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.RuoliRepository;



@Service
public class RuoloService {

	@Autowired
	private Logger logger;

	@Autowired
	private RuoliRepository roleRepo;

	// Metodo per creare un nuovo ruolo
	public RuoliDto createRole(RuoliDto ruoloDto) {
		try {
			Ruoli role = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
			logger.info("Role creato");
			roleRepo.save(role);
			logger.info("Role aggiunto a database");
			RuoliDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(role);
			return dto;
		} catch (Exception ex) {
			throw new RuoloException(ex.getMessage());
		}
	}

	// Metodo per leggere le informazioni specifiche di un ruolo
	public RuoliDto readRole(RuoloType ruolo) {
		try {
			Ruoli entity = roleRepo.findByRuoloType(ruolo);
			RuoliDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare i dati di un ruolo già esistente
	public RuoliDto updateRole(RuoliDto ruoloDto) {
		try {
			Ruoli entity = roleRepo.findByRuoloType(ruoloDto.getRuoloType());
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
				roleRepo.save(entity);
			}
			RuoliDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per eliminare un ruolo da database
	public RuoliDto deleteRole(RuoloType ruolo) {
		try {
			Ruoli entity = roleRepo.findByRuoloType(ruolo);
			if (entity != null) {
				roleRepo.delete(entity);
			}
			RuoliDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
}
