package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.enums.RuoloType;
import com.perigea.tracker.timesheet.exception.RuoloException;
import com.perigea.tracker.timesheet.repository.RuoliRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@Service
public class RuoloService {

	@Autowired
	private Logger logger;

	@Autowired
	private RuoliRepository ruoliRepository;

	/**
	 * Creazione ruolo
	 * @param ruoloDto
	 * @return
	 */
	public Ruolo createRole(RuoloDto ruoloDto) {
		try {
			Ruolo ruolo = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
			logger.info("Role creato");
			ruoliRepository.save(ruolo);
			logger.info("Role aggiunto a database");
			return ruolo;
		} catch (Exception ex) {
			throw new RuoloException(ex.getMessage());
		}
	}

	/**
	 * Lettura delle informazioni di un ruolo
	 * @param ruolo
	 * @return
	 */
	public Ruolo readRole(RuoloType ruolo) {
		try {
			return ruoliRepository.findByTipo(ruolo);
		} catch (Exception ex) {
			throw new RuoloException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento ruolo
	 * @param ruoloDto
	 * @return
	 */
	public Ruolo updateRole(RuoloDto ruoloDto) {
		try {
			Ruolo ruolo = ruoliRepository.findByTipo(ruoloDto.getRuoloType());
			if (ruolo != null) {
				ruolo = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
				ruoliRepository.save(ruolo);
			}
			return ruolo;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	/**
	 * Cancellazione ruolo
	 * @param ruolo
	 * @return
	 */
	public Ruolo deleteRole(RuoloType ruolo) {
		try {
			Ruolo ruoloEntity = ruoliRepository.findByTipo(ruolo);
			if (ruoloEntity != null) {
				ruoliRepository.delete(ruoloEntity);
			}
			return ruoloEntity;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
}
