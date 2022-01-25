package com.perigea.tracker.timesheet.service;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
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
	 * @param utente
	 * @param dipendente
	 * @param codiceResponsabile
	 * @return
	 */
	public Utente createUtenteDipendente(Utente utente, AnagraficaDipendente dipendente, String codiceResponsabile) {
		try {
			dipendente.setUtente(utente);
			dipendente.setCodicePersona(TSUtils.uuid());
			utente.setDipendente(dipendente);
			Utente responsabile = utenteRepository.findByCodicePersona(codiceResponsabile);
			utente.setResponsabile(responsabile);
			if(responsabile != null) {
				responsabile.addDipendente(utente);
			}
			utenteRepository.save(utente);
			logger.info("done");
			return utente;
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Lettura dati di un dipendente
	 * @param dipendenteParam
	 * @return
	 */
	public Utente readDipendente(String codicePersona) {
		try {
			Utente utente = utenteRepository.findByCodicePersona(codicePersona);
//			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam);
//			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtente());
//			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
//			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
//			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			return utente;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	/**
	 * Aggiornamento dati dipendente
	 * @param dipendenteParam
	 * @return
	 */
	public Utente updateDipendente(AnagraficaDipendenteInputDto dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam.getUtenteDto().getCodicePersona());
			if (anagDipendente != null) {
				dipendenteRepository.save(anagDipendente);
			}
			return anagDipendente.getUtente();
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
		}
	}

	/**
	 * Cancellazione dipendente
	 * @param id
	 * @return
	 */
	public Utente deleteDipendente(String id) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(id);
			Utente utente = anagDipendente.getUtente();
			utenteRepository.delete(utente);
			return utente;
		} catch (Exception ex) {
			throw new DipendenteException(ex.getMessage());
		}
	}

	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
	public Utente updateUserStatus(String codicePersona, StatoUtenteType newStatus) {
		try {
			Integer edits = applicationDao.updateUserStatus(codicePersona, newStatus);
			if (edits != null && edits == 1) {
				return utenteRepository.findByCodicePersona(codicePersona);
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
	private Utente editUserRoles(List<Ruolo> ruoloList, Utente utente) {
		try {
			ruoloList.forEach(r -> {
				if(!utente.getRuoli().contains(r)) {
					utente.addRuolo(r);
				}
			});
			return utenteRepository.save(utente);
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
	public Utente editUserRolesDto(List<RuoloDto> ruoloList, String codicePersona) {
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