package com.perigea.tracker.timesheet.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.ContattoDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.GruppoContattoDto;
import com.perigea.tracker.timesheet.entity.Contatto;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.service.GruppoContattoService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/contatti-e-gruppi")
public class GruppoContattoController {

	@Autowired
	private GruppoContattoService gruppoContattoService;

	@PostMapping(value = "/create-gruppo")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> createGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = DtoEntityMapper.INSTANCE.fromDtoToEntityGruppo(gruppoContattoDto);
		gruppo = gruppoContattoService.createGruppo(gruppo);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppo);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-gruppo")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> readGruppo(@RequestParam Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppoContatto);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-gruppo")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> updateGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = DtoEntityMapper.INSTANCE.fromDtoToEntityGruppo(gruppoContattoDto);
		gruppo = gruppoContattoService.updateGruppo(gruppo);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppo);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-gruppo")
	ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> deleteGruppo(@RequestParam Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppoContatto);
		gruppoContattoService.deleteGruppo(id);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/create-contatto")
	public ResponseEntity<GenericWrapperResponse<ContattoDto>> createContatto(@RequestBody ContattoDto contattoDto) {
		Contatto contatto = DtoEntityMapper.INSTANCE.fromDtoToEntityContatto(contattoDto);
		contatto = gruppoContattoService.createContatto(contatto);
		ContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoContatto(contatto);
		GenericWrapperResponse<ContattoDto> genericResponse = GenericWrapperResponse
				.<ContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-contatto")
	public ResponseEntity<GenericWrapperResponse<ContattoDto>> readContatto(@RequestParam Long id) {
		Contatto contatto = gruppoContattoService.readContatto(id);
		ContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoContatto(contatto);
		GenericWrapperResponse<ContattoDto> genericResponse = GenericWrapperResponse
				.<ContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-contatto")
	public ResponseEntity<GenericWrapperResponse<ContattoDto>> updateContatto(@RequestBody ContattoDto contattoDto) {
		Contatto contatto = DtoEntityMapper.INSTANCE.fromDtoToEntityContatto(contattoDto);
		contatto = gruppoContattoService.updateContatto(contatto);
		ContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoContatto(contatto);
		GenericWrapperResponse<ContattoDto> genericResponse = GenericWrapperResponse
				.<ContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-contatto")
	public ResponseEntity<GenericWrapperResponse<ContattoDto>> deleteContatto(@RequestParam Long id) {
		Contatto contatto = gruppoContattoService.readContatto(id);
		ContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoContatto(contatto);
		gruppoContattoService.deleteContatto(id);
		GenericWrapperResponse<ContattoDto> genericResponse = GenericWrapperResponse
				.<ContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-all-contatti")
	public ResponseEntity<GenericWrapperResponse<List<ContattoDto>>> readAllContatti() {
		List<Contatto> contatti = gruppoContattoService.readAllContatti();
		List<ContattoDto> dtos = DtoEntityMapper.INSTANCE.fromEntityToDtoContatto(contatti);
		GenericWrapperResponse<List<ContattoDto>> genericResponse = GenericWrapperResponse
				.<List<ContattoDto>>builder().dataRichiesta(new Date()).risultato(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all-contatti/{groupId}")
	public ResponseEntity<GenericWrapperResponse<List<ContattoDto>>> readAllContatti(@PathVariable(name = "groupId") Long groupId) {
		List<Contatto> contatti = gruppoContattoService.readAllContactsByGroupId(groupId);
		List<ContattoDto> dtos = DtoEntityMapper.INSTANCE.fromEntityToDtoContatto(contatti);
		GenericWrapperResponse<List<ContattoDto>> genericResponse = GenericWrapperResponse
				.<List<ContattoDto>>builder().dataRichiesta(new Date()).risultato(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
}
