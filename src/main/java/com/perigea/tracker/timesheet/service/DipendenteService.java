package com.perigea.tracker.timesheet.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.CentroDiCostoException;
import com.perigea.tracker.commons.exception.DipendenteException;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Personale;
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
import com.perigea.tracker.timesheet.search.Condition;
import com.perigea.tracker.timesheet.search.FilterFactory;
import com.perigea.tracker.timesheet.search.Operator;

@Service
@Transactional
public class DipendenteService extends UtenteService {
	
	@Autowired
	private FilterFactory<Dipendente> filter;

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
			economics.setCentroDiCosto(centroDiCostoRepository.findById(economics.getCodiceCentroDiCosto()).orElseThrow());
			dipendente.setEconomics(economics);
			economics.setCodicePersona(null);
			economics.setPersonale(dipendente);
		}
		dipendente.setDataCessazione(null);
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
			return dipendenteRepository.findByCodicePersona(codicePersona).orElseThrow();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new DipendenteException(ex.getMessage());
		}
	}
	
	/**
	 * ricerca di tutti gli utenti tramite il responsabile
	 * @param responsabile
	 * @return
	 */
	public List<Personale> readAllDipendentiByResponsabile(Personale responsabile) {
		try {
			return dipendenteRepository.findAllByResponsabile(responsabile);
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new DipendenteException(ex.getMessage());
		}
	}
	
	/**
	 * ricerca di tutti i dipendenti in base al RAL
	 * @param ral
	 * @param centroDiCosto
	 * @return
	 */
	public List<Dipendente> searchDipendentiByRal(Float ral,  String centroDiCosto) {
		try {
			return dipendenteRepository.findAll(dipendenteByRal(ral, centroDiCosto));
		} catch (Exception ex) {
			throw new CentroDiCostoException(ex.getMessage());
		}
	}
	
	/**
	 * Specification del RAL
	 * @param ral
	 * @param centroDiCosto
	 * @return
	 */
	private Specification<Dipendente> dipendenteByRal(final Float ral, final String centroDiCosto) {
		
		List<Condition> conditions = new ArrayList<>();
		conditions.add(Condition.builder().field("economics.ralAttuale").value(ral).valueType(Float.class).operator(Operator.gt).build());
		conditions.add(Condition.builder().field("economics.centroDiCosto.codiceCentroDiCosto").value(centroDiCosto).valueType(String.class).operator(Operator.eq).build());

		return filter.buildSpecification(conditions, false);
	}
	
	
	/**
	 * cessazione attività dipendente
	 * @param dipendente
	 * @param dataCessazione
	 * @return
	 */
	public Dipendente cessazioneDipendente(Dipendente dipendente, LocalDate dataCessazione) {
		dipendente.setDataCessazione(dataCessazione);
		updateUtenteStatus(dipendente.getCodicePersona(), StatoUtenteType.C);
		createStorico(dipendente.getEconomics());
		dipendenteRepository.save(dipendente);
		return dipendente;
	}
	
	/**
	 * metodo di creazione dello storico di un dipendente
	 * @param newDatiEconomici
	 */
	public void createStorico(DatiEconomiciDipendente newDatiEconomici) {
		String codicePersona = newDatiEconomici.getCodicePersona();
		Dipendente personale = newDatiEconomici.getPersonale();
		
		DatiEconomiciDipendente oldDatiEconomici = dipendenteRepository.findById(codicePersona).orElseThrow().getEconomics();
		
		// Storico LivelloContrattuale
		if (! oldDatiEconomici.getLivelloAttuale().equals(newDatiEconomici.getLivelloAttuale())) {
			StoricoLivelloContrattualeKey k = new StoricoLivelloContrattualeKey(codicePersona, oldDatiEconomici.getDecorrenzaLivello(), LocalDate.now());
			StoricoLivelloContrattuale st = new StoricoLivelloContrattuale(k, oldDatiEconomici.getLivelloAttuale(), personale);
			storico.createStoricoLivelloContrattuale(st);
		}
		
		// Storico TipoContratto
		if (! oldDatiEconomici.getTipoContrattoAttuale().equals(newDatiEconomici.getTipoContrattoAttuale())) {
			StoricoContrattoTypeKey k = new StoricoContrattoTypeKey(codicePersona, newDatiEconomici.getDecorrenzaTipoContratto(), LocalDate.now());
			StoricoContrattoType st = new StoricoContrattoType(k, oldDatiEconomici.getTipoContrattoAttuale(), personale);
			storico.createStoricoContrattoType(st);
		}
		
		// Storico Ral
		if (! oldDatiEconomici.getRalAttuale().equals(newDatiEconomici.getRalAttuale())) {
			StoricoRalKey k = new StoricoRalKey(codicePersona, oldDatiEconomici.getDecorrenzaRalAttuale(), LocalDate.now());
			StoricoRal st = new StoricoRal(k, BigDecimal.valueOf(oldDatiEconomici.getRalAttuale()), personale);
			storico.createStoricoRal(st);
		}
		
		// Storico premio
		if (! oldDatiEconomici.getDataUltimoPremio().equals(newDatiEconomici.getDataUltimoPremio())) {
			StoricoPremioKey k = new StoricoPremioKey(codicePersona, oldDatiEconomici.getDataUltimoPremio());
			StoricoPremio st = new StoricoPremio(k, BigDecimal.valueOf(oldDatiEconomici.getUltimoPremio()), personale);
			storico.createStoricoPremio(st);
		}
		
		// Storico rimborsiKm
		if ((! oldDatiEconomici.getModelloAuto().equals(newDatiEconomici.getModelloAuto())) || (! oldDatiEconomici.getRimborsoPerKm().equals(newDatiEconomici.getRimborsoPerKm()))) {
			StoricoRimborsiKmKey k = new StoricoRimborsiKmKey(codicePersona, oldDatiEconomici.getDecorrenzaRimborsiKm(), LocalDate.now());
			StoricoRimborsiKm st = new StoricoRimborsiKm(k, codicePersona, BigDecimal.valueOf(oldDatiEconomici.getRimborsoPerKm()), personale);
			storico.createStoricoRimborsiKm(st);
		}
		
		// Storico KmRimborsabili
		if (! oldDatiEconomici.getKmPerGiorno().equals(newDatiEconomici.getKmPerGiorno())) {
			StoricoKmRimborsabiliPerGiornoKey k = new StoricoKmRimborsabiliPerGiornoKey(codicePersona, oldDatiEconomici.getDecorrenzaKmRimborsabili(), LocalDate.now());
			StoricoKmRimborsabiliPerGiorno st = new StoricoKmRimborsabiliPerGiorno(k, BigDecimal.valueOf(oldDatiEconomici.getKmPerGiorno()), personale);
			storico.createStoricoKmRimborsabiliPerGiorno(st);
		}
		
		// Storico assegnazioneCentroDiCosto
		if (! oldDatiEconomici.getCodiceCentroDiCosto().equals(newDatiEconomici.getCodiceCentroDiCosto())) {
			StoricoAssegnazioneCentroCostoKey k = new StoricoAssegnazioneCentroCostoKey(codicePersona, oldDatiEconomici.getDecorrenzaAssegnazioneCentroDiCosto(), LocalDate.now());
			StoricoAssegnazioneCentroCosto st = new StoricoAssegnazioneCentroCosto(k, oldDatiEconomici.getCodiceCentroDiCosto(), personale);
			storico.createStoricoAssegnazioneCentroCosto(st);
		}
		
		// Storico costo giornaliero
		if (! oldDatiEconomici.getCostoGiornaliero().equals(newDatiEconomici.getCostoGiornaliero())) {
			StoricoGiornalieroKey k = new StoricoGiornalieroKey(codicePersona, oldDatiEconomici.getDataDecorrenzaCosto(), LocalDate.now());
			StoricoCostoGiornaliero st = new StoricoCostoGiornaliero(k, BigDecimal.valueOf(oldDatiEconomici.getCostoGiornaliero()) , personale);
			storico.createStoricoCostoGiornaliero(st);
		}
	}
	
}