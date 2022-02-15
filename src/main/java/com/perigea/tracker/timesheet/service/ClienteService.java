package com.perigea.tracker.timesheet.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.ClienteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.exception.FornitoreException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.repository.ClienteRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

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
	private UtenteRepository utenteRepository;

	/**
	 * creazione anagrafica cliente
	 * @param anaClienteDto
	 * @return
	 */
	public Cliente saveCliente(Cliente cliente) {
		try {
			cliente.setCodiceAzienda(Utils.uuid());
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
	 * add contatto
	 * @param cliente
	 * @param contatto
	 */
	public void addContatto(Cliente cliente, Utente contatto) {
		try {
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
	public void removeContatto(Cliente cliente, Utente contatto) {
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
	public void deleteContatto(Cliente cliente, Utente contatto) {
		try {
			cliente.removeContatto(contatto);
			clienteRepository.save(cliente);
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
