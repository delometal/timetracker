package com.perigea.tracker.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.NotaSpeseInputDto;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;
import com.perigea.tracker.timesheet.service.NotaSpeseService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/note-spesa")
public class NotaSpeseController {

	@Autowired
	private NotaSpeseService notaSpeseService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> createNotaSpese(@RequestBody NotaSpeseInputDto notaSpeseInputDto) {
		NotaSpese notaSpese = dtoEntityMapper.dtoToEntity(notaSpeseInputDto);
		notaSpese = notaSpeseService.createNotaSpese(notaSpese);
		NotaSpeseDto dtoNotaSpese = dtoEntityMapper.entityToDto(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}
		
	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> readNotaSpese(@RequestBody NotaSpeseKey notaSpeseKey) {
		NotaSpese notaSpese = notaSpeseService.readNotaSpese(notaSpeseKey);
		NotaSpeseDto dtoNotaSpese = dtoEntityMapper.entityToDto(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> updateNotaSpese(@RequestBody NotaSpeseInputDto notaSpeseInputDto) {
		NotaSpese notaSpese = dtoEntityMapper.dtoToEntity(notaSpeseInputDto);
		notaSpese = notaSpeseService.updateNotaSpese(notaSpese);
		NotaSpeseDto dtoNotaSpese = dtoEntityMapper.entityToDto(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> deleteNotaSpese(@RequestBody NotaSpeseKey notaSpeseKey) {
		NotaSpese notaSpese = notaSpeseService.readNotaSpese(notaSpeseKey);
		NotaSpeseDto dtoNotaSpese = dtoEntityMapper.entityToDto(notaSpese);
		notaSpeseService.deleteNotaSpese(notaSpeseKey);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(TSUtils.now()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}
}
