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

import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.dto.ConsulenteDto;
import com.perigea.tracker.timesheet.dto.DatiEconomiciConsulenteDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.UtenteDto;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciConsulente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.service.ConsulenteService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/consulenti")
public class ConsulenteController {

	@Autowired
	protected Logger logger;
	
	@Autowired
	private ConsulenteService consulenteService;
	
//	@Autowired
//    private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> createConsulente(@RequestBody ConsulenteDto consulenteDto) {
		Utente responsabile = consulenteService.readUtenteConsulente(consulenteDto.getUtente().getCodiceResponsabile());
		Utente utente = dtoEntityMapper.dtoToEntity(consulenteDto.getUtente());
		Consulente consulente = dtoEntityMapper.dtoToEntity(consulenteDto);
		utente.setResponsabile(responsabile);
		utente = consulenteService.createUtenteConsulente(utente, consulente);

		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente) utente.getAnagrafica();
		consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(consulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> readConsulente(@RequestParam String codicePersona) {
		Utente utente = consulenteService.readUtenteConsulente(codicePersona);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getAnagrafica();
		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(consulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> deleteConsulente(@RequestParam String codicePersona) {
		Utente utente = consulenteService.readUtenteConsulente(codicePersona);
		Consulente consulente = (Consulente) utente.getAnagrafica();
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(consulente);
		consulenteDto.setUtente(utenteDto);

		consulenteService.deleteUtenteConsulente(codicePersona);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(consulenteDto)
				.build();
//		publishEvent(new UserCrudEvent(this, consulenteDto, CrudType.DELETE));
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> updateUser(@RequestBody ConsulenteDto consulenteDto) {
		Utente responsabile = consulenteService.readUtenteConsulente(consulenteDto.getUtente().getCodiceResponsabile());
		Utente utente = consulenteService.readUtenteConsulente(consulenteDto.getUtente().getCodicePersona());
		Consulente consulente = dtoEntityMapper.dtoToEntity(consulenteDto);
		consulente.setCodicePersona(utente.getCodicePersona());
		utente.setResponsabile(responsabile);
		utente.setAnagrafica(consulente);
		utente = consulenteService.updateUtenteConsulente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getAnagrafica();

		ConsulenteDto anagraficaResponseDto = dtoEntityMapper.entityToDto(anagrafica);
		anagraficaResponseDto.setUtente(utenteResponseDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(anagraficaResponseDto)
				.build();
//		publishEvent(new UserCrudEvent(this, anagraficaResponseDto, CrudType.UPDATE));
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-user")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> updateUser(@RequestBody UtenteDto UtenteDto) {
		Utente utente = dtoEntityMapper.dtoToEntity(UtenteDto);
		Utente responsabile = consulenteService.readUtenteConsulente(UtenteDto.getCodiceResponsabile());
		
		Consulente consulente = consulenteService.readAnagraficaConsulente(UtenteDto.getCodicePersona());
		utente.setResponsabile(responsabile);
		utente.setAnagrafica(consulente);
		utente = consulenteService.updateUtenteConsulente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getAnagrafica();

		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteResponseDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(consulenteDto)
				.build();
//		publishEvent(new UserCrudEvent(this, consulenteDto, CrudType.UPDATE));
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-status/{codicePersona}/{status}")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> editStatusUser(@PathVariable("codicePersona") String codicePersona, @PathParam("status") StatoUtenteType status) {
		Utente utente = consulenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
//		Consulente anagrafica = (Consulente)utente.getAnagrafica();
//		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse.<UtenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(utenteResponseDto)
				.build();
//		if(status == StatoUtenteType.C) {
//			publishEvent(new UserCrudEvent(this, consulenteDto, CrudType.UPDATE));
//		}
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-roles")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> editRoleUser(@RequestParam String codicePersona, @RequestBody List<RuoloDto> ruoliDto) {
		Utente utente = consulenteService.readUtenteConsulente(codicePersona);
		List<Ruolo> ruoli = dtoEntityMapper.dtoToEntityRuoloList(ruoliDto);
		utente.setRuoli(ruoli);
		utente = consulenteService.updateUtenteConsulente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse.<UtenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(utenteResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	
	@PutMapping(value = "/update-economics")
	public ResponseEntity<GenericWrapperResponse<DatiEconomiciConsulenteDto>> editDatiEconomiciConsulente(@RequestParam String codicePersona, @RequestBody DatiEconomiciConsulenteDto datiEconomiciConsulenteDto) {
		Utente utente = consulenteService.readUtenteConsulente(codicePersona);
		DatiEconomiciConsulente economics = dtoEntityMapper.dtoToEntity(datiEconomiciConsulenteDto);
		Consulente consulente = (Consulente) utente.getAnagrafica();
		consulente.setEconomics(economics);
		economics.setConsulente(consulente);
		utente.setAnagrafica(consulente);
		
		utente = consulenteService.updateUtenteConsulente(utente);
		datiEconomiciConsulenteDto = dtoEntityMapper.entityToDto(economics);

		GenericWrapperResponse<DatiEconomiciConsulenteDto> genericResponse = GenericWrapperResponse.<DatiEconomiciConsulenteDto>builder()
				.dataRichiesta(Utils.now())
				.risultato(datiEconomiciConsulenteDto)
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