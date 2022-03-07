package com.perigea.tracker.timesheet.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.exception.DipendenteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.StoricoAssegnazioneCentroCosto;
import com.perigea.tracker.timesheet.entity.StoricoContrattoType;
import com.perigea.tracker.timesheet.entity.StoricoCostoGiornaliero;
import com.perigea.tracker.timesheet.entity.StoricoKmRimborsabiliPerGiorno;
import com.perigea.tracker.timesheet.entity.StoricoLivelloContrattuale;
import com.perigea.tracker.timesheet.entity.StoricoPremio;
import com.perigea.tracker.timesheet.entity.StoricoRal;
import com.perigea.tracker.timesheet.entity.StoricoRimborsiKm;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.StoricoAssegnazioneCentroCostoKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoContrattoTypeKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoGiornalieroKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoKmRimborsabiliPerGiornoKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoLivelloContrattualeKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoPremioKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoRalKey;
import com.perigea.tracker.timesheet.entity.keys.StoricoRimborsiKmKey;
import com.perigea.tracker.timesheet.repository.CentroDiCostoRepository;
import com.perigea.tracker.timesheet.repository.DipendenteRepository;

@Service
@Transactional
public class DipendenteService extends UtenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	@Autowired
	private CentroDiCostoRepository centroDiCostoRepository;
	
	@Autowired
	private StoricoService storico;
	

	/**
	 * Creazione anagrafica dipendente e utente
	 * @param utente
	 * @param dipendente
	 * @param economics 
	 * @param codiceResponsabile
	 * @return
	 */
	public Utente createUtenteDipendente(Utente utente, Dipendente dipendente, DatiEconomiciDipendente economics) {
		if(economics != null) {
			economics.setCentroDiCosto(centroDiCostoRepository.findById(economics.getCodiceCentroDiCosto()).get());
			dipendente.setEconomics(economics);
			economics.setCodicePersona(null);
			economics.setPersonale(dipendente);
		}
		utente.setPersonale(dipendente);
		return super.createUtente(utente, dipendente);
	}
	
	/**
	 * Lettura dati di una AnagraficaDipendente
	 * @param dipendenteParam
	 * @return
	 */
	public Dipendente readAnagraficaDipendente(String codicePersona) {
		try {
			return dipendenteRepository.findByCodicePersona(codicePersona).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new DipendenteException(ex.getMessage());
		}
	}
	
	public void createStorico(DatiEconomiciDipendente newDatiEconomici) {
		String codicePersona = newDatiEconomici.getCodicePersona();
		Dipendente personale = newDatiEconomici.getPersonale();
		
		DatiEconomiciDipendente oldDatiEconomici = dipendenteRepository.findById(codicePersona).get().getEconomics();
		
		// Storico LivelloContrattuale
		if (oldDatiEconomici.getLivelloAttuale() != newDatiEconomici.getLivelloAttuale()) {
			StoricoLivelloContrattualeKey k = new StoricoLivelloContrattualeKey(codicePersona, oldDatiEconomici.getDecorrenzaLivello(), LocalDate.now());
			StoricoLivelloContrattuale st = new StoricoLivelloContrattuale(k, oldDatiEconomici.getLivelloAttuale(), personale);
			storico.createStoricoLivelloContrattuale(st);
		}
		
		// Storico TipoContratto
		if (oldDatiEconomici.getTipoContrattoAttuale() != newDatiEconomici.getTipoContrattoAttuale()) {
			StoricoContrattoTypeKey k = new StoricoContrattoTypeKey(codicePersona, newDatiEconomici.getDecorrenzaTipoContratto(), LocalDate.now());
			StoricoContrattoType st = new StoricoContrattoType(k, oldDatiEconomici.getTipoContrattoAttuale(), personale);
			storico.createStoricoContrattoType(st);
		}
		
		// Storico Ral
		if (oldDatiEconomici.getRalAttuale() != newDatiEconomici.getRalAttuale()) {
			StoricoRalKey k = new StoricoRalKey(codicePersona, oldDatiEconomici.getDecorrenzaRalAttuale(), LocalDate.now());
			StoricoRal st = new StoricoRal(k, new BigDecimal(oldDatiEconomici.getRalAttuale()), personale);
			storico.createStoricoRal(st);
		}
		
		// Storico premio
		if (oldDatiEconomici.getDataUltimoPremio() != newDatiEconomici.getDataUltimoPremio()) {
			StoricoPremioKey k = new StoricoPremioKey(codicePersona, oldDatiEconomici.getDataUltimoPremio());
			StoricoPremio st = new StoricoPremio(k, new BigDecimal(oldDatiEconomici.getUltimoPremio()), personale);
			storico.createStoricoPremio(st);
		}
		
		// Storico rimborsiKm
		if (oldDatiEconomici.getModelloAuto() != newDatiEconomici.getModelloAuto() || oldDatiEconomici.getRimborsoPerKm() != newDatiEconomici.getRimborsoPerKm()) {
			StoricoRimborsiKmKey k = new StoricoRimborsiKmKey(codicePersona, oldDatiEconomici.getDecorrenzaRimborsiKm(), LocalDate.now());
			StoricoRimborsiKm st = new StoricoRimborsiKm(k, codicePersona, new BigDecimal(oldDatiEconomici.getRimborsoPerKm()), personale);
			storico.createStoricoRimborsiKm(st);
		}
		
		// Storico KmRimborsabili
		if (oldDatiEconomici.getKmPerGiorno() != newDatiEconomici.getKmPerGiorno()) {
			StoricoKmRimborsabiliPerGiornoKey k = new StoricoKmRimborsabiliPerGiornoKey(codicePersona, oldDatiEconomici.getDecorrenzaKmRimborsabili(), LocalDate.now());
			StoricoKmRimborsabiliPerGiorno st = new StoricoKmRimborsabiliPerGiorno(k, new BigDecimal(oldDatiEconomici.getKmPerGiorno()), personale);
			storico.createStoricoKmRimborsabiliPerGiorno(st);
		}
		
		// Storico assegnazioneCentroDiCosto
		if (oldDatiEconomici.getCodiceCentroDiCosto() != newDatiEconomici.getCodiceCentroDiCosto()) {
			StoricoAssegnazioneCentroCostoKey k = new StoricoAssegnazioneCentroCostoKey(codicePersona, oldDatiEconomici.getDecorrenzaAssegnazioneCentroDiCosto(), LocalDate.now());
			StoricoAssegnazioneCentroCosto st = new StoricoAssegnazioneCentroCosto(k, oldDatiEconomici.getCodiceCentroDiCosto(), personale);
			storico.createStoricoAssegnazioneCentroCosto(st);
		}
		
		// Storico costo giornaliero
		if (oldDatiEconomici.getCostoGiornaliero() != newDatiEconomici.getCostoGiornaliero()) {
			StoricoGiornalieroKey k = new StoricoGiornalieroKey(codicePersona, oldDatiEconomici.getDataDecorrenzaCosto(), LocalDate.now());
			StoricoCostoGiornaliero st = new StoricoCostoGiornaliero(k, new BigDecimal(oldDatiEconomici.getCostoGiornaliero()) , personale);
			storico.createStoricoCostoGiornaliero(st);
		}
	}
}