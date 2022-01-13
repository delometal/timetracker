package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.RuoliDto;
import com.perigea.tracker.timesheet.enumerator.RuoloType;
import com.perigea.tracker.timesheet.service.RuoloService;


@RestController
public class RuoloController {

	@Autowired
	private RuoloService roleService;

	// Metodo per creare un ruolo
	@PostMapping(value = "/create-role")
	public ResponseEntity<GenericWrapperResponse<RuoliDto>> createRole(@RequestBody RuoliDto ruoloDto) {
		RuoliDto dto = roleService.createRole(ruoloDto);
		GenericWrapperResponse<RuoliDto> genericDto = GenericWrapperResponse.<RuoliDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per leggere un ruolo
	@GetMapping(value = "/read-role")
	public ResponseEntity<GenericWrapperResponse<RuoliDto>> readRole(@RequestParam RuoloType roleName) {
		RuoliDto dto = roleService.readRole(roleName);
		GenericWrapperResponse<RuoliDto> genericDto = GenericWrapperResponse.<RuoliDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per cancellare un ruolo
	@GetMapping(value = "/delete-role")
	public ResponseEntity<GenericWrapperResponse<RuoliDto>> deleteRole(@RequestParam RuoloType roleName) {
		RuoliDto dto = roleService.deleteRole(roleName);
		GenericWrapperResponse<RuoliDto> genericDto = GenericWrapperResponse.<RuoliDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per aggiornare un ruolo
	@PostMapping(value = "/update-role")
	public ResponseEntity<GenericWrapperResponse<RuoliDto>> updateRole(@RequestBody RuoliDto ruoloDto) {
		RuoliDto dto = roleService.updateRole(ruoloDto);
		GenericWrapperResponse<RuoliDto> genericDto = GenericWrapperResponse.<RuoliDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

}
