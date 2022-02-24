package com.perigea.tracker.timesheet.controller;

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

import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.RuoloDto;
import com.perigea.tracker.commons.enums.RuoloType;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.RuoloService;

@RestController
@RequestMapping("/backoffice/ruoli")
public class RuoloController {

	@Autowired
	private RuoloService roleService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<RuoloDto>> createRole(@RequestBody RuoloDto ruoloDto) {
		Ruolo ruolo = dtoEntityMapper.dtoToEntity(ruoloDto);
		ruolo = roleService.createRole(ruolo);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		ResponseDto<RuoloDto> genericDto = ResponseDto.<RuoloDto>builder().data(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read/{roleName}")
	public ResponseEntity<ResponseDto<RuoloDto>> readRole(@PathVariable(name = "roleName") RuoloType roleName) {
		Ruolo ruolo = roleService.readRole(roleName);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		ResponseDto<RuoloDto> genericDto = ResponseDto.<RuoloDto>builder().data(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<RuoloDto>> updateRole(@RequestBody RuoloDto ruoloDto) {
		Ruolo ruolo = dtoEntityMapper.dtoToEntity(ruoloDto);
		ruolo = roleService.updateRole(ruolo);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		ResponseDto<RuoloDto> genericDto = ResponseDto.<RuoloDto>builder().data(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete/{roleName}")
	public ResponseEntity<ResponseDto<RuoloDto>> deleteRole(@PathVariable(name = "roleName") RuoloType roleName) {
		Ruolo ruolo = roleService.readRole(roleName);
		RuoloDto dtoRuolo = dtoEntityMapper.entityToDto(ruolo);
		roleService.deleteRole(roleName);
		ResponseDto<RuoloDto> genericDto = ResponseDto.<RuoloDto>builder().data(dtoRuolo).build();
		return ResponseEntity.ok(genericDto);
	}

}
