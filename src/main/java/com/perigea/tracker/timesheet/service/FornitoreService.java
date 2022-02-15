package com.perigea.tracker.timesheet.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.ClienteException;
import com.perigea.tracker.commons.exception.FornitoreException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.FornitoreRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
@Transactional
public class FornitoreService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private FornitoreRepository fornitoreRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	/**
	 * creazione anagrafica fornitore
	 * @param anaFornitoreDto
	 * @return
	 */
	public Fornitore saveFornitore(Fornitore fornitore) {
		try {
			fornitore.setCodiceAzienda(Utils.uuid());
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
	public Fornitore readFornitoreById(String id) {
		try {
			return fornitoreRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new FornitoreException(ex.getMessage());
		}
	}
	

	/**
	 * lettura anagrafica fornitore
	 * @param partitaIva
	 * @return
	 */
	public Fornitore readFornitoreByPartitaIva(String partitaIva) {
		try {
			return fornitoreRepository.findByPartitaIva(partitaIva).get();
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
	public void deleteFornitoreById(String id) {
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
	 * cancellazione anagrafica fornitore
	 * @param partitaIva
	 * @return
	 */
	public void deleteFornitoreByPartitaIva(String partitaIva) {
		try {
			fornitoreRepository.deleteById(readFornitoreByPartitaIva(partitaIva).getCodiceAzienda());
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
	public void addContatto(Fornitore fornitore, Utente contatto) {
		try {
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
	public void removeContatto(Fornitore fornitore, Utente contatto) {
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
	public void deleteContatto(Fornitore fornitore, Utente contatto) {
		try {
			fornitore.removeContatto(contatto);
			fornitoreRepository.save(fornitore);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	/**
	 * @param codicePersona
	 * @return
	 */
	public Utente findContatto(String codicePersona) {
		try {
			return utenteRepository.findByCodicePersona(codicePersona).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new FornitoreException(ex.getMessage());
		}
	}
	

	/**
	 * Legge tutti i fornitori
	 * @return
	 */
	public List<Fornitore> readAll() {
		try {
			return fornitoreRepository.findAll();
		} catch (Exception ex) {
			throw new FornitoreException(ex.getMessage());
		}
	}
	
}
