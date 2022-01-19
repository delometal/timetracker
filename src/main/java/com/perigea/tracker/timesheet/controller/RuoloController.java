package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	// Metodo per creare un ruolo
	@PostMapping(value = "/create-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> createRole(@RequestBody RuoloDto ruoloDto) {
//		RuoloDto dto = roleService.createRole(ruoloDto);
		Ruolo ruolo = roleService.createRole(ruoloDto);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per leggere un ruolo
	@GetMapping(value = "/read-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> readRole(@RequestParam RuoloType ruoloTipo) {
//		RuoloDto dto = roleService.readRole(roleName);
		Ruolo ruolo = roleService.readRole(ruoloTipo);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per cancellare un ruolo
	@GetMapping(value = "/delete-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> deleteRole(@RequestParam RuoloType ruoloTipo) {
//		RuoloDto dto = roleService.deleteRole(roleName);
		Ruolo ruolo = roleService.deleteRole(ruoloTipo);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per aggiornare un ruolo
	@PostMapping(value = "/update-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> updateRole(@RequestBody RuoloDto ruoloDto) {
//		RuoloDto dto = roleService.updateRole(ruoloDto);
		Ruolo ruolo = roleService.updateRole(ruoloDto);
		RuoloDto dtoRuolo = DtoEntityMapper.INSTANCE.fromEntityToDtoRuoli(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(new Date()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

}
