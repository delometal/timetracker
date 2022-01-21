package com.perigea.tracker.timesheet.service;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.DipendenteCommessaDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.exception.CommessaException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.DipendenteCommessaRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@Service
public class DipendenteCommessaService {

	@Autowired
	private Logger logger;

	@Autowired
	private CommessaRepository commessaRepository;

	@Autowired
	private DipendenteCommessaRepository dipendenteCommessaRepository;
	
	@Autowired
	private UtenteRepository utenteRepository;

	/**
	 * Creazione relazione dipendente commessa
	 * @param dipendenteCommessaDto
	 * @return
	 */
	public DipendenteCommessa createRelazioneDipendenteCommessa(DipendenteCommessaDto dipendenteCommessaDto) {
		try {
			DipendenteCommessa dipendenteCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityRelazioneDipendenteCommessa(dipendenteCommessaDto);
			Utente utente = utenteRepository.findByCodicePersona(dipendenteCommessaDto.getCodicePersona());
			Commessa commessa = commessaRepository.findByCodiceCommessa(dipendenteCommessaDto.getCodiceCommessa());
			dipendenteCommessa.setId(new DipendenteCommessaKey(utente, commessa));
			dipendenteCommessaRepository.save(dipendenteCommessa);
			logger.info("Relazione Dipendente-commessa creata");
			return dipendenteCommessa;
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}

	/**
	 * Lettura relazione dipendente commessa
	 * @param id
	 * @return
	 */
	public DipendenteCommessa readRelazioneDipendenteCommessa(DipendenteCommessaKey id) {
		try {
			return dipendenteCommessaRepository.findById(id).get();
		} catch (Exception ex) {
			if(ex instanceof NoSuchElementException) {
				throw new EntityNotFoundException(ex.getMessage());
			}
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * Update relazione dipendente commessa
	 * @param dipendenteCommessaDto
	 */
	public void updateRelazioneDipendenteCommessa(DipendenteCommessaDto dipendenteCommessaDto) {
		try {
			DipendenteCommessa dipendenteCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityRelazioneDipendenteCommessa(dipendenteCommessaDto);
			Utente utente = utenteRepository.findByCodicePersona(dipendenteCommessaDto.getCodicePersona());
			Commessa commessa = commessaRepository.findByCodiceCommessa(dipendenteCommessaDto.getCodiceCommessa());
			dipendenteCommessa.setId(new DipendenteCommessaKey(utente, commessa));
			dipendenteCommessaRepository.save(dipendenteCommessa);
			logger.info("Relazione Dipendente-commessa aggiornata");
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
	/**
	 * Delete relazione dipendente commessa
	 * @param dipendenteCommessaDto
	 */
	public void deleteRelazioneDipendenteCommessa(DipendenteCommessaDto dipendenteCommessaDto) {
		try {
			DipendenteCommessa dipendenteCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityRelazioneDipendenteCommessa(dipendenteCommessaDto);
			Utente utente = utenteRepository.findByCodicePersona(dipendenteCommessaDto.getCodicePersona());
			Commessa commessa = commessaRepository.findByCodiceCommessa(dipendenteCommessaDto.getCodiceCommessa());
			dipendenteCommessa.setId(new DipendenteCommessaKey(utente, commessa));
			dipendenteCommessaRepository.save(dipendenteCommessa);
			logger.info("Relazione Dipendente-commessa cancellata");
		} catch (Exception ex) {
			throw new CommessaException(ex.getMessage());
		}
	}
	
}