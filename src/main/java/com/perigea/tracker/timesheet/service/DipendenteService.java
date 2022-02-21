package com.perigea.tracker.timesheet.service;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.DipendenteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.PersistenceException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.DipendenteRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
@Transactional
public class DipendenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private ApplicationDao applicationDao;

	/**
	 * Creazione anagrafica dipendente e utente
	 * @param utente
	 * @param dipendente
	 * @param economics 
	 * @param codiceResponsabile
	 * @return
	 */
	public Utente createUtenteDipendente(Utente utente, Dipendente dipendente, DatiEconomiciDipendente economics) {
		try {
			utente.setCodicePersona(null);
			dipendente.setCodicePersona(null);
			economics.setCodicePersona(null);
			String codicePersona = Utils.uuid();
			utente.setCodicePersona(codicePersona);			
			dipendente.setUtente(utente);
			utente.setPersonale(dipendente);
			economics.setPersonale(dipendente);
			dipendente.setEconomics(economics);
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
	public Dipendente readAnagraficaDipendente(String codicePersona) {
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