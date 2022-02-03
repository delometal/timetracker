package com.perigea.tracker.timesheet.controller;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.perigea.tracker.commons.dto.DatiEconomiciDipendenteDto;
import com.perigea.tracker.commons.dto.DipendenteDto;
import com.perigea.tracker.commons.dto.GenericWrapperResponse;
import com.perigea.tracker.commons.dto.RuoloDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.DipendenteService;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

	@Autowired
	protected Logger logger;
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
//	@Autowired
//    private ApplicationEventPublisher applicationEventPublisher;

	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<DipendenteDto>> createDipendente(@RequestBody DipendenteDto dipendenteDto) {
		Utente responsabile = null;
		try {
			responsabile = dipendenteService.readUtenteDipendente(dipendenteDto.getUtente().getCodiceResponsabile());
		} catch(EntityNotFoundException e) {
			responsabile = null;
		}
		Utente utente = dtoEntityMapper.dtoToEntity(dipendenteDto.getUtente());
		Dipendente dipendente = dtoEntityMapper.dtoToEntity(dipendenteDto);
		utente.setResponsabile(responsabile);
		utente = dipendenteService.createUtenteDipendente(utente, dipendente);

		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente)utente.getAnagrafica();
		dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteDto);
		GenericWrapperResponse<DipendenteDto> genericResponse = GenericWrapperResponse
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.risultato(dipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<DipendenteDto>> readDipendente(@RequestParam String codicePersona) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente)utente.getAnagrafica();
		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteDto);
		GenericWrapperResponse<DipendenteDto> genericResponse = GenericWrapperResponse
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.risultato(dipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<DipendenteDto>> deleteDipendente(@RequestParam String codicePersona) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		Dipendente dipendente = (Dipendente) utente.getAnagrafica();
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(dipendente);
		dipendenteDto.setUtente(utenteDto);

		dipendenteService.deleteUtenteDipendente(codicePersona);
		GenericWrapperResponse<DipendenteDto> genericResponse = GenericWrapperResponse
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.risultato(dipendenteDto)
				.build();
//		publishEvent(new UserCrudEvent(this, dipendenteDto, CrudType.DELETE));
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<DipendenteDto>> updateUser(@RequestBody DipendenteDto dipendenteDto) {
		Utente responsabile = dipendenteService.readUtenteDipendente(dipendenteDto.getUtente().getCodiceResponsabile());
		Utente utente = dipendenteService.readUtenteDipendente(dipendenteDto.getUtente().getCodicePersona());
		Dipendente dipendente = dtoEntityMapper.dtoToEntity(dipendenteDto);
		dipendente.setCodicePersona(utente.getCodicePersona());
		utente.setResponsabile(responsabile);
		utente.setAnagrafica(dipendente);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente)utente.getAnagrafica();

		DipendenteDto anagraficaResponseDto = dtoEntityMapper.entityToDto(anagrafica);
		anagraficaResponseDto.setUtente(utenteResponseDto);
		GenericWrapperResponse<DipendenteDto> genericResponse = GenericWrapperResponse
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.risultato(anagraficaResponseDto)
				.build();
//		publishEvent(new UserCrudEvent(this, anagraficaResponseDto, CrudType.UPDATE));
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-user")
	public ResponseEntity<GenericWrapperResponse<DipendenteDto>> updateUser(@RequestBody UtenteDto UtenteDto) {
		Utente utente = dtoEntityMapper.dtoToEntity(UtenteDto);
		Utente responsabile = dipendenteService.readUtenteDipendente(UtenteDto.getCodiceResponsabile());
		
		Dipendente dipendente = dipendenteService.readAnagraficaDipendente(UtenteDto.getCodicePersona());
		utente.setResponsabile(responsabile);
		utente.setAnagrafica(dipendente);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente)utente.getAnagrafica();

		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteResponseDto);
		GenericWrapperResponse<DipendenteDto> genericResponse = GenericWrapperResponse
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.risultato(dipendenteDto)
				.build();
//		publishEvent(new UserCrudEvent(this, dipendenteDto, CrudType.UPDATE));
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-status/{codicePersona}/{status}")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> editStatusUser(@PathVariable("codicePersona") String codicePersona, @PathParam("status") StatoUtenteType status) {
		Utente utente = dipendenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse.<UtenteDto>builder()
				.timestamp(Utils.now())
				.risultato(utenteResponseDto)
				.build();
//		Dipendente anagrafica = (Dipendente)utente.getAnagrafica();
//		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
//		if(status == StatoUtenteType.C) {
//			publishEvent(new UserCrudEvent(this, dipendenteDto, CrudType.UPDATE));
//		}
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-roles")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> editRoleUser(@RequestParam String codicePersona, @RequestBody List<RuoloDto> ruoliDto) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		List<Ruolo> ruoli = dtoEntityMapper.dtoToEntityRuoloList(ruoliDto);
		utente.setRuoli(ruoli);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse.<UtenteDto>builder()
				.timestamp(Utils.now())
				.risultato(utenteResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-economics")
	public ResponseEntity<GenericWrapperResponse<DatiEconomiciDipendenteDto>> editDatiEconomiciDipendente(@RequestParam String codicePersona, @RequestBody DatiEconomiciDipendenteDto datiEconomiciDipendenteDto) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(datiEconomiciDipendenteDto);
		economics.setCodicePersona(codicePersona);
		
		Dipendente dipendente = (Dipendente) utente.getAnagrafica();
		dipendente.setEconomics(economics);
		economics.setDipendente(dipendente);
		utente.setAnagrafica(dipendente);
		
		utente = dipendenteService.updateUtenteDipendente(utente);
		datiEconomiciDipendenteDto = dtoEntityMapper.entityToDto(economics);

		GenericWrapperResponse<DatiEconomiciDipendenteDto> genericResponse = GenericWrapperResponse.<DatiEconomiciDipendenteDto>builder()
				.timestamp(Utils.now())
				.risultato(datiEconomiciDipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
//	/**
//	 * Publish an event
//	 * @param event
//	 */
//	private void publishEvent(ApplicationEvent event) {
//		logger.info("Sending event to GruppoService {}", event);
//		applicationEventPublisher.publishEvent(event);
//	}

}