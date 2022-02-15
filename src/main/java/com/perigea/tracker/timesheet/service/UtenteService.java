package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.UtenteException;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
@Transactional
public class UtenteService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private UtenteRepository utenteRepository;

	/**
	 * creazione utente
	 * @param utente
	 * @return
	 */
	public Utente saveUtente(Utente utente) {
		try {
			return utenteRepository.save(utente);
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * lettura utente
	 * @param id
	 * @return
	 */
	public Utente readUtente(String id) {
		try {
			return utenteRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * cancellazione utente
	 * @param partitaIva
	 * @return
	 */
	public void deleteUtente(String id) {
		try {
			utenteRepository.deleteById(id);
			logger.info("Utente cancellato");
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}
	
	/**
	 * delete contatto
	 * @param contatto
	 */
	public void deleteUtente(Utente contatto) {
		try {
			utenteRepository.delete(contatto);
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

}
