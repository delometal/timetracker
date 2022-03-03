package com.perigea.tracker.timesheet.service;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.DipendenteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.DipendenteRepository;

@Service
@Transactional
public class DipendenteService extends UtenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	

	/**
	 * Creazione anagrafica dipendente e utente
	 * @param utente
	 * @param dipendente
	 * @param economics 
	 * @param codiceResponsabile
	 * @return
	 */
	public Utente createUtenteDipendente(Utente utente, Dipendente dipendente, DatiEconomiciDipendente economics) {
		dipendente.setEconomics(economics);
		if(economics != null) {
			economics.setCodicePersona(null);
			economics.setPersonale(dipendente);
		}
		utente.setPersonale(dipendente);
		return super.createUtente(utente, dipendente);
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
}