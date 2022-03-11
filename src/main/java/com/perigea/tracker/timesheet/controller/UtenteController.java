package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@GetMapping(value = "/check-token/{token}")
	public ResponseEntity<ResponseDto<String>> checkToken(@PathVariable(name = "token") String token) {
		return (utenteService.checkToken(token))
				? ResponseEntity.ok().body(ResponseDto.<String>builder().data("token is valid").build())
				: ResponseEntity.badRequest().body(
						ResponseDto.<String>builder().type(ResponseType.ERROR).code(401).data("token expired").build());
	}

	@PutMapping(value = "/update-user-password")
	public ResponseEntity<ResponseDto<UtenteDto>> updateUtentePassword(@RequestBody UserCredentialDto credentialDto) {
		Utente utente = utenteService.updateUtentePassword(credentialDto.getCodicePersona(),
				credentialDto.getPassword());
		UtenteDto dto = dtoEntityMapper.entityToDto(utente);
		return ResponseEntity.ok(ResponseDto.<UtenteDto>builder().data(dto).build());
	}

	@PutMapping(value = "/update-user-status/{codicePersona}/{status}")
	public ResponseEntity<ResponseDto<UtenteDto>> updateUtenteStatus(@PathVariable String codicePersona,
			@PathVariable StatoUtenteType status) {
		Utente utente = utenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto dto = dtoEntityMapper.entityToDto(utente);
		return ResponseEntity.ok(ResponseDto.<UtenteDto>builder().data(dto).build());
	}
	

	@GetMapping(value = "/checkSpecification/{username}/{tipoKey}/{statoUtente}")
	public ResponseEntity<ResponseDto<List<UtenteDto>>> checkToken(@PathVariable String username, @PathVariable AnagraficaType tipoKey, @PathVariable StatoUtenteType statoUtente ) {
		
		List<Utente> utenti = utenteService.searchUtenti(username, tipoKey, statoUtente);
		
		List<UtenteDto> utentiDto = dtoEntityMapper.entityToDtoUtenteList(utenti);
		
		return  ResponseEntity.ok().body(ResponseDto.<List<UtenteDto>>builder().data(utentiDto).build());
	}
}
