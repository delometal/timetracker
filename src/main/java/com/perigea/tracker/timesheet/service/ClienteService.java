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

	public AnagraficaCliente createCustomerPersonalData(AnagraficaClienteDto anaClienteDto) {
		try {
			AnagraficaCliente anagraficaClienteEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
			anagraficaClienteRepository.save(anagraficaClienteEntity);
			logger.info("Dati anagrafici cliente persistiti");
//			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return anagraficaClienteEntity;
		} catch (Exception ex) {
			throw new ClienteException(ex.getMessage());
		}
	}

	public AnagraficaCliente readCustomerPersonalData(String partitaIva) {
		try {
			AnagraficaCliente anagraficaClienteEntity = anagraficaClienteRepository.findByPartitaIva(partitaIva);
//			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return anagraficaClienteEntity;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	public AnagraficaCliente updateCustomerPersonalData(AnagraficaClienteDto anaClienteDto) {
		try {
			AnagraficaCliente anagraficaClienteEntity = anagraficaClienteRepository.findByPartitaIva(anaClienteDto.getPartitaIva());
			if (anagraficaClienteEntity != null) {
				anagraficaClienteEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
				anagraficaClienteEntity.setLastUpdateTimestamp(new Date());
				logger.info("Anagrafica Cliente Aggiornata");
				anagraficaClienteRepository.save(anagraficaClienteEntity);
			}
//			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return anagraficaClienteEntity;
			} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	public AnagraficaCliente deleteCustomerPersonalData(String partitaIva) {
		try {
			AnagraficaCliente anagraficaClienteEntity = anagraficaClienteRepository.findByPartitaIva(partitaIva);
			if (anagraficaClienteEntity != null) {
				anagraficaClienteRepository.delete(anagraficaClienteEntity);
			}
//			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return anagraficaClienteEntity;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}
