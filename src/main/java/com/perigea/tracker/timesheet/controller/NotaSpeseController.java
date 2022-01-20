package com.perigea.tracker.timesheet.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.wrapper.NotaSpeseDtoWrpper;
import com.perigea.tracker.timesheet.entity.NotaSpese;
import com.perigea.tracker.timesheet.service.NotaSpeseService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/nota_spese")
public class NotaSpeseController {

	@Autowired
	private NotaSpeseService notaSpeseService;

	// Metodo per aggiornare una nota spese
	@PostMapping(value = "/create-nota-spese")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> createNotaSpese(@RequestBody NotaSpeseDtoWrpper notaSpeseDtoWrpper ) {
		NotaSpese notaSpese = notaSpeseService.createNotaSpese(notaSpeseDtoWrpper.getNotaSpeseDto(), notaSpeseDtoWrpper.getId());
		NotaSpeseDto dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}
		
	// Metodo per leggere una nota spese
	@GetMapping(value = "/read-nota-spese")
	public ResponseEntity<GenericWrapperResponse<List<NotaSpeseDto>>> readNotaSpese(@RequestParam String codicePersona) {
		List<NotaSpese> notaSpese = notaSpeseService.readNotaSpese(codicePersona);
		List<NotaSpeseDto> dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<List<NotaSpeseDto>> genericDto = GenericWrapperResponse.<List<NotaSpeseDto>>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per aggiornare una nota spese
	@PostMapping(value = "/update-nota-spese")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> updateNotaSpese(@RequestBody NotaSpeseDto notaSpeseDto) {
		NotaSpese notaSpese = notaSpeseService.updateNotaSpese(notaSpeseDto);
		NotaSpeseDto dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}

	// Metodo per rimuovere una nota spese
	@GetMapping(value = "/delete-nota-spese")
	public ResponseEntity<GenericWrapperResponse<NotaSpeseDto>> deleteNotaSpese(@RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		NotaSpese notaSpese = notaSpeseService.deleteNotaSpese(codicePersona, codiceCommessa);
		NotaSpeseDto dtoNotaSpese = DtoEntityMapper.INSTANCE.fromEntityToDtoNotaSpese(notaSpese);
		GenericWrapperResponse<NotaSpeseDto> genericDto = GenericWrapperResponse.<NotaSpeseDto>builder()
				.dataRichiesta(new Date()).risultato(dtoNotaSpese).build();
		return ResponseEntity.ok(genericDto);
	}
}
