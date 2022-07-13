package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.UserCredentialDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.enums.AnagraficaType;
import com.perigea.tracker.commons.enums.ResponseType;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.UtenteService;

@RestController
@RequestMapping("/utente")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	
	@GetMapping(value = "/read-all-utenti")
	public ResponseEntity<ResponseDto<List<UtenteDto>>> readAll() {
		List<Utente> utenti = utenteService.readAll();
		List<UtenteDto> dtos = dtoEntityMapper.entityToDtoUtenteList(utenti);
		ResponseDto<List<UtenteDto>> genericResponse = ResponseDto.<List<UtenteDto>>builder().data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-utente-by-id/{codicePersona}")
	public ResponseEntity<ResponseDto<UtenteDto>> readUtente(@PathVariable String codicePersona) {
		Utente utente = utenteService.readUtente(codicePersona);
		UtenteDto dto = dtoEntityMapper.entityToDto(utente);
		ResponseDto<UtenteDto> genericResponse = ResponseDto.<UtenteDto>builder().data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/check-token/{token}")
	public ResponseEntity<ResponseDto<String>> checkToken(@PathVariable(name = "token") String token) {
		return (utenteService.checkToken(token))
				? ResponseEntity.ok().body(ResponseDto.<String>builder().data("token is valid").build())
				: ResponseEntity.badRequest().body(
						ResponseDto.<String>builder().type(ResponseType.ERROR).code(401).data("token expired").build());
	}
	
	@GetMapping(value = "/recover-password-request/{username}")
	public ResponseEntity<ResponseDto<String>> recoverPasswordRequest(@PathVariable(name = "username") String username) {
		utenteService.sendRecoverPasswordRequest(username);
		return ResponseEntity.ok().body(ResponseDto.<String>builder().data("recover Request sent").build());
	}

	@PutMapping(value = "/update-user-password/{codiceControllo}")
	public ResponseEntity<ResponseDto<UtenteDto>> updateUtentePassword(@RequestBody UserCredentialDto credentialDto,
			@PathVariable(name = "codiceControllo") String codiceControllo) {
		Utente utente = utenteService.updatePasswordWithCode(codiceControllo, credentialDto.getUsername(),
				credentialDto.getPassword());
		UtenteDto dto = dtoEntityMapper.entityToDto(utente);
		return ResponseEntity.ok(ResponseDto.<UtenteDto>builder().data(dto).build());
	}

	@PutMapping(value = "/update-user-status/{codicePersona}/{status}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<UtenteDto>> updateUtenteStatus(@PathVariable String codicePersona,
			@PathVariable StatoUtenteType status) {
		Utente utente = utenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto dto = dtoEntityMapper.entityToDto(utente);
		return ResponseEntity.ok(ResponseDto.<UtenteDto>builder().data(dto).build());
	}
	
		

	@GetMapping(value = "/checkSpecification/{username}/{tipoAnagrafica}/{statoUtente}")
	public ResponseEntity<ResponseDto<List<UtenteDto>>> checkToken(@PathVariable String username, @PathVariable AnagraficaType tipoAnagrafica, @PathVariable StatoUtenteType statoUtente ) {
		
		List<Utente> utenti = utenteService.searchUtenti(username, tipoAnagrafica, statoUtente);
		
		List<UtenteDto> utentiDto = dtoEntityMapper.entityToDtoUtenteList(utenti);
		
		return  ResponseEntity.ok().body(ResponseDto.<List<UtenteDto>>builder().data(utentiDto).build());
	}
	
	@GetMapping(value = "/checkSpecification/{name}/{luogoDiNascita}")
	public ResponseEntity<ResponseDto<List<UtenteDto>>> checkToken(@PathVariable String name, @PathVariable String luogoDiNascita ) {
		
		List<Utente> utenti = utenteService.searchUtenti(name, luogoDiNascita);
		
		List<UtenteDto> utentiDto = dtoEntityMapper.entityToDtoUtenteList(utenti);
		
		return  ResponseEntity.ok().body(ResponseDto.<List<UtenteDto>>builder().data(utentiDto).build());
	}
}
