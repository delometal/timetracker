package com.perigea.tracker.timesheet.service;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.UtenteException;
import com.perigea.tracker.timesheet.repository.AnagraficaDipendenteRepository;
import com.perigea.tracker.timesheet.repository.RuoliRepository;
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
	private RuoliRepository ruoliRepository;

	// metodo per creare un dipendente
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
			throw new UtenteException(ex.getMessage());
		}
	}

	// Metodo per leggere i dati di un determinato dipendente
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

	// Metodo per aggiornare i dati di un dipendente
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
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per eliminare un dipendente da database
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
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
	public UtenteViewDto editStatusUser(UtentePostDto utenteDto) {
		try {
			Utente entity = utenteRepository.findByCodicePersona(utenteDto.getCodicePersona());
			if (entity != null) {
				entity.setStatoUtenteType(utenteDto.getStatoUtente());
				entity.setLastUpdateUser("");
				utenteRepository.save(entity);
			}
//			Utente responsabile = entity.getResponsabile();
//			UtenteViewDto respDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
			UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(entity);
//			dto.setResponsabileDto(respDto);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	private UtenteViewDto editUserRoles(List<Ruolo> ruoloList, Utente utente) {
		try {
			ruoloList.forEach(r ->{
				if(utente.getRuoli().contains(r)) {
					utente.removeRuolo(r);
				} else {
					utente.addRuolo(r);
				}
			});
			utenteRepository.save(utente);
			UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
	
	public UtenteViewDto editUserRolesDto(List<RuoloDto> ruoloList, String codicePersona) {
		try {
			Utente entity= utenteRepository.findByCodicePersona(codicePersona);
			List<Ruolo> list= new ArrayList<>();
			if(entity != null && !ruoloList.isEmpty()) {
				ruoloList.forEach( r-> {
					list.add(DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(r));
				});
				return editUserRoles(list,entity);
			}
			throw new EntityNotFoundException("Utente non trovato o ruoli non passati correttamente");
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}