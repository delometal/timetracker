package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;

@Service
public class FestivitaService {

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Autowired
	private Logger logger;

	/**
	 * creazione festività
	 * @param festivita
	 * @return
	 */
	public Festivita createFestivita(Festivita festivita) {
		try {
			festivitaRepository.save(festivita);
			logger.info("festività inserita a db");
			return festivita;
		} catch (Exception ex) {
			throw new FestivitaException("festività non inserita");
		}
	}

	/**
	 * lettura dati di una festività
	 * @param id
	 * @return
	 */
	public Festivita readFestivita(Integer id) {
		try {
			return festivitaRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new FestivitaException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento di una festività
	 * @param festivita
	 * @return
	 */
	public Festivita updateFestivita(Festivita festivita) {
		try {
			festivitaRepository.save(festivita);
			return festivita;
		} catch (Exception ex) {
			throw new FestivitaException("festività non trovata");
		}
	}

	/**
	 * Cancellazione di una festività
	 * @param id
	 * @return
	 */
	public Festivita deleteFestivita(Integer id) {
		try {
			Festivita festivita = festivitaRepository.findById(id).get();
			festivitaRepository.deleteById(id);
			return festivita;
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new FestivitaException(ex.getMessage());
		}
	}

}
