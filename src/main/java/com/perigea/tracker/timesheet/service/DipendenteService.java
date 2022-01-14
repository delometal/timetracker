package com.perigea.tracker.timesheet.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
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
	private AnagraficaDipendenteRepository dipendenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	// metodo per creare un dipendente
	public AnagraficaDipendenteResponseDto createDipendente(AnagraficaDipendenteInputDto dipendenteDto) {
		try {
			Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(dipendenteDto.getUtenteDto());
			AnagraficaDipendente dipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteDto);
			dipendente.setUtenteDipendente(utente);
			utente.setDipendente(dipendente);
			Utente responsabile = utenteRepository.findByCodicePersona(dipendenteDto.getUtenteDto().getCodicePersona());
			utente.setResponsabile(responsabile);
			if(responsabile != null) {
				responsabile.addDipendente(utente);
			}
			utenteRepository.save(utente);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(dipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			logger.info("done");
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	// Metodo per leggere i dati di un determinato dipendente
	public AnagraficaDipendenteResponseDto readDipendente(String dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare i dati di un dipendente
	public AnagraficaDipendenteResponseDto updateDipendente(AnagraficaDipendenteInputDto dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam.getUtenteDto().getCodicePersona());
			if (anagDipendente != null) {
				anagDipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteParam);
				dipendenteRepository.save(anagDipendente);
			}
			
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per eliminare un dipendente da database
	public AnagraficaDipendenteResponseDto deleteDipendente(String id) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(id);
			if (anagDipendente != null) {
				dipendenteRepository.delete(anagDipendente);
			}
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}