package com.perigea.tracker.timesheet.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.perigea.tracker.commons.dto.GruppoContattoDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.GruppoContattoService;
import com.perigea.tracker.timesheet.service.UtenteService;

@RestController
@RequestMapping("/contatti-e-gruppi")
public class GruppoContattoController {

	@Autowired
	private GruppoContattoService gruppoContattoService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@Autowired
	private UtenteService utenteService;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<GruppoContattoDto>> createGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		List<Utente> contatti= gruppoContattoDto.getContatti().stream()
				.map(c -> utenteService.loadUtente(c.getCodicePersona()))
				.collect(Collectors.toList());
		gruppo.setContatti(contatti);
		
		gruppo = gruppoContattoService.createGruppo(gruppo);
		gruppoContattoDto.setId(gruppo.getId());
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto
				.<GruppoContattoDto>builder().timestamp(Utils.now()).data(gruppoContattoDto).build();
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
		gruppoContattoService.deleteGruppo(gruppoContattoDto.getId());
		Gruppo gruppo = dtoEntityMapper.dtoToEntity(gruppoContattoDto);
		List<Utente> contatti= gruppoContattoDto.getContatti().stream()
				.map(c -> utenteService.loadUtente(c.getCodicePersona()))
				.collect(Collectors.toList());
		gruppo.setContatti(contatti);
		
		gruppo = gruppoContattoService.createGruppo(gruppo);
		gruppoContattoDto.setId(gruppo.getId());
		ResponseDto<GruppoContattoDto> genericResponse = ResponseDto
				.<GruppoContattoDto>builder().timestamp(Utils.now()).data(gruppoContattoDto).build();
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
	
}
