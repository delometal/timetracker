package com.perigea.tracker.timesheet.controller;

import java.util.Date;

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

@RestController
@RequestMapping("/nota_spese")
public class NotaSpeseController {

	@Autowired
	private NotaSpeseService notaSpeseService;

	@PostMapping(value = "/create-nota-spese")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> createNotaSpese(@RequestBody NotaSpeseInputDto notaSpeseInputDto) {
		NotaSpese notaSpese = DtoEntityMapper.INSTANCE.fromDtoToEntityNotaSpese(notaSpeseInputDto);
		notaSpese = notaSpeseService.createNotaSpese(notaSpese);
		NotaSpeseDto dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}
		
	@GetMapping(value = "/read-nota-spese")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> readNotaSpese(@RequestBody NotaSpeseKey notaSpeseKey) {
		NotaSpese notaSpese = notaSpeseService.readNotaSpese(notaSpeseKey);
		NotaSpeseDto dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}

	@PostMapping(value = "/update-nota-spese")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> updateNotaSpese(@RequestBody NotaSpeseInputDto notaSpeseInputDto) {
		NotaSpese notaSpese = DtoEntityMapper.INSTANCE.fromDtoToEntityNotaSpese(notaSpeseInputDto);
		notaSpese = notaSpeseService.updateNotaSpese(notaSpese);
		NotaSpeseDto dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/delete-nota-spese")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> deleteNotaSpese(@RequestBody NotaSpeseKey notaSpeseKey) {
		NotaSpese notaSpese = notaSpeseService.deleteNotaSpese(notaSpeseKey);
		NotaSpeseDto dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}
}
