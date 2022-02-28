package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.perigea.tracker.timesheet.service.GruppoContattoService;
import com.perigea.tracker.timesheet.service.UtenteService;

@RestController
@RequestMapping("/utenti")
public class ContactDetailsController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private GruppoContattoService gruppoContattoService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;	
	

	@GetMapping(value = "/contact-details/read-by-id/{contattoId}")
	public ResponseEntity<ResponseDto<ContactDto>> readUserContactDetails(
			@PathVariable(name = "userId") String userId) {
		Utente utente = utenteService.readUtente(userId);
		ContactDto contactDetails = dtoEntityMapper.entityToContactDto(utente);
		ResponseDto<ContactDto> genericResponse = ResponseDto.<ContactDto>builder().data(contactDetails).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/contact-details/read-all-group-contact-details/{groupId}")
	public ResponseEntity<ResponseDto<List<ContactDto>>> readAllContactDetails(
			@PathVariable(name = "groupId") Long groupId) {
		List<Utente> utenti = gruppoContattoService.readAllContactsByGroupId(groupId);
		List<ContactDto> dtos = dtoEntityMapper.entityToContactDtoList(utenti);
		ResponseDto<List<ContactDto>> genericResponse = ResponseDto.<List<ContactDto>>builder().data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}

	
	//CRUD per creazione contatto semplice
	@PostMapping(value = "/contatto/create")
	public ResponseEntity<ResponseDto<UtenteDto>> createContatto(@RequestBody ContactDto contactDto) {
		Utente utente = utenteService.saveContatto(contactDto);
		UtenteDto contatto = dtoEntityMapper.entityToDto(utente);

		ResponseDto<UtenteDto> response = ResponseDto.<UtenteDto>builder().data(contatto).build();
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/contatto/update")
	public ResponseEntity<ResponseDto<UtenteDto>> updateContatto(@RequestBody ContactDto contactDto) {
		Utente utente = utenteService.updateContatto(contactDto);
		UtenteDto contatto = dtoEntityMapper.entityToDto(utente);

		ResponseDto<UtenteDto> response = ResponseDto.<UtenteDto>builder().data(contatto).build();
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
	public ResponseEntity<ResponseDto<UtenteDto>> deleteContatto(@PathVariable String codicePersona) {

		Utente contatto = utenteService.readUtente(codicePersona);
		UtenteDto contattoDto = dtoEntityMapper.entityToDto(contatto);
		utenteService.deleteUtente(codicePersona);

		ResponseDto<UtenteDto> response = ResponseDto.<UtenteDto>builder().data(contattoDto).build();
		return ResponseEntity.ok(response);
	}
	/*
	 * QUESTION bisogna creare altre crud relative ai contatti, ad esempio che
	 * prendo una lista di utenti a caso non per forza appartenenti ad un gruppo
	 */

	/*
	 * QUESTION posso utilizzare questo controller come possibile punto per
	 * comunicare con il calendar nel senso che da qua implemento in automatico la
	 * collection contact attraverso una rest client?
	 */
}
