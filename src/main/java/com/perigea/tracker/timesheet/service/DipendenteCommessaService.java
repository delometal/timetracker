package com.perigea.tracker.timesheet.service;


import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.repository.DipendenteCommessaRepository;

@Service
public class DipendenteCommessaService {

	@Autowired
	private Logger logger;

	@Autowired
	private DipendenteCommessaRepository dipendenteCommessaRepository;
	
	/**
	 * Creazione relazione dipendente commessa
	 * @param dipendenteCommessaDto
	 * @return
	 */
	public DipendenteCommessa createDipendenteCommessa(DipendenteCommessa dipendenteCommessa) {
		try {
			dipendenteCommessa = dipendenteCommessaRepository.save(dipendenteCommessa);
			logger.info("Relazione Dipendente-commessa creata");
			return dipendenteCommessa;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * Lettura relazione dipendente commessa
	 * @param id
	 * @return
	 */
	public DipendenteCommessa readDipendenteCommessa(DipendenteCommessaKey id) {
		try {
			return dipendenteCommessaRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * 	
	 * @param dipendenteCommessaDto
	 * @return
	 */
	public DipendenteCommessa updateDipendenteCommessa(DipendenteCommessa dipendenteCommessa) {
		try {
			dipendenteCommessaRepository.save(dipendenteCommessa);
			logger.info("Relazione Dipendente-commessa aggiornata");
			return dipendenteCommessa;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public DipendenteCommessa deleteDipendenteCommessa(DipendenteCommessaKey id) {
		try {
			DipendenteCommessa dipendenteCommessa = readDipendenteCommessa(id);
			dipendenteCommessaRepository.delete(dipendenteCommessa);
			logger.info("Relazione Dipendente-commessa cancellata");
			return dipendenteCommessa;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
}