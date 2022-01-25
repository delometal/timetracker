package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.GruppoContattoDto;
import com.perigea.tracker.timesheet.entity.Gruppo;
import com.perigea.tracker.timesheet.service.GruppoService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/gruppi")
public class GruppoController {

	@Autowired
	private GruppoService gruppoService;

	@PostMapping(value = "/create-gruppo")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> createGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = DtoEntityMapper.INSTANCE.fromDtoToEntityGruppo(gruppoContattoDto);
		gruppo = gruppoService.createGruppo(gruppo);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppo);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-gruppo")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> readGruppo(@RequestParam Long id) {
		Gruppo gruppoContatto = gruppoService.readGruppo(id);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppoContatto);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-gruppo")
	public ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> updateGruppo(@RequestBody GruppoContattoDto gruppoContattoDto) {
		Gruppo gruppo = DtoEntityMapper.INSTANCE.fromDtoToEntityGruppo(gruppoContattoDto);
		gruppo = gruppoService.updateGruppo(gruppo);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppo);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-gruppo")
	ResponseEntity<GenericWrapperResponse<GruppoContattoDto>> deleteGruppo(@RequestParam Long id) {
		Gruppo gruppoContatto = gruppoService.deleteGruppo(id);
		GruppoContattoDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoGruppo(gruppoContatto);
		GenericWrapperResponse<GruppoContattoDto> genericResponse = GenericWrapperResponse
				.<GruppoContattoDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
}
