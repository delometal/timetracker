package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.enumerator.CommessaType;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.CommessaFatturabileRepository;
import com.perigea.tracker.timesheet.repository.CommessaNonFatturabileRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;

@Service
public class CommessaService {

	@Autowired
	private Logger logger;

	@Autowired
	private CommessaNonFatturabileRepository commessaNonFatturabileRepository;

	@Autowired
	private CommessaFatturabileRepository commessaFatturabileRepository;
	
	@Autowired
	private ClienteService clienteService;

	public CommessaFatturabileDto createCommessaFatturabile(CommessaFatturabileDtoWrapper commessaFatturabileDtoWrapper) {
		try {
			//AnagraficaCliente relazionata
			AnagraficaClienteDto anagraficaDto = clienteService.createCustomerPersonalData(commessaFatturabileDtoWrapper.getAnagraficaCliente());
			AnagraficaCliente anagraficaEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anagraficaDto);
			
			String codiceCommessa = TSUtils.uuid();
			
			//Commessa relazionata
			Commessa commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(commessaFatturabileDtoWrapper.getCommessaFatturabileDto().getCommessa());
			commessa.setCommessaType(CommessaType.F);
			commessa.setCodiceCommessa(codiceCommessa);
			
			//Commessa fatturabile
			CommessaFatturabile commessaFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(commessaFatturabileDtoWrapper.getCommessaFatturabileDto());
			commessaFatturabile.setCodiceCommessa(codiceCommessa);
			commessaFatturabile.setCliente(anagraficaEntity);
			commessaFatturabile.setCommessa(commessa);
			
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
			String codiceCommessa = TSUtils.uuid();
			
			//Commessa relazionata
			Commessa commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(commessaNonFatturabileDto.getCommessa());
			commessa.setCodiceCommessa(codiceCommessa);
			commessa.setCommessaType(CommessaType.NF);
			
			CommessaNonFatturabile commessaNonFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(commessaNonFatturabileDto);
			commessaNonFatturabile.setCodiceCommessa(codiceCommessa);
			commessaNonFatturabile.setCommessa(commessa);
			
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
			CommessaNonFatturabile entity = commessaNonFatturabileRepository.findByCodiceCommessa(dtoParam.getCodiceCommessa());
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

	// metodo per creare un ordine commessa
//	public OrdineCommessa createOrdineCommessa(CommessaFatturabileWrapper bodyConverter) {
//		try {
//			OrdineCommessa entityOrdineCommessa = new OrdineCommessa();
//			CommessaFatturabileDto commessaFatturabileDto = createCommessaFatturabile(
//					bodyConverter.getCommessaFatturabileDto(), bodyConverter.getAnagraficaCliente(),
//					bodyConverter.getDtoCommessa());
//			CommessaFatturabile entityCommessaFatturabile = DtoEntityMapper.INSTANCE
//					.fromDtoToEntityCommessaFatturabile(commessaFatturabileDto);
//			entityOrdineCommessa.setCommessaFatturabile(entityCommessaFatturabile);
//			entityCommessaFatturabile.setOrdineCommessa(entityOrdineCommessa);
//			entityOrdineCommessa.setCreateUser("");
//			entityOrdineCommessa.setDataInizio(bodyConverter.getOrdineCommessa().getDataInizio());
//			entityOrdineCommessa.setDataFine(bodyConverter.getOrdineCommessa().getDataFine());
//			entityOrdineCommessa.setDataOrdine(bodyConverter.getOrdineCommessa().getDataOrdine());
//			entityOrdineCommessa.setImportoOrdine(bodyConverter.getOrdineCommessa().getImportoOrdine());
//			entityOrdineCommessa.setImportoResiduo(bodyConverter.getOrdineCommessa().getImportoResiduo());
//			// entityOrdineCommessa.setNumeroOrdineCliente(bodyConverter.getOrdineCommessa().getNumeroOrdineCliente());
//			ordineCommessaRepo.save(entityOrdineCommessa);
//			logger.info("Ordine commessa creato e salvato a database");
//			return entityOrdineCommessa;
//		} catch (Exception ex) {
//			throw new CommessaException("Ordine commessa non creata");
//		}
//	}
}
