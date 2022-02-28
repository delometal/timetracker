package com.perigea.tracker.timesheet.service;

import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.enums.AnagraficaType;
import com.perigea.tracker.commons.enums.RuoloType;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.ConsulenteException;
import com.perigea.tracker.commons.exception.PersistenceException;
import com.perigea.tracker.commons.exception.UtenteException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Personale;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
@Transactional
public class UtenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private ApplicationDao applicationDao;

	@Autowired
	protected UtenteRepository utenteRepository;

	public <T extends Personale> Utente createUtente(Utente utente, T personale) {
		try {
			utente.setCodicePersona(null);
			personale.setCodicePersona(null);
			String codicePersona = Utils.uuid();
			utente.setCodicePersona(codicePersona);
			personale.setUtente(utente);
			logger.info("utente salvato");
			return utenteRepository.save(utente);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new ConsulenteException(ex.getMessage());
		}
	}

	/**
	 * creazione utente
	 * 
	 * @param utente
	 * @return
	 */
	public Utente saveUtente(Utente utente) {
		try {
			return utenteRepository.save(utente);
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * lettura utente
	 * 
	 * @param id
	 * @return
	 */
	public Utente readUtente(String id) {
		try {
			return utenteRepository.findById(id).get();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public Utente loadUtente(String id) {
		try {
			return utenteRepository.getOne(id);
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * cancellazione utente
	 * 
	 * @param partitaIva
	 * @return
	 */
	public void deleteUtente(String id) {
		try {
			utenteRepository.getById(id).getRuoli().clear();
			utenteRepository.deleteById(id);
			logger.info("Utente cancellato");
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new UtenteException(ex.getMessage());
		}
	}

	/**
	 * delete contatto
	 * 
	 * @param contatto
	 */
	public void deleteUtente(Utente contatto) {
		try {
			utenteRepository.delete(contatto);
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	public Utente updateUtente(Utente utente) {
		try {
			return utenteRepository.save(utente);
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
				throw new PersistenceException(String.format(
						"Si è verificato un errore durante l'aggiornamento per l'utente %s con il nuovo stato %s",
						codicePersona, newStatus.name()));
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	public Utente saveContattoEsterno(Utente utente) {
		try {
			utente.setTipo(AnagraficaType.C);
			utente.setRuoli(Arrays.asList(Ruolo.builder().id(RuoloType.P).build()));
			utente = utenteRepository.save(utente);
			return utente;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public Utente updateContattoEsterno(Utente utente) {
		try {
			utente = utenteRepository.save(utente);
			return utente;
		} catch (Exception ex) {
			throw ex;
		}
	}

//	public Personale readAnagraficaPersonale(String codicePersona) {
//		try {
//			return utenteRepository.getById(codicePersona).getPersonale();
//		} catch (Exception ex) {
//			if(ex instanceof NoSuchElementException) {
//				throw new EntityNotFoundException(ex.getMessage());
//			}
//			throw new ConsulenteException(ex.getMessage());
//		}
//	}

}
