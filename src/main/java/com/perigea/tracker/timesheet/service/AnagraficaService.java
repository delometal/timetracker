package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.AnagraficaException;
import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.repository.AnagraficaRepository;

@Service
@Transactional
public class AnagraficaService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private AnagraficaRepository anagraficaRepository;

	/**
	 * creazione anagrafica
	 * @param anagrafica
	 * @return
	 */
	public Anagrafica saveAnagrafica(Anagrafica anagrafica) {
		try {
			return anagraficaRepository.save(anagrafica);
		} catch (Exception ex) {
			throw new AnagraficaException(ex.getMessage());
		}
	}

	/**
	 * lettura anagrafica
	 * @param id
	 * @return
	 */
	public Anagrafica readAnagrafica(String id) {
		try {
			return anagraficaRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new AnagraficaException(ex.getMessage());
		}
	}

	/**
	 * cancellazione anagrafica
	 * @param partitaIva
	 * @return
	 */
	public void deleteAnagrafica(String id) {
		try {
			anagraficaRepository.deleteById(id);
			logger.info("Anagrafica cancellata");
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new AnagraficaException(ex.getMessage());
		}
	}
	
	/**
	 * delete contatto
	 * @param contatto
	 */
	public void deleteAnagrafica(Anagrafica contatto) {
		try {
			anagraficaRepository.delete(contatto);
		} catch (Exception ex) {
			throw new AnagraficaException(ex.getMessage());
		}
	}

}
