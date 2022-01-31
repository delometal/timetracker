package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.keys.OrdineCommessaKey;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.repository.ClienteRepository;
import com.perigea.tracker.timesheet.repository.CommessaFatturabileRepository;
import com.perigea.tracker.timesheet.repository.CommessaNonFatturabileRepository;
import com.perigea.tracker.timesheet.repository.OrdineCommessaRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;

@Service
@Transactional
public class CommessaService {

	@Autowired
	private Logger logger;

	@Autowired
	private CommessaNonFatturabileRepository commessaNonFatturabileRepository;

	@Autowired
	private CommessaFatturabileRepository commessaFatturabileRepository;

	@Autowired
	private OrdineCommessaRepository ordineCommessaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	/**
	 * @param commessaNonFatturabile
	 * metodo per creare o aggiornate una commessa non fatturabile
	 * @return
	 */
	public CommessaNonFatturabile saveCommessaNonFatturabile(CommessaNonFatturabile commessa) {
		try {
			return commessaNonFatturabileRepository.save(commessa);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per leggere i dati di una commessa non fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaNonFatturabile readCommessaNonFatturabile(String codiceCommessa) {
		try {
			return commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per eliminare una commessa non fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public void deleteCommessaNonFatturabile(String codiceCommessa) {
		try {
			commessaNonFatturabileRepository.deleteById(codiceCommessa);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CommessaException(ex.getMessage());
		}
	}
	
	
	/**
	 *************************************** FATTURABILE **************************************
	 */
	
	/**
	 * creazione commessa fatturabile
	 * @param commessaFatturabileDtoWrapper
	 * @return
	 */
	public CommessaFatturabile createCommessaFatturabile(CommessaFatturabile commessa, Cliente cliente) {
		try {
			if(clienteRepository.findByPartitaIva(cliente.getPartitaIva()) == null) {
				cliente = clienteRepository.save(cliente);
			}
			commessa.setCliente(cliente);
			commessa.setCodiceCommessa(TSUtils.uuid());
			commessaFatturabileRepository.save(commessa);
			logger.info("CommessaFatturabile creata e salvata a database");
			return commessa;
		} catch(Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * metodo per leggere i dati di una commessa fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaFatturabile readCommessaFatturabile(final String codiceCommessa) {
		try {
			return commessaFatturabileRepository.findByCodiceCommessa(codiceCommessa).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * metodo per aggiornare i dati di una commessa fatturabile
	 * @param dtoParam
	 * @return
	 */
	public CommessaFatturabile updateCommessaFatturabile(CommessaFatturabile commessa) {
		try {
			return commessaFatturabileRepository.save(commessa);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * metodo per eliminare una commessa fatturabile
	 * @param codiceCommessa
	 * @return
	 */
	public void deleteCommessaFatturabile(final String codiceCommessa) {
		try {
			commessaFatturabileRepository.deleteById(codiceCommessa);
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
	public OrdineCommessa createOrdineCommessa(OrdineCommessa ordineCommessa, CommessaFatturabile commessa, Cliente cliente) {
		try {
			commessa = createCommessaFatturabile(commessa, cliente);
			ordineCommessa.setId(new OrdineCommessaKey(commessa.getCodiceCommessa(), TSUtils.uuid(), cliente.getPartitaIva()));	
			
			ordineCommessa.setCommessaFatturabile(commessa);
			ordineCommessa.setCliente(cliente);
			ordineCommessaRepository.save(ordineCommessa);
			logger.info("Ordine commessa creato e salvato a database");
			return ordineCommessa;
		} catch (Exception ex) {
			throw new CommessaException("Ordine commessa non creata");
		}
	}
	
}