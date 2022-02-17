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

import com.perigea.tracker.commons.dto.FestivitaDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.FestivitaService;

@RestController
@RequestMapping("/backoffice/festivita")
public class FestivitaController {

	@Autowired
	private FestivitaService festeService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<FestivitaDto>> createFestivita(@RequestBody FestivitaDto festivitaDto) {
		Festivita festivita = dtoEntityMapper.dtoToEntity(festivitaDto);
		festivita = festeService.createFestivita(festivita);
		FestivitaDto dtoFestivita = dtoEntityMapper.entityToDto(festivita);
		ResponseDto<FestivitaDto> genericDto = ResponseDto.<FestivitaDto>builder()
				.timestamp(Utils.now()).data(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read/{id}")
	public ResponseEntity<ResponseDto<FestivitaDto>> readFestivita(@PathVariable("id") Integer id) {
		Festivita festivita = festeService.readFestivita(id);
		FestivitaDto dtoFestivita = dtoEntityMapper.entityToDto(festivita);
		ResponseDto<FestivitaDto> genericDto = ResponseDto.<FestivitaDto>builder()
				.timestamp(Utils.now()).data(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-by-name/{nomeFestivita}")
	public ResponseEntity<ResponseDto<FestivitaDto>> readFestivita(@PathVariable("nomeFestivita") String nomeFestivita) {
		Festivita festivita = festeService.readFestivitaByName(nomeFestivita);
		FestivitaDto dtoFestivita = dtoEntityMapper.entityToDto(festivita);
		ResponseDto<FestivitaDto> genericDto = ResponseDto.<FestivitaDto>builder()
				.timestamp(Utils.now()).data(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<FestivitaDto>> updateFestivita(@RequestBody FestivitaDto festivitaDto) {
		Festivita festivita = dtoEntityMapper.dtoToEntity(festivitaDto);
		festivita = festeService.updateFestivita(festivita);
		FestivitaDto dtoFestivita = dtoEntityMapper.entityToDto(festivita);
		ResponseDto<FestivitaDto> genericDto = ResponseDto.<FestivitaDto>builder()
				.timestamp(Utils.now()).data(dtoFestivita).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ResponseDto<FestivitaDto>> deleteFestivita(@PathVariable("id") Integer id) {
		Festivita festivita = festeService.readFestivita(id);
		FestivitaDto festivitaDto = dtoEntityMapper.entityToDto(festivita);
		festeService.deleteFestivita(id);
		ResponseDto<FestivitaDto> genericDto = ResponseDto.<FestivitaDto>builder()
				.timestamp(Utils.now()).data(festivitaDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
}
