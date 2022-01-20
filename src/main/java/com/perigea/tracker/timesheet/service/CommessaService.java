package com.perigea.tracker.timesheet.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.configuration.ApplicationProperties;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.wrapper.CommessaFatturabileDtoWrapper;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.keys.OrdineCommessaKey;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
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

	/**
	 * metodo per creare una commessa non fatturabile
	 * @param commessaNonFatturabileDto
	 * @return
	 */
	public CommessaNonFatturabile createCommessaNonFatturabile(CommessaNonFatturabileDto commessaNonFatturabileDto) {
		try {
			AnagraficaCliente anagraficaCliente = anagraficaClienteRepository.findByPartitaIva(applicationProperties.getPartitaIvaPerigea());
			CommessaNonFatturabile commessaNonFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(commessaNonFatturabileDto);
			commessaNonFatturabile.setCodiceCommessa(TSUtils.uuid());
			commessaNonFatturabile.setCliente(anagraficaCliente);
			commessaNonFatturabileRepository.save(commessaNonFatturabile);
			return commessaNonFatturabile;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per leggere i dati di una commessa non fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaNonFatturabile getCommessaNonFatturabile(String codiceCommessa) {
		try {
			CommessaNonFatturabile commessa = commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa);
			if(commessa == null) {
				throw new EntityNotFoundException(String.format("commessa non trovata con il codice %s", codiceCommessa));
			} 
			return commessa;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * metodo per aggiornare i dati di una commessa non fatturabile
	 * @param dtoParam
	 * @return
	 */
	public CommessaNonFatturabile updateCommessaNonFatturabile(CommessaNonFatturabileDto dtoParam) {
		try {
			CommessaNonFatturabile commessaNonFatturabile = commessaNonFatturabileRepository.findByCodiceCommessa(dtoParam.getCommessa().getCodiceCommessa());
			if (commessaNonFatturabile != null) {
				commessaNonFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaNonFatturabile(dtoParam);
				commessaNonFatturabileRepository.save(commessaNonFatturabile);
			}
			return commessaNonFatturabile;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	/**
	 * metodo per eliminare una commessa non fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaNonFatturabile deleteCommessaNonFatturabile(String codiceCommessa) {
		try {
			CommessaNonFatturabile commessaNonFatturabile = commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa);
			if (commessaNonFatturabile != null) {
				commessaNonFatturabileRepository.delete(commessaNonFatturabile);
			}
			return commessaNonFatturabile;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * creazione commessa fatturabile
	 * @param commessaFatturabileDtoWrapper
	 * @return
	 */
	public CommessaFatturabile createCommessaFatturabile(CommessaFatturabileDtoWrapper commessaFatturabileDtoWrapper) {
		try {
			AnagraficaCliente anagraficaEntity = anagraficaClienteRepository.findByPartitaIva(commessaFatturabileDtoWrapper.getAnagraficaCliente().getPartitaIva());
			CommessaFatturabile commessaFatturabile=new CommessaFatturabile();
			if(anagraficaEntity==null) {
				anagraficaEntity = clienteService.createAnagraficaCliente(commessaFatturabileDtoWrapper.getAnagraficaCliente());
				commessaFatturabile.setCodiceCommessa(TSUtils.uuid());
			}

			//Commessa fatturabile
			commessaFatturabile = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(commessaFatturabileDtoWrapper.getCommessaFatturabileDto());
			commessaFatturabile.setCliente(anagraficaEntity);
			commessaFatturabileRepository.save(commessaFatturabile);
			logger.info("CommessaFatturabile creata e salvata a database");
			return commessaFatturabile;
		} catch(Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * metodo per leggere i dati di una commessa  fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaFatturabile readCommessaFatturabile(String codiceCommessa) {
		try {
			CommessaFatturabile commessa = commessaFatturabileRepository.findByCodiceCommessa(codiceCommessa);
			if(commessa == null) {
				throw new EntityNotFoundException(String.format("commessa non trovata con il codice %s", codiceCommessa));
			} 
			return commessa;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * metodo per aggiornare i dati di una commessa fatturabile
	 * @param dtoParam
	 * @return
	 */
	public CommessaFatturabile updateCommessaFatturabile(CommessaFatturabileDto dtoParam) {
		try {
			CommessaFatturabile commessa = commessaFatturabileRepository.findByCodiceCommessa(dtoParam.getCommessa().getCodiceCommessa());
			if (commessa != null) {
				commessa = DtoEntityMapper.INSTANCE.fromDtoToEntityCommessaFatturabile(dtoParam);
				commessaFatturabileRepository.save(commessa);
			}
			return commessa;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
	
	/**
	 * metodo per eliminare una commessa fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaFatturabile deleteCommessaFatturabile(String codiceCommessa) {
		try {
			CommessaFatturabile commessa = commessaFatturabileRepository.findByCodiceCommessa(codiceCommessa);
			if (commessa != null) {
				commessaFatturabileRepository.delete(commessa);
			}
			return commessa;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per creare un ordine commessa
	 * @param commessaFatturabileWrapper
	 * @param ragioneSocialeCliente
	 * @return
	 */
	public OrdineCommessa createOrdineCommessa(CommessaFatturabileDtoWrapper commessaFatturabileWrapper, String ragioneSocialeCliente) {
		try {
			OrdineCommessa entityOrdineCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityOrdineCommessa(commessaFatturabileWrapper.getOrdineCommessa());
			CommessaFatturabile entityCommessaFatturabile = createCommessaFatturabile(commessaFatturabileWrapper);
			OrdineCommessaKey id = new OrdineCommessaKey(entityCommessaFatturabile.getCodiceCommessa(),TSUtils.uuid(),ragioneSocialeCliente);
			entityOrdineCommessa.setId(id);	
			AnagraficaCliente anaCliente=DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaCliente(commessaFatturabileWrapper.getAnagraficaCliente());
			entityOrdineCommessa.setCliente(anaCliente);	
			entityOrdineCommessa.setCommessaFatturabile(entityCommessaFatturabile);
			ordineCommessaRepository.save(entityOrdineCommessa);
			logger.info("Ordine commessa creato e salvato a database");
			return entityOrdineCommessa;
		} catch (Exception ex) {
			throw new CommessaException("Ordine commessa non creata");
		}
	}
	
}