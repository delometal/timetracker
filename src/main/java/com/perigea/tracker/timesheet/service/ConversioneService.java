package com.perigea.tracker.timesheet.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciConsulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Utente;

@Transactional
@Service
public class ConversioneService {
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private ConsulenteService consulenteService;

	public Consulente conversioneDipendenteConsulente(Dipendente dipendente, Utente utente, Consulente consulente,
			DatiEconomiciConsulente economics, LocalDate dataCessazione) {
			utente = consulenteService.createUtenteConsulente(utente, consulente, economics);
			dipendenteService.cessazioneDipendente(dipendente, dataCessazione);
			return consulenteService.readAnagraficaConsulente(utente.getCodicePersona());
	}
	
	public Dipendente conversioneConsulenteToDipendente(Consulente consulente,Utente utente, Dipendente dipendente,
			DatiEconomiciDipendente economics, LocalDate dataCessazione) {
			utente = dipendenteService.createUtenteDipendente(utente, dipendente, economics);
			consulenteService.cessazioneConsulente(consulente, dataCessazione);
			return dipendenteService.readAnagraficaDipendente(utente.getCodicePersona());
	}
	
	public Consulente readAnagraficaConsulente(String codiceConsulente) {
		return consulenteService.readAnagraficaConsulente(codiceConsulente);
	}
	
	public Dipendente readAnagraficaDipendente(String codiceDipendente) {
		return dipendenteService.readAnagraficaDipendente(codiceDipendente);
	}
		
}
