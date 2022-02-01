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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.AnagraficaDto;
import com.perigea.tracker.timesheet.dto.ContattoDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.GruppoContattoDto;
import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.service.GruppoContattoService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/contatti-e-gruppi")
public class GruppoContattoController {

	@Autowired
	private GruppoContattoService gruppoContattoService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> createGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		gruppo = gruppoContattoService.createGruppo(gruppo);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppo);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> readGruppo(@RequestParam Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppoContatto);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> updateGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		gruppo = gruppoContattoService.updateGruppo(gruppo);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppo);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> deleteGruppo(@RequestParam Long id) {
		Gruppo gruppoContatto = gruppoContattoService.readGruppo(id);
		GruppoContattoDto dto = dtoEntityMapper.entityToDto(gruppoContatto);
		gruppoContattoService.deleteGruppo(id);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/contatti/create")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> createContatto(@RequestBody ContattoDto contattoDto) {
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(contattoDto);
		contatto = gruppoContattoService.createContatto(contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/contatti/read")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> readContatto(@RequestParam String id) {
		Anagrafica contatto = gruppoContattoService.readContatto(id);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/contatti/update")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> updateContatto(@RequestBody ContattoDto contattoDto) {
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(contattoDto);
		contatto = gruppoContattoService.updateContatto(contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/contatti/delete")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> deleteContatto(@RequestParam String id) {
		Anagrafica contatto = gruppoContattoService.readContatto(id);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		gruppoContattoService.deleteContatto(id);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-all-contatti")
	public ResponseEntity<GenericWrapperResponse<List<AnagraficaDto>>> readAllContatti() {
		List<Anagrafica> contatti = gruppoContattoService.readAllContatti();
		List<AnagraficaDto> dtos = dtoEntityMapper.entityToDtoAnagraficaList(contatti);
		GenericWrapperResponse<List<AnagraficaDto>> genericResponse = GenericWrapperResponse
				.<List<AnagraficaDto>>builder().dataRichiesta(TSUtils.now()).risultato(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all-contatti/{groupId}")
	public ResponseEntity<GenericWrapperResponse<List<AnagraficaDto>>> readAllContatti(@PathVariable(name = "groupId") Long groupId) {
		List<Anagrafica> contatti = gruppoContattoService.readAllContactsByGroupId(groupId);
		List<AnagraficaDto> dtos = dtoEntityMapper.entityToDtoAnagraficaList(contatti);
		GenericWrapperResponse<List<AnagraficaDto>> genericResponse = GenericWrapperResponse
				.<List<AnagraficaDto>>builder().dataRichiesta(TSUtils.now()).risultato(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
}
