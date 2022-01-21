package com.perigea.tracker.timesheet.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.NotaSpeseInputDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.enums.CostoNotaSpeseType;
import com.perigea.tracker.timesheet.exception.NotaSpeseException;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.NotaSpeseRepository;
import com.perigea.tracker.timesheet.repository.TimesheetDataRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@Service
public class NotaSpeseService {

	@Autowired
	private NotaSpeseRepository notaSpeseRepository;
	
	@Autowired
	private CommessaRepository commessaReposirory;
	
	@Autowired
	private UtenteRepository utenteReposirory;

	@Autowired
	private TimesheetDataRepository timesheetEntryRepository;

	@Autowired
	private Logger logger;

	public NotaSpese createNotaSpese(NotaSpeseInputDto notaSpeseDto) {
		try {
			NotaSpese notaSpese = DtoEntityMapper.INSTANCE.fromDtoToEntityNotaSpese(notaSpeseDto);
			NotaSpeseKey id = new NotaSpeseKey(notaSpeseDto.getAnno(), notaSpeseDto.getMese(), notaSpeseDto.getGiorno(),
					notaSpeseDto.getCodicePersona(), notaSpeseDto.getCodiceCommessa(),
					notaSpeseDto.getCostoNotaSpese());
			notaSpese.setId(id);
			TimesheetEntryKey tsKey = new TimesheetEntryKey(notaSpeseDto.getAnno(), notaSpeseDto.getMese(), notaSpeseDto.getGiorno(), notaSpeseDto.getCodicePersona(), notaSpeseDto.getCodiceCommessa());
			TimesheetEntry timesheetEntry = timesheetEntryRepository.findById(tsKey);
			Commessa commessa = commessaReposirory.findByCodiceCommessa(notaSpeseDto.getCodiceCommessa());
			Utente utente = utenteReposirory.findByCodicePersona(notaSpeseDto.getCodicePersona());
			if(timesheetEntry != null) {
				notaSpese.setTimesheetEntry(timesheetEntry);
			}
			notaSpese.setCommessa(commessa);
			notaSpese.setUtente(utente);
			notaSpeseRepository.save(notaSpese);
			return notaSpese;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non creata");
		}
	}

	public List<NotaSpese> readNotaSpese(String codicePersona) {
		try {
			List<NotaSpese> allNotaSpeseList = notaSpeseRepository.findAll();
			List<NotaSpese> notaSpeseList = new ArrayList<>();
			for (NotaSpese entity : allNotaSpeseList) {
				if (entity.getUtente().getCodicePersona().equalsIgnoreCase(codicePersona)) {
					notaSpeseList.add(entity);
				}
			}
			return notaSpeseList;
		} catch (Exception ex) {
			throw new NotaSpeseException("Note Spese non trovate");
		}
	}

	public NotaSpese updateNotaSpese(NotaSpeseInputDto notaSpeseInputDto) {
		try {
			NotaSpeseKey id = new NotaSpeseKey(notaSpeseInputDto.getAnno(), notaSpeseInputDto.getMese(), notaSpeseInputDto.getGiorno(),
					notaSpeseInputDto.getCodicePersona(), notaSpeseInputDto.getCodiceCommessa(),
					notaSpeseInputDto.getCostoNotaSpese());
			NotaSpese notaSpesa = notaSpeseRepository.findById(id);
			notaSpesa = DtoEntityMapper.INSTANCE.fromDtoToEntityNotaSpeseInput(notaSpeseInputDto);
			notaSpesa.setId(id);
				notaSpeseRepository.save(notaSpesa);
			return notaSpesa;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}

	public NotaSpese deleteNotaSpese(String codicePersona, String codiceCommessa, String tipoCostoNotaSpese) {
		try {
			List<NotaSpese> notaSpeseList = notaSpeseRepository.findAll();
			CostoNotaSpeseType costo = CostoNotaSpeseType.valueOf(tipoCostoNotaSpese);
			for (NotaSpese entity : notaSpeseList) {
				if (entity.getUtente().getCodicePersona().equalsIgnoreCase(codicePersona)
						&& entity.getCommessa().getCodiceCommessa().equalsIgnoreCase(codiceCommessa)
						&& entity.getId().getCostoNotaSpese().equals(costo)) {
					notaSpeseRepository.delete(entity);
					return entity;
				}
			}
			return null;
		} catch (Exception ex) {
			throw new NotaSpeseException("NotaSpese non trovata");
		}
	}
}
