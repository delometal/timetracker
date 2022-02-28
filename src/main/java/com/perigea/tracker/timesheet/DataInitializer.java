package com.perigea.tracker.timesheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.perigea.tracker.commons.enums.FestivitaType;
import com.perigea.tracker.commons.enums.RuoloType;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.RuoliRepository;

@Component
public class DataInitializer implements ApplicationRunner {

	@Autowired
	private RuoliRepository ruoliRepository;

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

//inizializzazione Ruoli
		if (ruoliRepository.findAll().isEmpty()) {
			for (RuoloType r : RuoloType.values()) {
				ruoliRepository.save(Ruolo.builder().id(r).descrizione(r.getDescrizione()).build());
			}
		}

//inizializzazione Festivita
		if (festivitaRepository.findAll().isEmpty()) {
			for (FestivitaType f : FestivitaType.values()) {
				festivitaRepository.save(Festivita.builder().data(f.getData()).nomeFestivo(f).build());
			}
		}

	}

}
