package com.perigea.tracker.timesheet.controller;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.enums.CrudType;
import com.perigea.tracker.timesheet.enums.StatoUtenteType;
import com.perigea.tracker.timesheet.events.UserCrudEvent;
import com.perigea.tracker.timesheet.service.DipendenteService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;


@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

	@Autowired
	protected Logger logger;
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;

	@PostMapping(value = "/create-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> createDipendente(@RequestBody AnagraficaDipendenteInputDto anagraficaDipendenteDto) {
		Utente responsabile = dipendenteService.readUtenteDipendente(anagraficaDipendenteDto.getUtente().getCodiceResponsabile());
		Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(anagraficaDipendenteDto.getUtente());
		AnagraficaDipendente dipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(anagraficaDipendenteDto);
		utente.setResponsabile(responsabile);
		utente = dipendenteService.createUtenteDipendente(utente, dipendente);

		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(utente.getDipendente());
		anagraficaResponseDto.setUtenteDto(utenteResponseDto);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(anagraficaResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> readDipendente(@RequestParam String codicePersona) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(utente.getDipendente());
		anagraficaResponseDto.setUtenteDto(utenteResponseDto);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(anagraficaResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> deleteDipendente(@RequestParam String codicePersona) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		AnagraficaDipendente dipendente = utente.getDipendente();
		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(dipendente);
		anagraficaResponseDto.setUtenteDto(utenteResponseDto);

		dipendenteService.deleteUtenteDipendente(codicePersona);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(anagraficaResponseDto)
				.build();
		publishEvent(new UserCrudEvent(this, anagraficaResponseDto, CrudType.DELETE));
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> updateUser(@RequestBody AnagraficaDipendenteInputDto anagraficaDipendenteDto) {
		Utente responsabile = dipendenteService.readUtenteDipendente(anagraficaDipendenteDto.getUtente().getCodiceResponsabile());
		Utente utente = dipendenteService.readUtenteDipendente(anagraficaDipendenteDto.getUtente().getCodicePersona());
		AnagraficaDipendente dipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(anagraficaDipendenteDto);
		dipendente.setCodicePersona(utente.getCodicePersona());
		utente.setResponsabile(responsabile);
		utente.setDipendente(dipendente);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(utente.getDipendente());
		anagraficaResponseDto.setUtenteDto(utenteResponseDto);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(anagraficaResponseDto)
				.build();
		publishEvent(new UserCrudEvent(this, anagraficaResponseDto, CrudType.UPDATE));
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-utente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> updateUser(@RequestBody UtenteViewDto utenteViewDto) {
		Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(utenteViewDto);
		Utente responsabile = dipendenteService.readUtenteDipendente(utenteViewDto.getCodiceResponsabile());
		
		AnagraficaDipendente dipendente = dipendenteService.readAnagraficaDipendente(utenteViewDto.getCodicePersona());
		utente.setResponsabile(responsabile);
		utente.setDipendente(dipendente);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(utente.getDipendente());
		anagraficaResponseDto.setUtenteDto(utenteResponseDto);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(anagraficaResponseDto)
				.build();
		publishEvent(new UserCrudEvent(this, anagraficaResponseDto, CrudType.UPDATE));
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-user-status/{codicePersona}/{status}")
	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> editStatusUser(@PathVariable("codicePersona") String codicePersona, @PathParam("status") StatoUtenteType status) {
		Utente utente = dipendenteService.updateUtenteStatus(codicePersona, status);
		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(utente.getDipendente());
		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
				.dataRichiesta(new Date())
				.risultato(utenteResponseDto)
				.build();
		if(status == StatoUtenteType.CESSATO) {
			publishEvent(new UserCrudEvent(this, anagraficaResponseDto, CrudType.UPDATE));
		}
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-user-roles")
	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> editRoleUser(@RequestParam String codicePersona, @RequestBody List<RuoloDto> ruoliDto) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		List<Ruolo> ruoli = DtoEntityMapper.INSTANCE.fromDtoToEntity(ruoliDto);
		utente.setRuoli(ruoli);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
				.dataRichiesta(new Date())
				.risultato(utenteResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	/**
	 * Publish an event
	 * @param event
	 */
	private void publishEvent(ApplicationEvent event) {
		logger.info("Sending event to GruppoService {}", event);
		applicationEventPublisher.publishEvent(event);
	}

}