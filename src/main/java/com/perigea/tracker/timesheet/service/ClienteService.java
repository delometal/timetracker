package com.perigea.tracker.timesheet.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.ClienteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.Cliente;
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
	
	/**
	 * creazione anagrafica cliente
	 * @param anaClienteDto
	 * @return
	 */
	public Cliente saveCliente(Cliente cliente) {
		try {
			if(Utils.isEmpty(cliente.getCodiceAzienda())) {
				cliente.setCodiceAzienda(Utils.uuid());
			}
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
	public Cliente readClienteById(String id) {
		try {
			return clienteRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new ClienteException(ex.getMessage());
		}
	}

	/**
	 * lettura anagrafica cliente
	 * @param partitaIva
	 * @return
	 */
	public Cliente readClienteByPartitaIva(String partitaIva) {
		try {
			return clienteRepository.findByPartitaIva(partitaIva).get();
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
	public void deleteClienteById(String id) {
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
	public void deleteClienteByParitaIva(String partitaIva) {
		try {
			clienteRepository.deleteById(readClienteByPartitaIva(partitaIva).getCodiceAzienda());
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
			return readClienteByPartitaIva(applicationProperties.getPartitaIvaPerigea());
		} catch (Exception ex) {
			throw ex;
		}
	}
		
	/**
	 * Legge tutti i clienti
	 * @return
	 */
	public List<Cliente> readAll() {
		try {
			return clienteRepository.findAll();
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}
	
}
