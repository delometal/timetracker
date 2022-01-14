package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.enumerator.RuoloType;
import com.perigea.tracker.timesheet.exception.RuoloException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.RuoliRepository;



@Service
public class RuoloService {

	@Autowired
	private Logger logger;

	@Autowired
	private RuoliRepository ruoliRepository;

	// Metodo per creare un nuovo ruolo
	public RuoloDto createRole(RuoloDto ruoloDto) {
		try {
			Ruolo role = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
			logger.info("Role creato");
			ruoliRepository.save(role);
			logger.info("Role aggiunto a database");
			RuoloDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(role);
			return dto;
		} catch (Exception ex) {
			throw new RuoloException(ex.getMessage());
		}
	}

	// Metodo per leggere le informazioni specifiche di un ruolo
	public RuoloDto readRole(RuoloType ruolo) {
		try {
			Ruolo entity = ruoliRepository.findByTipo(ruolo);
			RuoloDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare i dati di un ruolo già esistente
	public RuoloDto updateRole(RuoloDto ruoloDto) {
		try {
			Ruolo entity = ruoliRepository.findByTipo(ruoloDto.getRuoloType());
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
				ruoliRepository.save(entity);
			}
			RuoloDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per eliminare un ruolo da database
	public RuoloDto deleteRole(RuoloType ruolo) {
		try {
			Ruolo entity = ruoliRepository.findByTipo(ruolo);
			if (entity != null) {
				ruoliRepository.delete(entity);
			}
			RuoloDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
}
