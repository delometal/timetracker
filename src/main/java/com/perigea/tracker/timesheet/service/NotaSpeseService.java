package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.NotaSpeseException;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.NotaSpeseRepository;
import com.perigea.tracker.timesheet.repository.TimesheetDataRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
public class NotaSpeseService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private NotaSpeseRepository notaSpeseRepository;
	
	@Autowired
	private CommessaRepository commessaRepository;
	
	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private TimesheetDataRepository timesheetEntryRepository;

	/**
	 * creazione di una nota spese
	 * @param notaSpese
	 * @return
	 */
	public NotaSpese createNotaSpese(NotaSpese notaSpese) {
		try {
			NotaSpeseKey id = new NotaSpeseKey(notaSpese.getId().getAnno(), notaSpese.getId().getMese(), notaSpese.getId().getGiorno(),
					notaSpese.getId().getCodicePersona(), notaSpese.getId().getCodiceCommessa(),
					notaSpese.getId().getCostoNotaSpese());
			notaSpese.setId(id);
			TimesheetEntryKey tsKey = new TimesheetEntryKey(notaSpese.getId().getAnno(), notaSpese.getId().getMese(), notaSpese.getId().getGiorno(), notaSpese.getId().getCodicePersona(), notaSpese.getId().getCodiceCommessa());
			TimesheetEntry timesheetEntry = timesheetEntryRepository.findById(tsKey).orElseThrow();
			Commessa commessa = commessaRepository.findByCodiceCommessa(notaSpese.getId().getCodiceCommessa()).orElseThrow();
			Utente utente = utenteRepository.findByCodicePersona(notaSpese.getId().getCodicePersona()).orElseThrow();
			if(timesheetEntry != null) {
				notaSpese.setTimesheetEntry(timesheetEntry);
			}
			notaSpese.setCommessa(commessa);
			notaSpese.setPersonale(utente.getPersonale());
			notaSpeseRepository.save(notaSpese);
			logger.info("Nota spese salvata");
			return notaSpese;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non creata");
		}
	}
	
	/**
	 * lettura di una nota spese
	 * @param key
	 * @return
	 */
	public NotaSpese readNotaSpese(NotaSpeseKey key) {
		try {
			return notaSpeseRepository.findById(key).orElseThrow();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}
	
	/**
	 * update di una nota spese
	 * @param notaSpese
	 * @return
	 */
	public NotaSpese updateNotaSpese(NotaSpese notaSpese) {
		try {
			notaSpeseRepository.save(notaSpese);
			return notaSpese;
		} catch (Exception ex) {
			throw new NotaSpeseException(ex.getMessage());
		}
	}
	
	/**
	 * delete di una nota spese tramite composite key
	 * @param key
	 */
	public void deleteNotaSpese(NotaSpeseKey key) {
		try {
			notaSpeseRepository.deleteById(key);
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new NotaSpeseException(ex.getMessage());
		}
	}
	
	/**
	 * delete di una nota spese
	 * @param notaSpese
	 */
	public void deleteNotaSpese(NotaSpese notaSpese) {
		try {
			notaSpeseRepository.delete(notaSpese);
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new NotaSpeseException(ex.getMessage());
		}
	}
	
	
	
}
