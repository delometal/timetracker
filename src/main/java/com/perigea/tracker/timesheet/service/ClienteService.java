package com.perigea.tracker.timesheet.service;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.exception.ClienteException;
import com.perigea.tracker.timesheet.repository.AnagraficaClienteRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@Service
public class ClienteService {

	@Autowired
	private Logger logger;

	@Autowired
	private AnagraficaClienteRepository anagraficaClienteRepository;

	/**
	 * creazione anagrafica cliente
	 * @param anaClienteDto
	 * @return
	 */
	public AnagraficaCliente createAnagraficaCliente(AnagraficaClienteDto anaClienteDto) {
		try {
			AnagraficaCliente anagraficaClienteEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
			anagraficaClienteRepository.save(anagraficaClienteEntity);
			logger.info("Dati anagrafici cliente persistiti");
			return anagraficaClienteEntity;
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	/**
	 * lettura anagrafica cliente
	 * @param partitaIva
	 * @return
	 */
	public AnagraficaCliente readAnagraficaCliente(String partitaIva) {
		try {
			AnagraficaCliente anagraficaClienteEntity = anagraficaClienteRepository.findByPartitaIva(partitaIva);
			return anagraficaClienteEntity;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	/**
	 * update anagrafica cliente
	 * @param anaClienteDto
	 * @return
	 */
	public AnagraficaCliente updateAnagraficaCliente(AnagraficaClienteDto anaClienteDto) {
		try {
			AnagraficaCliente anagraficaClienteEntity = anagraficaClienteRepository.findByPartitaIva(anaClienteDto.getPartitaIva());
			if (anagraficaClienteEntity != null) {
				anagraficaClienteEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
				anagraficaClienteEntity.setLastUpdateTimestamp(new Date());
				logger.info("Anagrafica Cliente Aggiornata");
				anagraficaClienteRepository.save(anagraficaClienteEntity);
			}
			return anagraficaClienteEntity;
			} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	/**
	 * cancellazione anagrafica cliente
	 * @param partitaIva
	 * @return
	 */
	public AnagraficaCliente deleteAnagraficaCliente(String partitaIva) {
		try {
			AnagraficaCliente anagraficaClienteEntity = anagraficaClienteRepository.findByPartitaIva(partitaIva);
			if (anagraficaClienteEntity != null) {
				anagraficaClienteRepository.delete(anagraficaClienteEntity);
			}
			return anagraficaClienteEntity;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}
