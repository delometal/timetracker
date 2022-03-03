package com.perigea.tracker.timesheet.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.exception.ConsulenteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciConsulente;
import com.perigea.tracker.timesheet.entity.StoricoAssegnazioneCentroCosto;
import com.perigea.tracker.timesheet.entity.StoricoIngaggio;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.StoricoAssegnazioneCentroCostoKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoIngaggioKey;
import com.perigea.tracker.timesheet.repository.ConsulenteRepository;

@Service
public class ConsulenteService extends UtenteService {

	@Autowired
	private ConsulenteRepository consulenteRepository;
	
	@Autowired
	private StoricoService storico;

	/**
	 * Creazione anagrafica consulente e utente
	 * @param utente
	 * @param consulente
	 * @param codiceResponsabile
	 * @return
	 */
	public Utente createUtenteConsulente(Utente utente, Consulente consulente, DatiEconomiciConsulente economics) {
		consulente.setEconomics(economics);
		if(economics != null) {
			economics.setCodicePersona(null);
			economics.setPersonale(consulente);
		}
		utente.setPersonale(consulente);
		return super.createUtente(utente, consulente);		
	}
	
	/**
	 * Lettura dati di una AnagraficaConsulente
	 * @param consulenteParam
	 * @return
	 */

	public Consulente readAnagraficaConsulente(String codicePersona) {
		try {
			return consulenteRepository.findByCodicePersona(codicePersona).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new ConsulenteException(ex.getMessage());
		}
	}

	public void UpdateStorico(DatiEconomiciConsulente newDatiEconomici) {
		String codicePersona = newDatiEconomici.getCodicePersona();
		Consulente personale = newDatiEconomici.getPersonale();
		
		DatiEconomiciConsulente oldDatiEconomici = consulenteRepository.findById(codicePersona).get().getEconomics();
		
		// Storico CentroDiCosto 
		if (oldDatiEconomici.getDecorrenzaAssegnazioneCentroDiCosto() != newDatiEconomici.getDecorrenzaAssegnazioneCentroDiCosto()) {
			StoricoAssegnazioneCentroCostoKey k = new StoricoAssegnazioneCentroCostoKey(codicePersona, oldDatiEconomici.getDecorrenzaAssegnazioneCentroDiCosto(), LocalDate.now());
			StoricoAssegnazioneCentroCosto st = new StoricoAssegnazioneCentroCosto(k, oldDatiEconomici.getCodiceCentroDiCosto(), personale);
			storico.createStoricoAssegnazioneCentroCosto(st);
		}
		
		// Storico Ingaggio
		if (oldDatiEconomici.getCostoGiornaliero() != newDatiEconomici.getCostoGiornaliero()) {
			StoricoIngaggioKey k = new StoricoIngaggioKey(codicePersona, oldDatiEconomici.getDataDecorrenzaCosto(), LocalDate.now());
			StoricoIngaggio st = new StoricoIngaggio(k, new BigDecimal(oldDatiEconomici.getCostoGiornaliero()), personale);
			storico.createStoricoIngaggio(st);
		}
	}

}