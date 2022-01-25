package com.perigea.tracker.timesheet.service;


import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.GruppoException;
import com.perigea.tracker.timesheet.repository.GruppoRepository;

@Service
public class GruppoService {

	@Autowired
	private Logger logger;

	@Autowired
	private GruppoRepository gruppoRepository;
	
	/**
	 * Creazione gruppo
	 * @param dipendenteCommessa
	 * @return
	 */
	public Gruppo createGruppo(Gruppo gruppo) {
		try {
			gruppoRepository.save(gruppo);
			logger.info("Gruppo salvato");
			return gruppo;
		} catch (Exception ex) {
			throw new GruppoException(ex.getMessage());
		}
	}

	/**
	 * Lettura gruppo
	 * @param id
	 * @return
	 */
	public Gruppo readGruppo(final Long id) {
		try {
			return gruppoRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new GruppoException(ex.getMessage());
		}
	}
	
	/**
	 * 	
	 * @param gruppo
	 * @return
	 */
	public Gruppo updateGruppo(Gruppo gruppo) {
		try {
			gruppoRepository.save(gruppo);
			logger.info("Gruppo salvato");
			return gruppo;
		} catch (Exception ex) {
			throw new GruppoException(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public Gruppo deleteGruppo(final Long id) {
		try {
			Gruppo gruppo = readGruppo(id);
			gruppo.getContatti().clear();
			gruppoRepository.delete(gruppo);
			logger.info("Gruppo eliminato");
			return gruppo;
		} catch (Exception ex) {
			throw new GruppoException(ex.getMessage());
		}
	}
	
}