package com.perigea.tracker.timesheet.service;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.ConsulenteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.PersistenceException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.ConsulenteRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
public class ConsulenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private ConsulenteRepository consulenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private ApplicationDao applicationDao;

	/**
	 * Creazione anagrafica consulente e utente
	 * @param utente
	 * @param consulente
	 * @param codiceResponsabile
	 * @return
	 */
	public Utente createUtenteConsulente(Utente utente, Consulente consulente) {
		try {
			consulente.setUtente(utente);
			consulente.setCodicePersona(Utils.uuid());
			utente.setAnagrafica(consulente);
			utenteRepository.save(utente);
			logger.info("utente salvato");
			return utente;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new ConsulenteException(ex.getMessage());
		}
	}

	/**
	 * Lettura dati di un consulente
	 * @param codicePersona
	 * @return
	 */
	public Utente readUtenteConsulente(String codicePersona) {
		try {
			return utenteRepository.findByCodicePersona(codicePersona).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new ConsulenteException(ex.getMessage());
		}
	}
	
	/**
	 * Lettura dati di una AnagraficaConsulente
	 * @param consulenteParam
	 * @return
	 */
	public Consulente readAnagraficaConsulente(String codicePersona) {
		try {
			return consulenteRepository.findByCodicePersona(codicePersona).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new ConsulenteException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento dati consulente
	 * @param consulenteParam
	 * @return
	 */
	public Utente updateUtenteConsulente(Utente utente) {
		try {
			return utenteRepository.save(utente);
		} catch (Exception ex) {
			throw new ConsulenteException(ex.getMessage());
		}
	}

	/**
	 * Cancellazione consulente
	 * @param id
	 * @return
	 */
	public void deleteUtenteConsulente(String id) {
		try {
			utenteRepository.deleteById(id);
		} catch (Exception ex) {
			throw new ConsulenteException(ex.getMessage());
		}
	}

	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
	public Utente updateUtenteStatus(String codicePersona, StatoUtenteType newStatus) {
		try {
			Integer edits = applicationDao.updateUserStatus(codicePersona, newStatus);
			if (edits != null && edits == 1) {
				return utenteRepository.findByCodicePersona(codicePersona).get();
			} else {
				throw new PersistenceException(String.format("Si è verificato un errore durante l'aggiornamento per l'utente %s con il nuovo stato %s", codicePersona, newStatus.name()));
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

}