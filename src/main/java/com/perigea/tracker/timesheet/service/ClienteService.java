package com.perigea.tracker.timesheet.service;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.exception.ClienteException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.AnagraficaClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private Logger logger;

	@Autowired
	private AnagraficaClienteRepository anagraficaClienteRepository;

	public AnagraficaClienteDto createCustomerPersonalData(AnagraficaClienteDto anaClienteDto) {
		try {
			AnagraficaCliente entity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
			anagraficaClienteRepository.save(entity);
			logger.info("Dati anagrafici cliente persistiti");
			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	public AnagraficaClienteDto readCustomerPersonalData(String partitaIva) {
		try {
			AnagraficaCliente entity = anagraficaClienteRepository.findByPartitaIva(partitaIva);
			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	public AnagraficaClienteDto updateCustomerPersonalData(AnagraficaClienteDto anaClienteDto) {
		try {
			AnagraficaCliente entity = anagraficaClienteRepository.findByPartitaIva(anaClienteDto.getPartitaIva());
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
				entity.setLastUpdateTimestamp(new Date());
				logger.info("Anagrafica Cliente Aggiornata");
				anagraficaClienteRepository.save(entity);
			}
			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	public AnagraficaClienteDto deleteCustomerPersonalData(String partitaIva) {
		try {
			AnagraficaCliente entity = anagraficaClienteRepository.findByPartitaIva(partitaIva);
			if (entity != null) {
				anagraficaClienteRepository.delete(entity);
			}
			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}
