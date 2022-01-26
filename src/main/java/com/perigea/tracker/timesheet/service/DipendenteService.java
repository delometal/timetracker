package com.perigea.tracker.timesheet.service;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.enums.StatoUtenteType;
import com.perigea.tracker.timesheet.exception.DipendenteException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.PersistenceException;
import com.perigea.tracker.timesheet.repository.AnagraficaDipendenteRepository;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;

@Service
public class DipendenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private AnagraficaDipendenteRepository dipendenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private ApplicationDao applicationDao;

	/**
	 * Creazione anagrafica dipendente e utente
	 * @param utente
	 * @param dipendente
	 * @param codiceResponsabile
	 * @return
	 */
	public Utente createUtenteDipendente(Utente utente, AnagraficaDipendente dipendente) {
		try {
			dipendente.setUtente(utente);
			dipendente.setCodicePersona(TSUtils.uuid());
			utente.setDipendente(dipendente);
			utenteRepository.save(utente);
			logger.info("utente salvato");
			return utente;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Lettura dati di un dipendente
	 * @param dipendenteParam
	 * @return
	 */
	public Utente readUtenteDipendente(String codicePersona) {
		try {
			return utenteRepository.findByCodicePersona(codicePersona).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new DipendenteException(ex.getMessage());
		}
	}
	
	/**
	 * Lettura dati di una AnagraficaDipendente
	 * @param dipendenteParam
	 * @return
	 */
	public AnagraficaDipendente readAnagraficaDipendente(String codicePersona) {
		try {
			return dipendenteRepository.findByCodicePersona(codicePersona).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento dati dipendente
	 * @param dipendenteParam
	 * @return
	 */
	public Utente updateUtenteDipendente(Utente utente) {
		try {
			return utenteRepository.save(utente);
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Cancellazione dipendente
	 * @param id
	 * @return
	 */
	public void deleteUtenteDipendente(String id) {
		try {
			utenteRepository.deleteById(id);
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
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