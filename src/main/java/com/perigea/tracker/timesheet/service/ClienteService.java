package com.perigea.tracker.timesheet.service;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.controller.UtenteController;
import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.exception.ClienteException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.AnagraficaClienteRepository;


@Service
public class ClienteService {
	
	@Autowired
	private Logger logger;
	
	@Autowired
	private AnagraficaClienteRepository anagraficaClienteRepo;
	
	public AnagraficaClienteDto createCustomerPersonalData(AnagraficaClienteDto dto) {
		try {
			AnagraficaCliente entity=DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(dto);
			anagraficaClienteRepo.save(entity);
			logger.info("Entity dati anagrafici cliente creato e aggiunto a database");
			AnagraficaClienteDto dtoParam=DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return dtoParam;
		} catch (Exception ex) {
			throw new ClienteException("Anagrafica cliente non creata");
		}
	}
	
	public void readCustomerPersonalData(AnagraficaClienteDto dtoParam) {
		try {
			anagraficaClienteRepo.findByRagioneSocialeCliente(dtoParam.getRagioneSocialeCliente());
		} catch (Exception ex) {
			throw new EntityNotFoundException("Anagrafica cliente non trovata");
		}
	}
	
	public void updateCustomerPersonalData(AnagraficaClienteDto dtoParam) {
		try {
			AnagraficaCliente entity=anagraficaClienteRepo.findByRagioneSocialeCliente(dtoParam.getRagioneSocialeCliente());
			if(entity != null) {
				Date date=new Date();
				entity.setRagioneSocialeCliente(dtoParam.getRagioneSocialeCliente());
				entity.setAcronimoCliente(dtoParam.getAcronimoCliente());
				entity.setCodiceDestinatario(dtoParam.getCodiceDestinatario());
				entity.setCodiceFiscale(dtoParam.getCodiceFiscale());
				entity.setNotePerLaFatturazione(dtoParam.getNotePerLaFatturazione());
				entity.setPartitaIva(dtoParam.getPartitaIva());
				entity.setProgressivoPerCommesse(dtoParam.getProgressivoPerCommesse());
				entity.setSedeLegaleCap(dtoParam.getAcronimoCliente());
				entity.setSedeOperativaComune(dtoParam.getSedeLegaleComune());
				entity.setSedeOperativaIndirizzo(dtoParam.getSedeLegaleIndirizzo());
				entity.setLastUpdateTimestamp(date);
				entity.setLastUpdateUser("");
				anagraficaClienteRepo.save(entity);
			}
		} catch (Exception ex) {
			throw new EntityNotFoundException("Anagrafica cliente non trovata");
		}
	}
	
	public AnagraficaClienteDto deleteCustomerPersonalData(AnagraficaClienteDto dtoParam) {
		try {
			AnagraficaCliente entity=anagraficaClienteRepo.findByRagioneSocialeCliente(dtoParam.getRagioneSocialeCliente());
			if(entity != null) {
				anagraficaClienteRepo.delete(entity);
			}
			AnagraficaClienteDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaCliente(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException("Anagrafica cliente non trovata");
		}
	}
	

}
