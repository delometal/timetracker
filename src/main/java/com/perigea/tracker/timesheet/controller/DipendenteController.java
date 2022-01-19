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
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.service.DipendenteService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;



@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;

	@PostMapping(value = "/create-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> createDipendente(@RequestBody AnagraficaDipendenteInputDto anagraficaDipendenteDto) {
//		AnagraficaDipendenteResponseDto dto = dipendenteService.createDipendente(anagraficaDipendenteDto);
		AnagraficaDipendente anagraficaDipendente = dipendenteService.createDipendente(anagraficaDipendenteDto);
		AnagraficaDipendenteResponseDto anagraficaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagraficaDipendente);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder().dataRichiesta(new Date()).risultato(anagraficaDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	// Metodo per leggere un utente
	@GetMapping(value = "/read-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> readDipendente(@RequestParam String codicePersona) {
		AnagraficaDipendente anagraficaDipendente = dipendenteService.readDipendente(codicePersona);
		UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagraficaDipendente.getUtente());
		AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagraficaDipendente);
		anagraficaResponseDto.setUtenteDto(utenteResponseDto);
//		AnagraficaDipendenteResponseDto anagraficaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagraficaDipendente);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder().dataRichiesta(new Date()).risultato(anagraficaResponseDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	// Metodo per cancellare un utente
	@GetMapping(value = "/delete-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> deleteDipendente(@RequestParam String codicePersona) {
//		AnagraficaDipendenteResponseDto dto = dipendenteService.deleteDipendente(codicePersona);
		AnagraficaDipendente anagraficaDipendente = dipendenteService.deleteDipendente(codicePersona);
		AnagraficaDipendenteResponseDto anagraficaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagraficaDipendente);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder().dataRichiesta(new Date()).risultato(anagraficaDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	// Metodo per aggiornare un utente
	@PostMapping(value = "/update-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> updateUser(@RequestBody AnagraficaDipendenteInputDto dtoParam) {
//		AnagraficaDipendenteResponseDto dto = dipendenteService.updateDipendente(dtoParam);
		AnagraficaDipendente anagraficaDipendente = dipendenteService.updateDipendente(dtoParam);
		AnagraficaDipendenteResponseDto anagraficaDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagraficaDipendente);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder().dataRichiesta(new Date()).risultato(anagraficaDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	// Metodo per aggiornare lo status di un utente
	@PostMapping(value = "/edit-status-user")
	public ResponseEntity<GenericWrapperResponse<UtentePostDto>> editStatusUser(@RequestBody UtentePostDto utenteDto) {
//		UtenteViewDto dto = dipendenteService.editStatusUser(utenteDto);
		Utente utente = dipendenteService.editStatusUser(utenteDto);
		UtentePostDto dtoUtente = DtoEntityMapper.INSTANCE.fromEntityToDtoUtente(utente);
		GenericWrapperResponse<UtentePostDto> genericResponse = GenericWrapperResponse.<UtentePostDto>builder()
				.dataRichiesta(new Date()).risultato(dtoUtente).build();
		return ResponseEntity.ok(genericResponse);
	}

}
