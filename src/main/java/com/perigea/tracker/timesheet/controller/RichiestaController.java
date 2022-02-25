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
import com.perigea.tracker.commons.dto.RichiestaDto;
import com.perigea.tracker.commons.dto.RichiestaHistoryDto;
import com.perigea.tracker.commons.dto.TimesheetRefDto;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.RichiestaService;

@RestController
@RequestMapping("/richieste")
public class RichiestaController {

	@Autowired
	private RichiestaService richiestaService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<RichiestaDto>> createRole(@RequestBody RichiestaDto richiestaDto) {
		Richiesta richiesta = dtoEntityMapper.dtoToEntity(richiestaDto);
		richiesta = richiestaService.createRichiesta(richiesta);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		ResponseDto<RichiestaDto> genericDto = ResponseDto.<RichiestaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<ResponseDto<RichiestaDto>> readRole(@PathVariable(name = "id") Long id) {
		Richiesta richiesta = richiestaService.readRichiesta(id);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		ResponseDto<RichiestaDto> genericDto = ResponseDto.<RichiestaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<RichiestaDto>> updateRichiesta(@RequestBody RichiestaDto richiestaDto) {
		Richiesta richiesta = dtoEntityMapper.dtoToEntity(richiestaDto);
		richiesta = richiestaService.updateRichiesta(richiesta);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		ResponseDto<RichiestaDto> genericDto = ResponseDto.<RichiestaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ResponseDto<RichiestaDto>> deleteRichiesta(@PathVariable(name = "id") Long id) {
		Richiesta richiesta = richiestaService.readRichiesta(id);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		richiestaService.deleteRichiesta(id);
		ResponseDto<RichiestaDto> genericDto = ResponseDto.<RichiestaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-history-element")
	public ResponseEntity<ResponseDto<RichiestaDto>> updateRichiestaHistory(
			@RequestBody RichiestaHistoryDto richiestaHistoryDto) {
		RichiestaHistory richiestaHistory = dtoEntityMapper.dtoToEntity(richiestaHistoryDto);
		Richiesta richiesta = richiestaService.updateRichiestaHistory(richiestaHistory);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		ResponseDto<RichiestaDto> genericDto = ResponseDto.<RichiestaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-history-element")
	public ResponseEntity<ResponseDto<RichiestaDto>> deleteRichiestaHistory(
			@RequestBody RichiestaHistoryDto richiestaHistoryDto) {
		RichiestaHistory richiestaHistory = dtoEntityMapper.dtoToEntity(richiestaHistoryDto);
		Richiesta richiesta = richiestaService.deleteRichiestaHistory(richiestaHistory);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		ResponseDto<RichiestaDto> genericDto = ResponseDto.<RichiestaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PostMapping(value ="/send-timesheet-request")
	public ResponseEntity<ResponseDto<RichiestaDto>> sendRichiestaTimesheet(
			@RequestBody TimesheetRefDto timesheetReferences) {
		Richiesta richiesta = richiestaService.sendRichiestaTimesheet(timesheetReferences);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		ResponseDto<RichiestaDto> genericDto = ResponseDto.<RichiestaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);

	}

}
