package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.ClienteException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.repository.AnagraficaRepository;
import com.perigea.tracker.timesheet.repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private AnagraficaRepository anagraficaRepository;

	/**
	 * creazione anagrafica cliente
	 * @param anaClienteDto
	 * @return
	 */
	public Cliente saveCliente(Cliente cliente) {
		try {
			return clienteRepository.save(cliente);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	/**
	 * lettura anagrafica cliente
	 * @param partitaIva
	 * @return
	 */
	public Cliente readCliente(String id) {
		try {
			return clienteRepository.findByPartitaIva(id).get();
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
	public void deleteCliente(String id) {
		try {
			clienteRepository.deleteById(id);
			logger.info("Cliente cancellato");
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
	public Cliente loadClientePerigea() {
		try {
			return readCliente(applicationProperties.getPartitaIvaPerigea());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * add contatto
	 * @param cliente
	 * @param contatto
	 */
	public void addContatto(Cliente cliente, Anagrafica contatto) {
		try {
			contatto.setCodicePersona(Utils.uuid());
			cliente.addContatto(contatto);
			clienteRepository.save(cliente);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}
	
	/**
	 * remove contatto
	 * @param cliente
	 * @param contatto
	 */
	public void removeContatto(Cliente cliente, Anagrafica contatto) {
		try {
			cliente.removeContatto(contatto);
			clienteRepository.save(cliente);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}
	
	/**
	 * delete contatto
	 * @param fornitore
	 * @param contatto
	 */
	public void deleteContatto(Cliente cliente, Anagrafica contatto) {
		try {
			cliente.removeContatto(contatto);
			clienteRepository.save(cliente);
			anagraficaRepository.deleteById(contatto.getCodicePersona());
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}
	
}
