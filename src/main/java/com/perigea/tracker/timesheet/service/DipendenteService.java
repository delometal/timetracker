package com.perigea.tracker.timesheet.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteDto;
import com.perigea.tracker.timesheet.dto.UtenteDto;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.UtenteException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.AnagraficaDipendenteRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;


@Service
public class DipendenteService {

	@Autowired
	private Logger logger;
	
	@Autowired
	private AnagraficaDipendenteRepository dipendenteRepo;
	
	@Autowired 
	private UtenteRepository utenteRepo;


	public AnagraficaDipendenteDto createDipendente(AnagraficaDipendenteDto dipendenteParam, UtenteDto utenteDto, String codiceResponsabile){
		try {
			Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(utenteDto);

			AnagraficaDipendente dipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteParam);
			dipendente.setUtenteDipendente(utente);
			utente.setDipendente(dipendente);
			Utente responsabile = utenteRepo.findByCodicePersona(codiceResponsabile);
			utente.setResponsabile(responsabile);
			responsabile.addDipendente(utente);
			utenteRepo.save(utente);
			AnagraficaDipendenteDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendente(dipendente);
			return dto;
		}catch(Exception ex) {
			throw new UtenteException("Dipendente non creato");	
		}
	}

	//Metodo per leggere i dati di un determinato dipendente
	public AnagraficaDipendenteDto readDipendente(String dipendenteParam) {
		try {
			AnagraficaDipendente entity=dipendenteRepo.findByCodicePersona(dipendenteParam);
			AnagraficaDipendenteDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendente(entity);
			return dto;
		}catch(Exception ex) {
			throw new EntityNotFoundException("Dipendente non trovato");	
		}
	}

	//Metodo per aggiornare i dati di un dipendente
	public AnagraficaDipendenteDto updateDipendente(AnagraficaDipendenteDto dipendenteParam) {
		try {
			AnagraficaDipendente entity=dipendenteRepo.findByCodicePersona(dipendenteParam.getCodicePersona());
			if(entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteParam);
				dipendenteRepo.save(entity);
			}
			AnagraficaDipendenteDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendente(entity);
			return dto;
		}catch(Exception ex) {
			throw new EntityNotFoundException("Dipendente non trovato");	
		}
	}

	//Metodo per eliminare un utente da database
	public void deleteDipendente(String id) {
		try {
			List<AnagraficaDipendente> entity= dipendenteRepo.findAll();
			for(AnagraficaDipendente u: entity) {
				if(u.getCodicePersona().equalsIgnoreCase(id)) {
					dipendenteRepo.delete(u);
				} 
			}
		}catch(Exception ex) {
			throw new EntityNotFoundException("Dipendente non trovato");	
		}
	}

}