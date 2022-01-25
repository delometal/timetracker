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
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.enums.RuoloType;
import com.perigea.tracker.timesheet.service.RuoloService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/backoffice/ruoli")
public class RuoloController {

	@Autowired
	private RuoloService roleService;

	@PostMapping(value = "/create-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> createRole(@RequestBody RuoloDto ruoloDto) {
		Ruolo ruolo = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
		ruolo = roleService.createRole(ruolo);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> readRole(@RequestParam RuoloType ruoloTipo) {
		Ruolo ruolo = roleService.readRole(ruoloTipo);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> updateRole(@RequestBody RuoloDto ruoloDto) {
		Ruolo ruolo = DtoEntityMapper.INSTANCE.fromDtoToEntityRuoli(ruoloDto);
		ruolo = roleService.updateRole(ruolo);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> deleteRole(@RequestParam RuoloType ruoloTipo) {
		Ruolo ruolo = roleService.deleteRole(ruoloTipo);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

}
