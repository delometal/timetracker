package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.ContactDetailsService;
import com.perigea.tracker.timesheet.service.UtenteService;

@RestController
@RequestMapping("/utenti")
@CrossOrigin(allowedHeaders = "*", origins = "*", originPatterns = "*", exposedHeaders = "*")
public class ContactDetailsController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@Autowired
	private ContactDetailsService contactDetailsService;

	
	@GetMapping(value = "/contact-details/read-by-id/{userId}")
	public ResponseEntity<ResponseDto<ContactDto>> readUserContactDetails(
			@PathVariable(name = "userId") String userId) {
		ContactDto contactDetails = contactDetailsService.readUserContactDetails(userId);
		ResponseDto<ContactDto> genericResponse = ResponseDto.<ContactDto>builder().data(contactDetails).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/contact-details/read-all-group-contact-details/{groupId}")
	public ResponseEntity<ResponseDto<List<ContactDto>>> readAllContactDetails(
			@PathVariable(name = "groupId") Long groupId) {
		List<ContactDto> dtos = contactDetailsService.readAllContactDetails(groupId);
		ResponseDto<List<ContactDto>> genericResponse = ResponseDto.<List<ContactDto>>builder().data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}

	// CRUD per creazione contatto semplice
	@PostMapping(value = "/contatto/create")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<UtenteDto>> createContatto(@RequestBody UtenteDto esternoDto) {
		
		Utente utente = dtoEntityMapper.dtoToEntity(esternoDto);
		utente = utenteService.saveContattoEsterno(utente);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);

		ResponseDto<UtenteDto> response = ResponseDto.<UtenteDto>builder().data(utenteDto).build();
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/contatto/update")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<UtenteDto>> updateContatto(@RequestBody UtenteDto esternoDto) {
		
		Utente utente = dtoEntityMapper.dtoToEntity(esternoDto);
		utente = utenteService.updateUtente(utente);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		
		ResponseDto<UtenteDto> response = ResponseDto.<UtenteDto>builder().data(utenteDto).build();
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/contatto/{codicePersona}")
	public ResponseEntity<ResponseDto<UtenteDto>> getContatto(@PathVariable String codicePersona) {

		Utente contatto = utenteService.readUtente(codicePersona);
		UtenteDto contattoDto = dtoEntityMapper.entityToDto(contatto);

		ResponseDto<UtenteDto> response = ResponseDto.<UtenteDto>builder().data(contattoDto).build();
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/contatto/delete/{codicePersona}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<UtenteDto>> deleteContatto(@PathVariable String codicePersona) {

		Utente contatto = utenteService.readUtente(codicePersona);
		UtenteDto contattoDto = dtoEntityMapper.entityToDto(contatto);
		utenteService.deleteUtente(codicePersona);

		ResponseDto<UtenteDto> response = ResponseDto.<UtenteDto>builder().data(contattoDto).build();
		return ResponseEntity.ok(response);
	}

}
