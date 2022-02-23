package com.perigea.tracker.timesheet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.exception.CentroDiCostoException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.CentroDiCosto;
import com.perigea.tracker.timesheet.repository.CentroDiCostoRepository;
import com.perigea.tracker.timesheet.search.Condition;
import com.perigea.tracker.timesheet.search.FilterFactory;
import com.perigea.tracker.timesheet.search.Operator;


@Service
public class CentroDiCostoService {
	
	@Autowired
	private Logger logger;

	@Autowired
	private CentroDiCostoRepository centroDiCostoRepository;
	
	@Autowired
	private FilterFactory<CentroDiCosto> filter;

	/**
	 * creazione centro di costo
	 * @param centroDiCosto
	 * @return
	 */
	public CentroDiCosto createCentroDiCosto(CentroDiCosto centroDiCosto) {
		try {
			if(Utils.isEmpty(centroDiCosto.getCodiceCentroDiCosto())) {
				centroDiCosto.setCodiceCentroDiCosto(Utils.uuid());
			}
			return centroDiCostoRepository.save(centroDiCosto);
		} catch (Exception ex) {
			throw new CentroDiCostoException("Centro di costo non inserito");
		}
	}

	/**
	 * lettura dati di un centro di costo
	 * @param id
	 * @return
	 */
	public CentroDiCosto readCentroDiCosto(String id) {
		try {
			return centroDiCostoRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CentroDiCostoException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento di un centro di costo
	 * @param centroDiCosto
	 * @return
	 */
	public CentroDiCosto updateCentroDiCosto(CentroDiCosto centroDiCosto) {
		try {
			return centroDiCostoRepository.save(centroDiCosto);
		} catch (Exception ex) {
			throw new CentroDiCostoException("Centro di costo non trovato");
		}
	}

	/**
	 * Cancellazione di un centro di costo
	 * @param id
	 * @return
	 */
	public void deleteCentroDiCosto(String id) {
		try {
			centroDiCostoRepository.deleteById(id);
			logger.info(String.format("Centro di Costo con id %s cancellato", id));
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CentroDiCostoException(ex.getMessage());
		}
	}
	

	/**
	 * ricerca centro di costo per descrizione
	 * @param id
	 * @return
	 */
	public List<CentroDiCosto> searchCentroDiCosto(String descrizione) {
		try {
			return centroDiCostoRepository.findAll(centroDiCostoByIdOrDescriptionSearch(descrizione));
		} catch (Exception ex) {
			throw new CentroDiCostoException(ex.getMessage());
		}
	}
	
	private Specification<CentroDiCosto> centroDiCostoByIdOrDescriptionSearch(final String searchKey) {
		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("codiceCentroDiCosto").value(searchKey).valueType(String.class).operator(Operator.like).build());
		conditions.add(Condition.builder().field("descrizione").value(searchKey).valueType(String.class).operator(Operator.like).build());
		return filter.buildSpecification(conditions, true);
	}

}
