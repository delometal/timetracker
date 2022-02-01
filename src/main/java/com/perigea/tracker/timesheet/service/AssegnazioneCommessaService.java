package com.perigea.tracker.timesheet.service;


import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.repository.AssegnazioneCommessaRepository;

@Service
public class AssegnazioneCommessaService {

	@Autowired
	private Logger logger;

	@Autowired
	private AssegnazioneCommessaRepository assegnazioneCommessaRepository;
	
	/**
	 * Creazione relazione dipendente commessa
	 * @param dipendenteCommessaDto
	 * @return
	 */
	public DipendenteCommessa createDipendenteCommessa(DipendenteCommessa dipendenteCommessa) {
		try {
			return assegnazioneCommessaRepository.save(dipendenteCommessa);
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
			return assegnazioneCommessaRepository.findById(id).get();
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
			return assegnazioneCommessaRepository.save(dipendenteCommessa);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteDipendenteCommessa(DipendenteCommessaKey id) {
		try {
			assegnazioneCommessaRepository.deleteById(id);
			logger.info("Relazione Dipendente-commessa cancellata");
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
}