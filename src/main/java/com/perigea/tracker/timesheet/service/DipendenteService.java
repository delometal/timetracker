package com.perigea.tracker.timesheet.service;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.enums.StatoUtenteType;
import com.perigea.tracker.timesheet.exception.DipendenteException;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.repository.AnagraficaDipendenteRepository;
import com.perigea.tracker.timesheet.repository.ApplicationDao;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@Service
public class DipendenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private AnagraficaDipendenteRepository dipendenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private ApplicationDao applicationDao;

	/**
	 * Creazione anagrafica dipendente e utente
	 * @param dipendenteDto
	 * @param codiceResponsabile
	 * @return
	 */
	public AnagraficaDipendenteResponseDto createDipendente(AnagraficaDipendenteInputDto dipendenteDto, String codiceResponsabile) {
		try {
			Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(dipendenteDto.getUtenteDto());
			AnagraficaDipendente dipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteDto);
			dipendente.setUtente(utente);
			dipendente.setCodicePersona(TSUtils.uuid());
			utente.setDipendente(dipendente);
			Utente responsabile = utenteRepository.findByCodicePersona(codiceResponsabile);
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
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Lettura dati di un dipendente
	 * @param dipendenteParam
	 * @return
	 */
	public AnagraficaDipendenteResponseDto readDipendente(String dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtente());
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento dati dipendente
	 * @param dipendenteParam
	 * @return
	 */
	public AnagraficaDipendenteResponseDto updateDipendente(AnagraficaDipendenteInputDto dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam.getUtenteDto().getCodicePersona());
			if (anagDipendente != null) {
				dipendenteRepository.save(anagDipendente);
			}
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtente());
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Cancellazione dipendente
	 * @param id
	 * @return
	 */
	@Transactional
	public AnagraficaDipendenteResponseDto deleteDipendente(String id) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(id);
			if (anagDipendente != null) {
				utenteRepository.delete(anagDipendente.getUtente());
				dipendenteRepository.delete(anagDipendente);
			}
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtente());
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
		}
	}

	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
	public UtenteViewDto updateUserStatus(String codicePersona, StatoUtenteType newStatus) {
		try {
			Integer edits = applicationDao.updateUserStatus(codicePersona, newStatus);
			if (edits != null && edits == 1) {
				return DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utenteRepository.findByCodicePersona(codicePersona));
			} else {
				throw new Exception(String.format("Si è verificato un errore durante l'aggiornamento per l'utente %s con il nuovo stato %s", codicePersona, newStatus.name()));
			}
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento ruoli utente
	 * @param ruoloList
	 * @param utente
	 * @return
	 */
	private UtenteViewDto editUserRoles(List<Ruolo> ruoloList, Utente utente) {
		try {
			ruoloList.forEach(r -> {
				if(!utente.getRuoli().contains(r)) {
					utente.addRuolo(r);
				}
			});
			utenteRepository.save(utente);
			return DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
	
	/**
	 * Aggiornamento ruoli utente
	 * @param ruoloList
	 * @param utente
	 * @return
	 */
	public UtenteViewDto editUserRolesDto(List<RuoloDto> ruoloList, String codicePersona) {
		try {
			Utente entity = utenteRepository.findByCodicePersona(codicePersona);
			List<Ruolo> ruoli = new ArrayList<>();
			if(entity != null && !ruoloList.isEmpty()) {
				ruoloList.forEach(r -> ruoli.add(DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(r)));
				return editUserRoles(ruoli, entity);
			}
			throw new EntityNotFoundException("Utente non trovato o ruoli non passati correttamente");
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}