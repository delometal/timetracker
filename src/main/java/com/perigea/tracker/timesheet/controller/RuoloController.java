package com.perigea.tracker.timesheet.controller;

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
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/backoffice/ruoli")
public class RuoloController {

	@Autowired
	private RuoloService roleService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> createRole(@RequestBody RuoloDto ruoloDto) {
		Ruolo ruolo = dtoEntityMapper.dtoToEntity(ruoloDto);
		ruolo = roleService.createRole(ruolo);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> readRole(@RequestParam RuoloType ruoloTipo) {
		Ruolo ruolo = roleService.readRole(ruoloTipo);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> updateRole(@RequestBody RuoloDto ruoloDto) {
		Ruolo ruolo = dtoEntityMapper.dtoToEntity(ruoloDto);
		ruolo = roleService.updateRole(ruolo);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-role")
	public ResponseEntity<GenericWrapperResponse<RuoloDto>> deleteRole(@RequestParam RuoloType ruoloTipo) {
		Ruolo ruolo = roleService.readRole(ruoloTipo);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		roleService.deleteRole(ruoloTipo);
		GenericWrapperResponse<RuoloDto> genericDto = GenericWrapperResponse.<RuoloDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

}
