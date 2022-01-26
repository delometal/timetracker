package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.exception.ClienteException;
import com.perigea.tracker.timesheet.repository.AnagraficaClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private AnagraficaClienteRepository anagraficaClienteRepository;

	/**
	 * creazione anagrafica cliente
	 * @param anaClienteDto
	 * @return
	 */
	public AnagraficaCliente saveAnagraficaCliente(AnagraficaCliente anagraficaCliente) {
		try {
			return anagraficaClienteRepository.save(anagraficaCliente);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	/**
	 * lettura anagrafica cliente
	 * @param partitaIva
	 * @return
	 */
	public AnagraficaCliente readAnagraficaCliente(String id) {
		try {
			return anagraficaClienteRepository.findByPartitaIva(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new ClienteException(ex.getMessage());
		}
	}

	/**
	 * cancellazione anagrafica cliente
	 * @param partitaIva
	 * @return
	 */
	public void deleteAnagraficaCliente(String id) {
		try {
			anagraficaClienteRepository.deleteById(id);
			logger.info("Relazione Dipendente-commessa cancellata");
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new ClienteException(ex.getMessage());
		}
	}
	
	/**
	 * cancellazione anagrafica cliente
	 * @param partitaIva
	 * @return
	 */
	public AnagraficaCliente loadAnagraficaClientePerigea() {
		try {
			return readAnagraficaCliente(applicationProperties.getPartitaIvaPerigea());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
}
