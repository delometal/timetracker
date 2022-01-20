package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.keys.OrdineCommessaKey;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.repository.AnagraficaClienteRepository;
import com.perigea.tracker.timesheet.repository.CommessaFatturabileRepository;
import com.perigea.tracker.timesheet.repository.CommessaNonFatturabileRepository;
import com.perigea.tracker.timesheet.repository.OrdineCommessaRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
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

	
	public CommessaFatturabile createCommessaFatturabile(CommessaFatturabileDtoWrapper commessaFatturabileDtoWrapper) {
		try {
			AnagraficaCliente anagraficaEntity = anagraficaClienteRepo.findByPartitaIva(commessaFatturabileDtoWrapper.getAnagraficaCliente().getPartitaIva());
			CommessaFatturabile commessaFatturabile=new CommessaFatturabile();
			if(anagraficaEntity==null) {
//				AnagraficaClienteDto anagraficaDto = clienteService.createCustomerPersonalData(commessaFatturabileDtoWrapper.getAnagraficaCliente());
//				anagraficaEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anagraficaDto);
				anagraficaEntity = clienteService.createCustomerPersonalData(commessaFatturabileDtoWrapper.getAnagraficaCliente());
				commessaFatturabile.setCodiceCommessa(TSUtils.uuid());
			}

			//Commessa fatturabile
			commessaFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(commessaFatturabileDtoWrapper.getCommessaFatturabileDto());
			commessaFatturabile.setCliente(anagraficaEntity);
			commessaFatturabileRepository.save(commessaFatturabile);
			logger.info("CommessaFatturabile creata e salvata a database");
//			CommessaFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(commessaFatturabile);
//			return dto;
			return commessaFatturabile;
		} catch(Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	// metodo per creare una commessa no nfatturabile
	public CommessaNonFatturabile createCommessaNonFatturabile(CommessaNonFatturabileDto commessaNonFatturabileDto) {
		try {
			AnagraficaCliente anagraficaCliente = anagraficaClienteRepository.findByPartitaIva(applicationProperties.getPartitaIvaPerigea());
			CommessaNonFatturabile commessaNonFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(commessaNonFatturabileDto);
			commessaNonFatturabile.setCodiceCommessa(TSUtils.uuid());
			commessaNonFatturabile.setCliente(anagraficaCliente);
			commessaNonFatturabileRepository.save(commessaNonFatturabile);
//			CommessaNonFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessaNonFatturabile);
//			return dto;
			return commessaNonFatturabile;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	// metodo per leggere i dati di una commessa non fatturabile
	public CommessaNonFatturabile getCommessaNonFatturabile(String codiceCommessa) {
		try {
			CommessaNonFatturabile commessa = commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa);
//			return DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessa);
			return commessa;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// metodo per aggiornari i dati di una commessa non fatturabile
	public CommessaNonFatturabile updateCommessaNonFatturabile(CommessaNonFatturabileDto dtoParam) {
		try {
			CommessaNonFatturabile commessaNonFatturabile = commessaNonFatturabileRepository.findByCodiceCommessa(dtoParam.getCommessa().getCodiceCommessa());
			if (commessaNonFatturabile != null) {
				commessaNonFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(dtoParam);
				commessaNonFatturabileRepository.save(commessaNonFatturabile);
			}
//			CommessaNonFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(entity);
//			return dto;
			return commessaNonFatturabile;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// metodo per eliminare una commessa non fatturabile
	public CommessaNonFatturabile deleteCommessaNonFatturabile(String codiceCommessa) {
		try {
			CommessaNonFatturabile commessaNonFatturabile = commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa);
			if (commessaNonFatturabile != null) {
				commessaNonFatturabileRepository.delete(commessaNonFatturabile);
			}
//			CommessaNonFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(entity);
//			return dto;
			return commessaNonFatturabile;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	//metodo per creare un ordine commessa
	public OrdineCommessa createOrdineCommessa(CommessaFatturabileDtoWrapper commessaFatturabileWrapper, String ragioneSocialeCliente) {
		try {
			OrdineCommessa entityOrdineCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityOrdineCommessa(commessaFatturabileWrapper.getOrdineCommessa());
//			CommessaFatturabileDto commessaFatturabileDto = createCommessaFatturabile(commessaFatturabileWrapper);
//			CommessaFatturabile entityCommessaFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(commessaFatturabileDto);
			CommessaFatturabile entityCommessaFatturabile = createCommessaFatturabile(commessaFatturabileWrapper);
			OrdineCommessaKey id = new OrdineCommessaKey(entityCommessaFatturabile.getCodiceCommessa(),TSUtils.uuid(),ragioneSocialeCliente);
			entityOrdineCommessa.setId(id);	
			AnagraficaCliente anaCliente=DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(commessaFatturabileWrapper.getAnagraficaCliente());
			entityOrdineCommessa.setCliente(anaCliente);	
			entityOrdineCommessa.setCommessaFatturabile(entityCommessaFatturabile);
			ordineCommessaRepository.save(entityOrdineCommessa);
			logger.info("Ordine commessa creato e salvato a database");
//			OrdineCommessaDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoOrdineCommessa(entityOrdineCommessa);
//			return dto;
			return entityOrdineCommessa;
		} catch (Exception ex) {
			throw new CommessaException("Ordine commessa non creata");
		}
	}
	
}