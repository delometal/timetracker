package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.enums.RuoloType;
import com.perigea.tracker.timesheet.exception.RuoloException;
import com.perigea.tracker.timesheet.repository.RuoliRepository;

@Service
public class RuoloService {

	@Autowired
	private Logger logger;

	@Autowired
	private RuoliRepository ruoliRepository;

	/**
	 * Creazione ruolo
	 * @param ruolo
	 * @return
	 */
	public Ruolo createRole(Ruolo ruolo) {
		try {
			return ruoliRepository.save(ruolo);
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
			return ruoliRepository.findById(ruolo).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new RuoloException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento ruolo
	 * @param ruoloDto
	 * @return
	 */
	public Ruolo updateRole(Ruolo ruolo) {
		try {
			ruoliRepository.save(ruolo);
			return ruolo;
		} catch (Exception ex) {
			throw new RuoloException(ex.getMessage());
		}
	}

	/**
	 * Cancellazione ruolo
	 * @param ruolo
	 * @return
	 */
	public void deleteRole(final RuoloType ruoloId) {
		try {
			ruoliRepository.deleteById(ruoloId);
			logger.info("Ruolo eliminato");
		} catch (Exception ex) {
			throw new RuoloException(ex.getMessage());
		}
	}
}
