package com.perigea.tracker.timesheet.service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.enums.AnagraficaType;
import com.perigea.tracker.commons.enums.RuoloType;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.ContattoException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.GruppoException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.GruppoRepository;
import com.perigea.tracker.timesheet.repository.RuoliRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
public class GruppoContattoService {

	@Autowired
	private Logger logger;

	@Autowired
	private GruppoRepository gruppoRepository;

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private RuoliRepository ruoliRepository;
	
	/**
	 * Creazione gruppo
	 * @param gruppo
	 * @return
	 */
	public Gruppo createGruppo(Gruppo gruppo) {
		try {
			return gruppoRepository.save(gruppo);
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
			return gruppoRepository.save(gruppo);
		} catch (Exception ex) {
			throw new GruppoException(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteGruppo(final Long id) {
		try {
			Gruppo gruppo = readGruppo(id);
			gruppo.getContatti().clear();
			gruppoRepository.delete(gruppo);
			logger.info("Gruppo eliminato");
		} catch (Exception ex) {
			throw new GruppoException(ex.getMessage());
		}
	}
	
	/**
	 * Creazione contatto
	 * @param contatto
	 * @return
	 */
	public Utente createContatto(Utente contatto) {
		try {
			List<Ruolo> ruoli = new ArrayList<Ruolo>(1);
			ruoli.add(ruoliRepository.getById(RuoloType.P));
			Utente utente = new Utente();
			utente.setCodicePersona(Utils.uuid());
			utente.setStato(StatoUtenteType.A);
			utente.setRuoli(ruoli);
			utente.setTipo(AnagraficaType.C);
			utenteRepository.save(utente);
			return contatto;
		} catch (Exception ex) {
			throw new GruppoException(ex.getMessage());
		}
	}

	/**
	 * Lettura contatto
	 * @param id
	 * @return
	 */
	public Utente readContatto(final String id) {
		try {
			return utenteRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new ContattoException(ex.getMessage());
		}
	}

	/**
	 * Lettura tutti i contatti
	 * @param id
	 * @return
	 */
	public List<Utente> readAllContatti() {
		try {
			return utenteRepository.findAll();
		} catch (Exception ex) {
			throw new ContattoException(ex.getMessage());
		}
	}
	
	/**
	 * Lettura tutti i contatti di un gruppo
	 * @param id
	 * @return
	 */
	public List<Utente> readAllContactsByGroupId(Long groupId) {
		try {
			return gruppoRepository.getById(groupId).getContatti();
		} catch (Exception ex) {
			throw new ContattoException(ex.getMessage());
		}
	}
	
	/**
	 * 	
	 * @param gruppo
	 * @return
	 */
	public Utente updateContatto(Utente contatto) {
		try {
			return utenteRepository.save(contatto);
		} catch (Exception ex) {
			throw new ContattoException(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteContatto(final String id) {
		try {
			utenteRepository.deleteById(id);
		} catch (Exception ex) {
			throw new ContattoException(ex.getMessage());
		}
	}
	
//	@EventListener
//	public void handleUserCrudEvent(UserCrudEvent event) {
//		try {
//			logger.info("UserCrudEvent handling request");
//			DipendenteDto persona = event.getDipendente();
//			String email = persona.getMailAziendale();
//			Anagrafica contatto = anagraficaRepository.findByMailAziendale(email).get();
//			if(contatto != null) {
//				switch (event.getCrudType()) {
//					case UPDATE:
//						contatto.setNome(persona.getNome());
//						contatto.setCognome(persona.getCognome());
//						contatto.setCodiceFiscale(persona.getCodiceFiscale());
//						contatto.setMailPrivata(persona.getMailPrivata());
//						contatto.setMailAziendale(email);
//						contatto.setCellulare(persona.getCellulare());
//						contatto.setComuneDiDomicilio(persona.getComuneDiDomicilio());
//						contatto.setIndirizzoDiDomicilio(persona.getIndirizzoDiDomicilio());
//						contatto.setProvinciaDiDomicilio(persona.getProvinciaDiDomicilio());
//						anagraficaRepository.save(contatto);
//						logger.info(String.format("Contatto con email aziendale %s ?? stato aggiornato", email));
//						break;
//					case DELETE:
//						anagraficaRepository.delete(contatto);
//						logger.info(String.format("Contatto con email aziendale %s ?? stato rimosso", email));
//						break;
//					case CREATE:
//					case READ:
//					default:
//					break;
//				}
//			} else {
//				logger.info(String.format("Contatto con email aziendale %s non ?? tra i contatti", email));
//			}
//		} catch(Exception ex) {
//			logger.error(ex.getMessage());
//		}
//	}
	
}