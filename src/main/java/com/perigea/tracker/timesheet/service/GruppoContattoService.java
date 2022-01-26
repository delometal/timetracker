package com.perigea.tracker.timesheet.service;


import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.entity.Contatto;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.events.UserCrudEvent;
import com.perigea.tracker.timesheet.exception.ContattoException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.GruppoException;
import com.perigea.tracker.timesheet.repository.ContattoRepository;
import com.perigea.tracker.timesheet.repository.GruppoRepository;

@Service
public class GruppoContattoService {

	@Autowired
	private Logger logger;

	@Autowired
	private GruppoRepository gruppoRepository;

	@Autowired
	private ContattoRepository contattoRepository;
	
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
	public Contatto createContatto(Contatto contatto) {
		try {
			return contattoRepository.save(contatto);
		} catch (Exception ex) {
			throw new GruppoException(ex.getMessage());
		}
	}

	/**
	 * Lettura contatto
	 * @param id
	 * @return
	 */
	public Contatto readContatto(final Long id) {
		try {
			return contattoRepository.findById(id).get();
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
	public List<Contatto> readAllContatti() {
		try {
			return contattoRepository.findAll();
		} catch (Exception ex) {
			throw new ContattoException(ex.getMessage());
		}
	}
	
	/**
	 * Lettura tutti i contatti di un gruppo
	 * @param id
	 * @return
	 */
	public List<Contatto> readAllContactsByGroupId(Long groupId) {
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
	public Contatto updateContatto(Contatto contatto) {
		try {
			return contattoRepository.save(contatto);
		} catch (Exception ex) {
			throw new ContattoException(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteContatto(final Long id) {
		try {
			contattoRepository.deleteById(id);
		} catch (Exception ex) {
			throw new ContattoException(ex.getMessage());
		}
	}
	
	@EventListener
	public void handleUserCrudEvent(UserCrudEvent event) {
		try {
			logger.info("UserCrudEvent handling request");
			AnagraficaDipendenteResponseDto persona = event.getAnagraficaDto();
			String email = persona.getMailAziendale();
			Contatto contatto = contattoRepository.findByMailAziendale(email).get();
			if(contatto != null) {
				switch (event.getCrudType()) {
					case UPDATE:
						contatto.setNome(persona.getUtenteDto().getNome());
						contatto.setCognome(persona.getUtenteDto().getNome());
						contatto.setCodiceFiscale(persona.getCodiceFiscale());
						contatto.setMailPrivata(persona.getMailPrivata());
						contatto.setMailAziendale(email);
						contatto.setCellulare(persona.getCellulare());
						contatto.setComuneDiDomicilio(persona.getComuneDiDomicilio());
						contatto.setIndirizzoDiDomicilio(persona.getIndirizzoDiDomicilio());
						contatto.setProvinciaDiDomicilio(persona.getProvinciaDiDomicilio());
						contattoRepository.save(contatto);
						logger.info(String.format("Contatto con email aziendale %s è stato aggiornato", email));
						break;
					case DELETE:
						contattoRepository.delete(contatto);
						logger.info(String.format("Contatto con email aziendale %s è stato rimosso", email));
						break;
					case CREATE:
					case READ:
					default:
					break;
				}
			} else {
				logger.info(String.format("Contatto con email aziendale %s non è tra i contatti", email));
			}
		} catch(Exception ex) {
			logger.error(ex.getMessage());
		}
	}
	
}