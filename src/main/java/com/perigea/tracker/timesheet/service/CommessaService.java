package com.perigea.tracker.timesheet.service;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.CommessaDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.CommessaFatturabileRepository;
import com.perigea.tracker.timesheet.repository.CommessaNonFatturabileRepository;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;
import com.perigea.tracker.timesheet.wrapper.CommessaFatturabileWrapper;

@Service
public class CommessaService {

	@Autowired
	private Logger logger;

	@Autowired
	private CommessaNonFatturabileRepository commessaNonFatturabileRepo;

	@Autowired
	private CommessaFatturabileRepository commessaFatturabileRepo;

	@Autowired
	private CommessaRepository commessaRepo;

	@Autowired
	private ClienteService trackerClient;
	

	// metodo per creare una commessa fatturabile
//	public CommessaFatturabileDto createCommessaFatturabile(CommessaFatturabileDto commessaFatturabileDto,
//			AnagraficaClienteDto anagraficaClienteDto, CommessaDto dtoCommessa) {
//		try {
//			CommessaFatturabile entity = new CommessaFatturabile();
//			AnagraficaClienteDto anaClienteDto = trackerClient.createCustomerPersonalData(anagraficaClienteDto);
//			AnagraficaCliente anaEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(anaClienteDto);
//			CommessaDto commessaDto = createCommessa(dtoCommessa);
//			Commessa commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(commessaDto);
//			entity = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(commessaFatturabileDto);
//			entity.setCodiceCommessa(commessa.getCodiceCommessa());
//			entity.setRagioneSociale(anaEntity);
//			anaEntity.addCommessaFat(entity);
//			entity.setCommessaFatturabile(commessa);
//			commessa.setCommessaFatturabile(entity);
//		
//			commessaFatturabileRepo.save(entity);
//			logger.info("CommessaFatturabile creata e salvata a database");
//			CommessaFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(entity);
//			return dto;
//		} catch (Exception ex) {
//			throw new CommessaException(ex.getMessage());
//		}
//	}

	public CommessaFatturabileDto createCommessaFatturabile(CommessaFatturabileWrapper bodyConverter) {
		try {
			CommessaFatturabile entity=new CommessaFatturabile();

			//AnagraficaCliente relazionata
			AnagraficaClienteDto ana=trackerClient.createCustomerPersonalData(bodyConverter.getAnagraficaCliente());
			AnagraficaCliente anaEntity=DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(ana);
			entity.setRagioneSociale(anaEntity);
//			anaEntity.addCommessaFat(entity);
			//Commessa relazionata
			CommessaDto commessaDto=createCommessa(bodyConverter.getDtoCommessa());
			Commessa commessa=DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(commessaDto);
			entity.setCommessaFatturabile(commessa);
			commessa.setCommessaFatturabile(entity);
			entity=DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(bodyConverter.getCommessaFatturabileDto());
			entity.setCodiceCommessa(commessa.getCodiceCommessa());
			commessaFatturabileRepo.save(entity);
			logger.info("CommessaFatturabile creata e salvata a database");
			CommessaFatturabileDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(entity);
			return dto;
		} catch(Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
//	public CommessaFatturabileDto createCommessaFatturabile(CommessaFatturabileWrapper bodyConverter) {
//		try {
//			CommessaFatturabile entity = new CommessaFatturabile();
//
//			// AnagraficaCliente relazionata
//			AnagraficaClienteDto ana = trackerClient.createCustomerPersonalData(bodyConverter.getAnagraficaCliente());
//			AnagraficaCliente anaEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(ana);
//			entity.setRagioneSociale(anaEntity);
//			// Commessa relazionata
//			CommessaDto commessaDto = createCommessa(bodyConverter.getDtoCommessa());
//			Commessa commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(commessaDto);
//			entity.setCommessaFatturabile(commessa);
//			commessa.setCommessaFatturabile(entity);
//
//			entity.setCreateUser("");
//			Date date = new Date();
//			entity.setDataInizioCommessa(date);
//			entity.setTipoCommessaFatturabileType(bodyConverter.getCommessaFatturabileDto().getTipoCommessa());
//			entity.setDataFineCommessa(bodyConverter.getCommessaFatturabileDto().getDataFineCommessa());
//			entity.setImportoCommessaInizialePresunto(
//					bodyConverter.getCommessaFatturabileDto().getImportoCommessaInizialePresunto());
//			entity.setDescrizioneCommessaCliente(
//					bodyConverter.getCommessaFatturabileDto().getDescrizioneCommessaCliente());
//			entity.setDescrizioneCommessaPerigea(
//					bodyConverter.getCommessaFatturabileDto().getDescrizioneCommessaPerigea());
//			entity.setMargineDaInizioAnno(bodyConverter.getCommessaFatturabileDto().getMargineDaInizioAnno());
//			entity.setMargineDaInizioCommessa(bodyConverter.getCommessaFatturabileDto().getMargineDaInizioCommessa());
//			entity.setMargineIniziale(bodyConverter.getCommessaFatturabileDto().getMargineIniziale());
//			entity.setOrdineInternoCorrente(bodyConverter.getCommessaFatturabileDto().getOrdineInternoCorrente());
//			entity.setResponsabileCommerciale(bodyConverter.getCommessaFatturabileDto().getResponsabileCommerciale());
//			entity.setPercentualeAvanzamentoCosti(
//					bodyConverter.getCommessaFatturabileDto().getPercentualeAvanzamentoCosti());
//			entity.setPercentualeAvanzamentoFatturazione(
//					bodyConverter.getCommessaFatturabileDto().getPercentualeAvanzamentoFatturazione());
//			entity.setPercentualeSconto(bodyConverter.getCommessaFatturabileDto().getPercentualeSconto());
//			entity.setTotaleCostiDaInizioAnno(bodyConverter.getCommessaFatturabileDto().getTotaleCostiDaInizioAnno());
//			entity.setTotaleCostiDaInizioCommessa(
//					bodyConverter.getCommessaFatturabileDto().getTotaleCostiDaInizioCommessa());
//			entity.setTotaleEstensioni(bodyConverter.getCommessaFatturabileDto().getTotaleEstensioni());
//			entity.setTotaleFatturatoDaInizioAnno(
//					bodyConverter.getCommessaFatturabileDto().getTotaleFatturatoDaInizioAnno());
//			entity.setTotaleFatturatoreDaInizioCommessa(
//					bodyConverter.getCommessaFatturabileDto().getTotaleFatturatoreDaInizioCommessa());
//			entity.setTotaleOrdine(bodyConverter.getCommessaFatturabileDto().getTotaleOrdine());
//			entity.setTotaleOrdineClienteFormale(
//					bodyConverter.getCommessaFatturabileDto().getTotaleOrdineClienteFormale());
//			entity.setTotaleRicaviDaInizioAnno(bodyConverter.getCommessaFatturabileDto().getTotaleRicaviDaInizioAnno());
//			entity.setTotaleRicaviDaInizioCommessa(
//					bodyConverter.getCommessaFatturabileDto().getTotaleRicaviDaInizioCommessa());
//			commessaFatturabileRepo.save(entity);
//			logger.info("CommessaFatturabile creata e salvata a database");
//			CommessaFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaFatturabile(entity);
//			return dto;
//		} catch (Exception ex) {
//			throw new CommessaException(ex.getMessage());
//		}
//	}

	// metodo per creare una commessa no nfatturabile
	public CommessaNonFatturabileDto createCommessaNonFatturabile(CommessaNonFatturabileDto dtoParam,
			CommessaDto dtoCommessa) {
		try {
			CommessaNonFatturabile entity = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(dtoParam);
//			CommessaDto commessaDto = createCommessa(dtoCommessa);
			Commessa commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(dtoCommessa);
			entity.setCommessaNonFatturabile(commessa);
			commessa.setCommessaNonFatturabile(entity);
			commessaNonFatturabileRepo.save(entity);
			CommessaNonFatturabileDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(entity);
			return dto;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	//metodo per creare una commessa no nfatturabile
//	public CommessaNonFatturabileDto createCommessaNonFatturabile(CommessaNonFatturabileDto dtoParam,CommessaDto dtoCommessa) {
//		try {
//			CommessaNonFatturabile entity=new CommessaNonFatturabile();
//			CommessaDto commessaDto=createCommessa(dtoCommessa);
//			Commessa commessa=DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(commessaDto);
//			entity.setCommessaNonFatturabile(commessa);
//			entity.setCreateUser("");
//			entity.setDescrizione(dtoParam.getDescrizione());
//			commessa.setCommessaNonFatturabile(entity);
//			commessaNonFatturabileRepo.save(entity);
//			CommessaNonFatturabileDto dto=DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(entity);
//			return dto;
//		}catch(Exception ex) {
//			throw new CommessaException("Commessa non creata " + ex.getMessage());
//		}
//	}

	// metodo per creare una commessa
	public CommessaDto createCommessa(CommessaDto dto) {
		try {
			Commessa entity = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessa(dto);
			entity.setCodiceCommessa(TSUtils.UUDI());
			commessaRepo.save(entity);
			return DtoEntityMapper.INSTANCE.fromEntityToDtoCommessa(entity);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	// metodo per eliminare una commessa
	public CommessaDto deleteCommessa(String codiceCommessa) {
		try {
			Commessa entity = commessaRepo.findByCodiceCommessa(codiceCommessa);
			if (entity != null) {
				commessaRepo.delete(entity);
			}
			return DtoEntityMapper.INSTANCE.fromEntityToDtoCommessa(entity);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// metodo per leggere i dati di una commessa non fatturabile
	public CommessaNonFatturabileDto readCommessaNonFatturabile(String codiceCommessa) {
		try {
			CommessaNonFatturabile commessa = commessaNonFatturabileRepo.findByCodiceCommessa(codiceCommessa);
			return DtoEntityMapper.INSTANCE.fromEntityToDtoCommessaNonFatturabile(commessa);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// metodo per aggiornari i dati di una commessa non fatturabile
	public CommessaNonFatturabileDto updateCommessaNonFatturabile(CommessaNonFatturabileDto dtoParam) {
		try {
			CommessaNonFatturabile entity = commessaNonFatturabileRepo
					.findByCodiceCommessa(dtoParam.getCodiceCommessa());
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(dtoParam);
				commessaNonFatturabileRepo.save(entity);
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
			CommessaNonFatturabile entity = commessaNonFatturabileRepo.findByCodiceCommessa(codiceCommessa);
			if (entity != null) {
				commessaNonFatturabileRepo.delete(entity);
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
