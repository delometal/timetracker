package com.perigea.tracker.timesheet.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.enums.CommessaType;
import com.perigea.tracker.commons.exception.CentroDiCostoException;
import com.perigea.tracker.commons.exception.CommessaException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Cliente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaEstensione;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.keys.CommessaEstensioneKey;
import com.perigea.tracker.timesheet.entity.keys.OrdineCommessaKey;
import com.perigea.tracker.timesheet.repository.ClienteRepository;
import com.perigea.tracker.timesheet.repository.CommessaEstensioneRepository;
import com.perigea.tracker.timesheet.repository.CommessaFatturabileRepository;
import com.perigea.tracker.timesheet.repository.CommessaNonFatturabileRepository;
import com.perigea.tracker.timesheet.repository.OrdineCommessaRepository;
import com.perigea.tracker.timesheet.search.Condition;
import com.perigea.tracker.timesheet.search.FilterFactory;
import com.perigea.tracker.timesheet.search.Operator;

@Service
@Transactional
public class CommessaService {
	
	@Autowired
	private FilterFactory<CommessaFatturabile> filter;

	@Autowired
	private Logger logger;

	@Autowired
	private CommessaNonFatturabileRepository commessaNonFatturabileRepository;
	
	@Autowired
	private CommessaEstensioneRepository commessaEstensioneRepository;

	@Autowired
	private CommessaFatturabileRepository commessaFatturabileRepository;

	@Autowired
	private OrdineCommessaRepository ordineCommessaRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * @param commessaNonFatturabile metodo per creare o aggiornate una commessa non
	 *                               fatturabile
	 * @return
	 */
	public CommessaNonFatturabile saveCommessaNonFatturabile(CommessaNonFatturabile commessa) {
		try {
			commessa.setTipoCommessa(CommessaType.S);
			commessa.setCliente(clienteService.loadClientePerigea());
			commessa.setCodiceCommessa(Utils.uuid());
			return commessaNonFatturabileRepository.save(commessa);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per leggere i dati di una commessa non fatturabile
	 * 
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaNonFatturabile readCommessaNonFatturabile(String codiceCommessa) {
		try {
			return commessaNonFatturabileRepository.findByCodiceCommessa(codiceCommessa).orElseThrow();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per eliminare una commessa non fatturabile
	 * 
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
	 * 
	 * @param commessaFatturabileDtoWrapper
	 * @return
	 */
	public CommessaFatturabile createCommessaFatturabile(CommessaFatturabile commessa, Cliente cliente) {
		try {
			if (clienteRepository.findById(cliente.getCodiceAzienda()).isEmpty()) {
				cliente = clienteRepository.save(cliente);
			}
			commessa.setCliente(cliente);
			commessa.setCodiceCommessa(Utils.uuid());
			commessaFatturabileRepository.save(commessa);
			logger.info("CommessaFatturabile creata e salvata a database");
			return commessa;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per leggere i dati di una commessa fatturabile
	 * 
	 * @param codiceCommessa
	 * @return
	 */
	public CommessaFatturabile readCommessaFatturabile(final String codiceCommessa) {
		try {
			return commessaFatturabileRepository.findByCodiceCommessa(codiceCommessa).orElseThrow();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per aggiornare i dati di una commessa fatturabile
	 * 
	 * @param dtoParam
	 * @return
	 */
	public CommessaFatturabile updateCommessaFatturabile(CommessaFatturabile commessaAggiornata) {
		try {
			return commessaFatturabileRepository.save(commessaAggiornata);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per eliminare una commessa fatturabile
	 * 
	 * @param codiceCommessa
	 * @return
	 */
	public void deleteCommessaFatturabile(final String codiceCommessa) {
		try {
			CommessaFatturabile commessa = readCommessaFatturabile(codiceCommessa);
			ordineCommessaRepository.delete(commessa.getOrdineCommessa());
			commessaFatturabileRepository.deleteById(codiceCommessa);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	

	/**
	 *************************************** ESTENSIONE **************************************
	 */
	public CommessaEstensione createEstensioneCommessa(CommessaEstensione estensione) {
		try {
			CommessaEstensioneKey id = new CommessaEstensioneKey(estensione.getId().getCodiceCommessa(), 
					estensione.getId().getDataEstensione());
			estensione.setId(id);
			Commessa commessa = commessaFatturabileRepository.getById(estensione.getId().getCodiceCommessa());
			estensione.setCommessa(commessa);
			return commessaEstensioneRepository.save(estensione);
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * lettura di tutte le estensioni di una commessa
	 * @param codiceCommessa
	 * @return
	 */
	public List<CommessaEstensione> readAllCommessaEstensione(String codiceCommessa) {
		try {
			return commessaEstensioneRepository.findAllByCodiceCommessa(codiceCommessa);
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * lettura di un'estensione tramite composite key
	 * @param codiceCommessa
	 * @param dataEstensione
	 * @return
	 */
	public CommessaEstensione findById(String codiceCommessa, LocalDate dataEstensione) {
		try {
			CommessaEstensioneKey id = new CommessaEstensioneKey(codiceCommessa, dataEstensione);
			return commessaEstensioneRepository.findById(id).orElseThrow();
		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * metodo per creare un ordine commessa
	 * 
	 * @param commessaFatturabileWrapper
	 * @param ragioneSocialeCliente
	 * @return
	 */
	public OrdineCommessa createOrdineCommessa(OrdineCommessa ordineCommessa, CommessaFatturabile commessa,
			Cliente cliente) {
		try {
			commessa = createCommessaFatturabile(commessa, cliente);
			ordineCommessa.setId(
					new OrdineCommessaKey(commessa.getCodiceCommessa(), Utils.uuid(), cliente.getCodiceAzienda()));

			ordineCommessa.setCommessaFatturabile(commessa);
			ordineCommessa.setCliente(cliente);
			ordineCommessaRepository.save(ordineCommessa);
			logger.info("Ordine commessa creato e salvato a database");
			return ordineCommessa;
		} catch (Exception ex) {
			throw new CommessaException("Ordine commessa non creata");
		}
	}
	
	/**
	 * lettura commesse fatturabili tramite specification
	 * @param totaleFatturato
	 * @param importoOrdine
	 * @return
	 */
	public List<CommessaFatturabile> searchCommesseAfterThatImports(Double totaleFatturato, Double importoOrdine) {
		try {
			return commessaFatturabileRepository.findAll(commesseAfterThatImport(totaleFatturato, importoOrdine));
		} catch (Exception ex) {
			throw new CentroDiCostoException(ex.getMessage());
		}
	}
	
	/**
	 * specification commesse fatturabili
	 * @param totaleFatturato
	 * @param importoOrdine
	 * @return
	 */
	private Specification<CommessaFatturabile> commesseAfterThatImport(final Double totaleFatturato, final Double importoOrdine) {
		
		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("totaleFatturatoDaInizioAnno").value(totaleFatturato).valueType(Double.class).operator(Operator.gt).build());
		conditions.add(Condition.builder().field("ordineCommessa.importoOrdine").value(importoOrdine).valueType(Double.class).operator(Operator.gt).build());

		return filter.buildSpecification(conditions, false);
	}
	

}