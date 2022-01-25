package com.perigea.tracker.timesheet.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.exception.NotaSpeseException;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.NotaSpeseRepository;
import com.perigea.tracker.timesheet.repository.TimesheetDataRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
public class NotaSpeseService {

	@Autowired
	private NotaSpeseRepository notaSpeseRepository;
	
	@Autowired
	private CommessaRepository commessaRepository;
	
	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private TimesheetDataRepository timesheetEntryRepository;

	@Autowired
	private Logger logger;

	public NotaSpese createNotaSpese(NotaSpese notaSpese) {
		try {
			NotaSpeseKey id = new NotaSpeseKey(notaSpese.getId().getAnno(), notaSpese.getId().getMese(), notaSpese.getId().getGiorno(),
					notaSpese.getId().getCodicePersona(), notaSpese.getId().getCodiceCommessa(),
					notaSpese.getId().getCostoNotaSpese());
			notaSpese.setId(id);
			TimesheetEntryKey tsKey = new TimesheetEntryKey(notaSpese.getId().getAnno(), notaSpese.getId().getMese(), notaSpese.getId().getGiorno(), notaSpese.getId().getCodicePersona(), notaSpese.getId().getCodiceCommessa());
			TimesheetEntry timesheetEntry = timesheetEntryRepository.findById(tsKey);
			Commessa commessa = commessaRepository.findByCodiceCommessa(notaSpese.getId().getCodiceCommessa());
			Utente utente = utenteRepository.findByCodicePersona(notaSpese.getId().getCodicePersona());
			if(timesheetEntry != null) {
				notaSpese.setTimesheetEntry(timesheetEntry);
			}
			notaSpese.setCommessa(commessa);
			notaSpese.setUtente(utente);
			notaSpeseRepository.save(notaSpese);
			logger.info("Nota spese salvata");
			return notaSpese;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non creata");
		}
	}
	

	public NotaSpese readNotaSpese(NotaSpeseKey key) {
		try {
			return notaSpeseRepository.findById(key).get();
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}

	public NotaSpese updateNotaSpese(NotaSpese notaSpese) {
		try {
			notaSpeseRepository.save(notaSpese);
			return notaSpese;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}

	public NotaSpese deleteNotaSpese(NotaSpeseKey key) {
		try {
			NotaSpese notaSpese = notaSpeseRepository.findById(key).get();
			notaSpeseRepository.deleteById(key);
			return notaSpese;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}

	public NotaSpese deleteNotaSpese(NotaSpese notaSpese) {
		try {
			notaSpeseRepository.delete(notaSpese);
			return notaSpese;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}
}
