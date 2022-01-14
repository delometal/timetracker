package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.keys.OrdineCommessaKey;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.AnagraficaClienteRepository;
import com.perigea.tracker.timesheet.repository.CommessaFatturabileRepository;
import com.perigea.tracker.timesheet.repository.CommessaNonFatturabileRepository;
import com.perigea.tracker.timesheet.repository.OrdineCommessaRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;



@Service
public class CommessaService {

	@Autowired
	private Logger logger;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private CommessaNonFatturabileRepository commessaNonFatturabileRepository;

	@Autowired
	private CommessaFatturabileRepository commessaFatturabileRepository;
	
	@Autowired
	private OrdineCommessaRepository ordineCommessaRepository;

	@Autowired
	private AnagraficaClienteRepository anagraficaClienteRepository;
	
	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private AnagraficaClienteRepository anagraficaClienteRepo;

	public CommessaFatturabileDto createCommessaFatturabile(CommessaFatturabileDtoWrapper commessaFatturabileDtoWrapper) {
		try {
			AnagraficaCliente anagraficaEntity = anagraficaClienteRepo.findByPartitaIva(commessaFatturabileDtoWrapper.getAnagraficaCliente().getPartitaIva());
			if(anagraficaEntity==null) {
				AnagraficaClienteDto anagraficaDto = clienteService.createCustomerPersonalData(commessaFatturabileDtoWrapper.getAnagraficaCliente());
				anagraficaEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anagraficaDto);
			}
			
			//Commessa fatturabile
			CommessaFatturabile commessaFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(commessaFatturabileDtoWrapper.getCommessaFatturabileDto());
			commessaFatturabile.setCodiceCommessa(TSUtils.uuid());
			commessaFatturabile.setCliente(anagraficaEntity);
			commessaFatturabileRepository.save(commessaFatturabile);
			logger.info("CommessaFatturabile creata e salvata a database");
			CommessaFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(commessaFatturabile);
			return dto;
		} catch(Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	// metodo per creare una commessa no nfatturabile
	public CommessaNonFatturabileDto createCommessaNonFatturabile(CommessaNonFatturabileDto commessaNonFatturabileDto) {
		try {
			AnagraficaCliente anagraficaCliente = anagraficaClienteRepository.findByPartitaIva(applicationProperties.getPartitaIvaPerigea());
			CommessaNonFatturabile commessaNonFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(commessaNonFatturabileDto);
			commessaNonFatturabile.setCodiceCommessa(TSUtils.uuid());
			commessaNonFatturabile.setCliente(anagraficaCliente);
			commessaNonFatturabileRepository.save(commessaNonFatturabile);
			CommessaNonFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessaNonFatturabile);
			return dto;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	// metodo per leggere i dati di una commessa non fatturabile
	public CommessaNonFatturabileDto getCommessaNonFatturabile(String codiceCommessa) {
		try {
			CommessaNonFatturabile commessa = commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa);
			return DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessa);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// metodo per aggiornari i dati di una commessa non fatturabile
	public CommessaNonFatturabileDto updateCommessaNonFatturabile(CommessaNonFatturabileDto dtoParam) {
		try {
			CommessaNonFatturabile entity = commessaNonFatturabileRepository.findByCodiceCommessa(dtoParam.getCommessa().getCodiceCommessa());
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(dtoParam);
				commessaNonFatturabileRepository.save(entity);
			}
			CommessaNonFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// metodo per eliminare una commessa non fatturabile
	public CommessaNonFatturabileDto deleteCommessaNonFatturabile(String codiceCommessa) {
		try {
			CommessaNonFatturabile entity = commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa);
			if (entity != null) {
				commessaNonFatturabileRepository.delete(entity);
			}
			CommessaNonFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(entity);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	 //metodo per creare un ordine commessa
	public OrdineCommessaDto createOrdineCommessa(CommessaFatturabileDtoWrapper bodyConverter, String numeroOrdineCliente, String ragioneSocialeCliente) {
		try {
			OrdineCommessa entityOrdineCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityOrdineCommessa(bodyConverter.getOrdineCommessa());
			CommessaFatturabileDto commessaFatturabileDto = createCommessaFatturabile(bodyConverter);
			CommessaFatturabile entityCommessaFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(commessaFatturabileDto);
			OrdineCommessaKey id = new OrdineCommessaKey(entityCommessaFatturabile.getCodiceCommessa(),numeroOrdineCliente,ragioneSocialeCliente);
			entityOrdineCommessa.setId(id);			
			ordineCommessaRepository.save(entityOrdineCommessa);

			logger.info("Ordine commessa creato e salvato a database");
			OrdineCommessaDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoOrdineCommessa(entityOrdineCommessa);
			return dto;
		} catch (Exception ex) {
			throw new CommessaException("Ordine commessa non creata");
		}
	}
}
