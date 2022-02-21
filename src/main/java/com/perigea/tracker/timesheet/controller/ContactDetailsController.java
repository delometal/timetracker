package com.perigea.tracker.timesheet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.ContactDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.GruppoContattoService;
import com.perigea.tracker.timesheet.service.UtenteService;

@RestController
@RequestMapping("/recapiti")
public class ContactDetailsController {
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private GruppoContattoService gruppoContattoService;

	@GetMapping(value = "/contact-details/read-by-id/{contattoId}")
	public ResponseEntity<ResponseDto<ContactDto>> readUserContactDetails(
			@PathVariable(name = "userId") String userId) {
		Utente utente = utenteService.readUtente(userId);
		ContactDto contactDetails = dtoEntityMapper.entityToContactDto(utente);
		ResponseDto<ContactDto> genericResponse = ResponseDto.<ContactDto>builder().timestamp(Utils.now())
				.data(contactDetails).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/contact-details/read/nome/{nome}/cognome/{cognome}")
	public ResponseEntity<ResponseDto<ContactDto>> readUserContactDetails(@PathVariable(name = "nome") String nome,
			@PathVariable(name = "cognome") String cognome) {
		Utente utente = utenteService.readUtente(nome, cognome);
		ContactDto contactDetails = dtoEntityMapper.entityToContactDto(utente);
		ResponseDto<ContactDto> genericResponse = ResponseDto.<ContactDto>builder().timestamp(Utils.now())
				.data(contactDetails).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/contact-details/read-all-group-contact-details/{groupId}")
	public ResponseEntity<ResponseDto<List<ContactDto>>> readAllContactDetails(
			@PathVariable(name = "groupId") Long groupId) {
		List<Utente> utenti = gruppoContattoService.readAllContactsByGroupId(groupId);
		List<ContactDto> dtos = dtoEntityMapper.entityToContactDtoList(utenti);
		ResponseDto<List<ContactDto>> genericResponse = ResponseDto.<List<ContactDto>>builder().timestamp(Utils.now())
				.data(dtos).build();
		return ResponseEntity.ok(genericResponse);
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
