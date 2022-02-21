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
import com.perigea.tracker.commons.dto.GruppoContattoDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.GruppoContattoService;

@RestController
@RequestMapping("/contatti-e-gruppi")
public class GruppoContattoController {

	@Autowired
	private GruppoContattoService gruppoContattoService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> createGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		gruppo = gruppoContattoService.createGruppo(gruppo);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppo);
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto
				.<GruppoContattoDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> readGruppo(@PathVariable(name="id") Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppoContatto);
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto
				.<GruppoContattoDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> updateGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		gruppo = gruppoContattoService.updateGruppo(gruppo);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppo);
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto
				.<GruppoContattoDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> deleteGruppo(@PathVariable(name="id") Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppoContatto);
		gruppoContattoService.deleteGruppo(id);
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto
				.<GruppoContattoDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/contatti/create")
	public ResponseEntity<ResponseDto<UtenteDto>> createContatto(@RequestBody UtenteDto contattoDto) {
		Utente contatto = dtoEntityMapper.dtoToEntity(contattoDto);
		contatto = gruppoContattoService.createContatto(contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/contatti/read/{contattoId}")
	public ResponseEntity<ResponseDto<UtenteDto>> readContatto(@PathVariable(name="contattoId") String contattoId) {
		Utente contatto = gruppoContattoService.readContatto(contattoId);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/contatti/read/{nome}/{cognome}")
	public ResponseEntity<ResponseDto<UtenteDto>> readContatto(@PathVariable(name="nome") String nome, @PathVariable (name = "cognome") String cognome) {
		Utente contatto = gruppoContattoService.readContatto(nome, cognome);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/contatti/update")
	public ResponseEntity<ResponseDto<UtenteDto>> updateContatto(@RequestBody ContactDto contattoDto) {
		Utente contatto = gruppoContattoService.readContatto(contattoDto.getCodicePersona());
		contatto = gruppoContattoService.updateContatto(contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/contatti/delete/{contattoId}")
	public ResponseEntity<ResponseDto<UtenteDto>> deleteContatto(@PathVariable(name="contattoId") String contattoId) {
		Utente contatto = gruppoContattoService.readContatto(contattoId);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		gruppoContattoService.deleteContatto(contattoId);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-all-contatti")
	public ResponseEntity<ResponseDto<List<UtenteDto>>> readAllContatti() {
		List<Utente> contatti = gruppoContattoService.readAllContatti();
		List<UtenteDto> dtos = dtoEntityMapper.entityToDtoUtenteList(contatti);
		ResponseDto<List<UtenteDto>> genericResponse = ResponseDto
				.<List<UtenteDto>>builder().timestamp(Utils.now()).data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all-contatti/{groupId}")
	public ResponseEntity<ResponseDto<List<UtenteDto>>> readAllContatti(@PathVariable(name = "groupId") Long groupId) {
		List<Utente> contatti = gruppoContattoService.readAllContactsByGroupId(groupId);
		List<UtenteDto> dtos = dtoEntityMapper.entityToDtoUtenteList(contatti);
		ResponseDto<List<UtenteDto>> genericResponse = ResponseDto
				.<List<UtenteDto>>builder().timestamp(Utils.now()).data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
}
