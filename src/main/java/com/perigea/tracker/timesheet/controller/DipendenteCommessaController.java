package com.perigea.tracker.timesheet.controller;

import java.util.Date;

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

import com.perigea.tracker.timesheet.dto.DipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.service.DipendenteCommessaService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/relazione-dipendente-commessa")
public class DipendenteCommessaController {
	
	@Autowired
	private DipendenteCommessaService dipendenteCommessaService;
	
	@PostMapping(value = "/create-dipendente-commessa")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> createDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		DipendenteCommessa dipendenteCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityRelazioneDipendenteCommessa(dipendenteCommessaDto);
		dipendenteCommessa = dipendenteCommessaService.createDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoDipendenteCommessa(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-dipendente-commessa")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> readDipendenteCommessa(@RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		DipendenteCommessa dipendenteCommessa = dipendenteCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoDipendenteCommessa(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-dipendente-commessa")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> updateDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		DipendenteCommessa dipendenteCommessa = DtoEntityMapper.INSTANCE.fromDtoToEntityRelazioneDipendenteCommessa(dipendenteCommessaDto);
		dipendenteCommessa = dipendenteCommessaService.updateDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoDipendenteCommessa(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-dipendente-commessa")
	ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> deleteDipendenteCommessa(@RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		DipendenteCommessa dipendenteCommessa = dipendenteCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoDipendenteCommessa(dipendenteCommessa);
		dipendenteCommessaService.deleteDipendenteCommessa(dipendenteCommessa.getId());
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
