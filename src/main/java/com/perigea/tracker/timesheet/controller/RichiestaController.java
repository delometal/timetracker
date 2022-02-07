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

import com.perigea.tracker.commons.dto.GenericWrapperResponse;
import com.perigea.tracker.commons.dto.RichiestaDto;
import com.perigea.tracker.commons.dto.RichiestaHistoryDto;
import com.perigea.tracker.commons.utils.Utils;
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
	public ResponseEntity<GenericWrapperResponse<RichiestaDto>> createRole(@RequestBody RichiestaDto richiestaDto) {
		Richiesta richiesta = dtoEntityMapper.dtoToEntity(richiestaDto);
		richiesta = richiestaService.createRichiesta(richiesta);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		GenericWrapperResponse<RichiestaDto> genericDto = GenericWrapperResponse.<RichiestaDto>builder()
				.timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<RichiestaDto>> readRole(@RequestParam Integer id) {
		Richiesta richiesta = richiestaService.readRichiesta(id);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		GenericWrapperResponse<RichiestaDto> genericDto = GenericWrapperResponse.<RichiestaDto>builder()
				.timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<RichiestaDto>> updateRichiesta(@RequestBody RichiestaDto richiestaDto) {
		Richiesta richiesta = dtoEntityMapper.dtoToEntity(richiestaDto);
		richiesta = richiestaService.updateRichiesta(richiesta);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		GenericWrapperResponse<RichiestaDto> genericDto = GenericWrapperResponse.<RichiestaDto>builder()
				.timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<RichiestaDto>> deleteRichiesta(@RequestParam Integer id) {
		Richiesta richiesta = richiestaService.readRichiesta(id);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		richiestaService.deleteRichiesta(id);
		GenericWrapperResponse<RichiestaDto> genericDto = GenericWrapperResponse.<RichiestaDto>builder()
				.timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update-history-element")
	public ResponseEntity<GenericWrapperResponse<RichiestaDto>> updateRichiestaHistory(@RequestBody RichiestaHistoryDto richiestaHistoryDto) {
		RichiestaHistory richiestaHistory = dtoEntityMapper.dtoToEntity(richiestaHistoryDto);
		Richiesta richiesta = richiestaService.updateRichiestaHistory(richiestaHistory);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		GenericWrapperResponse<RichiestaDto> genericDto = GenericWrapperResponse.<RichiestaDto>builder()
				.timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-history-element")
	public ResponseEntity<GenericWrapperResponse<RichiestaDto>> deleteRichiestaHistory(@RequestBody RichiestaHistoryDto richiestaHistoryDto) {
		RichiestaHistory richiestaHistory = dtoEntityMapper.dtoToEntity(richiestaHistoryDto);
		Richiesta richiesta = richiestaService.deleteRichiestaHistory(richiestaHistory);
		RichiestaDto dto = dtoEntityMapper.entityToDto(richiesta);
		GenericWrapperResponse<RichiestaDto> genericDto = GenericWrapperResponse.<RichiestaDto>builder()
				.timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
}
