package com.perigea.tracker.timesheet.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.exception.ContattoException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.GruppoException;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.GruppoRepository;

@Service
public class GruppoContattoService {

	@Autowired
	private Logger logger;

	@Autowired
	private GruppoRepository gruppoRepository;


	/**
	 * Creazione gruppo
	 * 
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
	 * 
	 * @param id
	 * @return
	 */
	public Gruppo readGruppo(final Long id) {
		try {
			return gruppoRepository.findById(id).get();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new GruppoException(ex.getMessage());
		}
	}

	/**
	 * update gruppo 
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
	 * delete di un gruppo tramite id
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
	 * Lettura tutti i contatti di un gruppo
	 * 
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

}