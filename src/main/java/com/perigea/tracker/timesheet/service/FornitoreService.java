package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.exception.ClienteException;
import com.perigea.tracker.timesheet.exception.FornitoreException;
import com.perigea.tracker.timesheet.repository.AnagraficaRepository;
import com.perigea.tracker.timesheet.repository.FornitoreRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;

@Service
@Transactional
public class FornitoreService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private FornitoreRepository fornitoreRepository;

	@Autowired
	private AnagraficaRepository anagraficaRepository;

	/**
	 * creazione anagrafica fornitore
	 * @param anaFornitoreDto
	 * @return
	 */
	public Fornitore saveFornitore(Fornitore fornitore) {
		try {
			return fornitoreRepository.save(fornitore);
		} catch (Exception ex) {
			throw new FornitoreException(ex.getMessage());
		}
	}

	/**
	 * lettura anagrafica fornitore
	 * @param partitaIva
	 * @return
	 */
	public Fornitore readFornitore(String id) {
		try {
			return fornitoreRepository.findByPartitaIva(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new FornitoreException(ex.getMessage());
		}
	}

	/**
	 * cancellazione anagrafica fornitore
	 * @param partitaIva
	 * @return
	 */
	public void deleteFornitore(String id) {
		try {
			fornitoreRepository.deleteById(id);
			logger.info("Fornitore cancellato");
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new FornitoreException(ex.getMessage());
		}
	}

	/**
	 * add contatto
	 * @param cliente
	 * @param contatto
	 */
	public void addContatto(Fornitore fornitore, Anagrafica contatto) {
		try {
			contatto.setCodicePersona(TSUtils.uuid());
			fornitore.addContatto(contatto);
			fornitoreRepository.save(fornitore);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}
	
	/**
	 * remove contatto
	 * @param fornitore
	 * @param contatto
	 */
	public void removeContatto(Fornitore fornitore, Anagrafica contatto) {
		try {
			fornitore.removeContatto(contatto);
			fornitoreRepository.save(fornitore);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	/**
	 * delete contatto
	 * @param fornitore
	 * @param contatto
	 */
	public void deleteContatto(Fornitore fornitore, Anagrafica contatto) {
		try {
			fornitore.removeContatto(contatto);
			fornitoreRepository.save(fornitore);
			anagraficaRepository.deleteById(contatto.getCodicePersona());
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}
	
}
