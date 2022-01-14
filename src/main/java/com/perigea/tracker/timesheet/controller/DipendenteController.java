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

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.service.DipendenteService;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;

	@PostMapping(value = "/create-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> createDipendente(@RequestBody AnagraficaDipendenteInputDto anagraficaDipendenteDto) {
		AnagraficaDipendenteResponseDto dto = dipendenteService.createDipendente(anagraficaDipendenteDto);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	// Metodo per leggere un utente
	@GetMapping(value = "/read-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> readDipendente(@RequestParam String codicePersona) {
		AnagraficaDipendenteResponseDto dto = dipendenteService.readDipendente(codicePersona);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	// Metodo per cancellare un utente
	@GetMapping(value = "/delete-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> deleteDipendente(@RequestParam String codicePersona) {
		AnagraficaDipendenteResponseDto dto = dipendenteService.deleteDipendente(codicePersona);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	// Metodo per aggiornare un utente
	@PostMapping(value = "/update-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> updateUser(@RequestBody AnagraficaDipendenteInputDto dtoParam) {
		AnagraficaDipendenteResponseDto dto = dipendenteService.updateDipendente(dtoParam);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

}
