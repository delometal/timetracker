package com.perigea.tracker.timesheet.controller;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

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

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.enums.StatoUtenteType;
import com.perigea.tracker.timesheet.service.DipendenteService;


@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;

	@PostMapping(value = "/create-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> createDipendente(@RequestBody AnagraficaDipendenteInputDto anagraficaDipendenteDto, @RequestParam String codiceResponsabile) {
		AnagraficaDipendenteResponseDto dto = dipendenteService.createDipendente(anagraficaDipendenteDto, codiceResponsabile);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

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

	@DeleteMapping(value = "/delete-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> deleteDipendente(@RequestParam String codicePersona) {
		AnagraficaDipendenteResponseDto dto = dipendenteService.deleteDipendente(codicePersona);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-dipendente")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDipendenteResponseDto>> updateUser(@RequestBody AnagraficaDipendenteInputDto dtoParam) {
		AnagraficaDipendenteResponseDto dto = dipendenteService.updateDipendente(dtoParam);
		GenericWrapperResponse<AnagraficaDipendenteResponseDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDipendenteResponseDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-user-status/{codicePersona}/{status}")
	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> editStatusUser(@PathParam("codicePersona") String codicePersona, @PathParam("status") StatoUtenteType status) {
		UtenteViewDto dto = dipendenteService.updateUserStatus(codicePersona, status);
		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/edit-role-user")
	public ResponseEntity<GenericWrapperResponse<UtenteViewDto>> editRoleUser(@RequestParam String codicePersona, @RequestBody List<RuoloDto> ruolo) {
		UtenteViewDto dto = dipendenteService.editUserRolesDto(ruolo, codicePersona);
		GenericWrapperResponse<UtenteViewDto> genericResponse = GenericWrapperResponse.<UtenteViewDto>builder()
				.dataRichiesta(new Date())
				.risultato(dto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

}