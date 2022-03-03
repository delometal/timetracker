package com.perigea.tracker.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.StoricoAssegnazioneCentroCosto;
import com.perigea.tracker.timesheet.entity.StoricoContrattoType;
import com.perigea.tracker.timesheet.entity.StoricoCostoGiornaliero;
import com.perigea.tracker.timesheet.entity.StoricoIngaggio;
import com.perigea.tracker.timesheet.entity.StoricoKmRimborsabiliPerGiorno;
import com.perigea.tracker.timesheet.entity.StoricoLivelloContrattuale;
import com.perigea.tracker.timesheet.entity.StoricoPremio;
import com.perigea.tracker.timesheet.entity.StoricoRal;
import com.perigea.tracker.timesheet.entity.StoricoRimborsiKm;
import com.perigea.tracker.timesheet.repository.StoricoAssegnazioneCentroCostoRepository;
import com.perigea.tracker.timesheet.repository.StoricoContrattoTypeRepository;
import com.perigea.tracker.timesheet.repository.StoricoCostoGiornalieroRepository;
import com.perigea.tracker.timesheet.repository.StoricoIngaggioRepository;
import com.perigea.tracker.timesheet.repository.StoricoKmRimborsabiliPerGiornoRepository;
import com.perigea.tracker.timesheet.repository.StoricoLivelloCotrattualeRepository;
import com.perigea.tracker.timesheet.repository.StoricoPremioRepository;
import com.perigea.tracker.timesheet.repository.StoricoRalRepository;
import com.perigea.tracker.timesheet.repository.StoricoRimborsiKmRepository;

import lombok.Getter;

@Service
@Getter
public class StoricoService {

	@Autowired
	private StoricoRimborsiKmRepository storicoKmRepository;
	@Autowired
	private StoricoRalRepository storicoRalRepository;
	@Autowired
	private StoricoPremioRepository storicoPremioRepository;
	@Autowired
	private StoricoLivelloCotrattualeRepository storicoLivelloCotrattualeRepository;
	@Autowired
	private StoricoKmRimborsabiliPerGiornoRepository storicoKmRimborsabiliPerGiornoRepository;
	@Autowired
	private StoricoCostoGiornalieroRepository storicoCostoGiornalieroRepository;
	@Autowired
	private StoricoContrattoTypeRepository storicoContrattoTypeRepository;
	@Autowired
	private StoricoAssegnazioneCentroCostoRepository storicoAssegnazioneCentroCostoRepository;
	@Autowired
	private StoricoIngaggioRepository storicoIngaggioRepository;
	
	
	public void createStoricoRimborsiKm(StoricoRimborsiKm storico) {
		storicoKmRepository.save(storico);
	}
	
	public void createStoricoRal(StoricoRal storico) {
		storicoRalRepository.save(storico);
	}
	
	public void createStoricoPremio(StoricoPremio storico) {
		storicoPremioRepository.save(storico);
	}
	
	public void createStoricoLivelloContrattuale(StoricoLivelloContrattuale storico) {
		storicoLivelloCotrattualeRepository.save(storico);
	}
	
	public void createStoricoKmRimborsabiliPerGiorno(StoricoKmRimborsabiliPerGiorno storico) {
		storicoKmRimborsabiliPerGiornoRepository.save(storico);
	}
	
	public void createStoricoCostoGiornaliero(StoricoCostoGiornaliero storico) {
		storicoCostoGiornalieroRepository.save(storico);
	}
	
	public void createStoricoContrattoType(StoricoContrattoType storico) {
		storicoContrattoTypeRepository.save(storico);
	}
	
	public void createStoricoAssegnazioneCentroCosto(StoricoAssegnazioneCentroCosto storico) {
		storicoAssegnazioneCentroCostoRepository.save(storico);
	}
	
	public void createStoricoIngaggio(StoricoIngaggio storico) {
		storicoIngaggioRepository.save(storico);
	}
}
