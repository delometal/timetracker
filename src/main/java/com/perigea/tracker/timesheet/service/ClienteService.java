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
			logger.info("Entity dati anagrafici cliente creato e aggiunto a database");
			AnagraficaClienteDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return dto;
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	public AnagraficaClienteDto readCustomerPersonalData(String ragioneSociale) {
		try {
			AnagraficaCliente entity = anagraficaClienteRepository
					.findByRagioneSocialeCliente(ragioneSociale);
			AnagraficaClienteDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	public AnagraficaClienteDto updateCustomerPersonalData(AnagraficaClienteDto anaClienteDto) {
		try {
			AnagraficaCliente entity = anagraficaClienteRepository
					.findByRagioneSocialeCliente(anaClienteDto.getRagioneSocialeCliente());
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
				entity.setLastUpdateTimestamp(new Date());
				logger.info("Anagrafica Cliente Aggiornata");
				anagraficaClienteRepository.save(entity);
			}
			AnagraficaClienteDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	public AnagraficaClienteDto deleteCustomerPersonalData(String ragioneSociale) {
		try {
			AnagraficaCliente entity = anagraficaClienteRepository
					.findByRagioneSocialeCliente(ragioneSociale);
			if (entity != null) {
				anagraficaClienteRepository.delete(entity);
			}
			AnagraficaClienteDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}
