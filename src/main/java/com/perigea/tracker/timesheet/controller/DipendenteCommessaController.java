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

import com.perigea.tracker.timesheet.dto.DipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.service.DipendenteCommessaService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/dipendenti-commesse")
public class DipendenteCommessaController {
	
	@Autowired
	private DipendenteCommessaService dipendenteCommessaService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@PostMapping(value = "/create-dipendente-commessa")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> createDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		DipendenteCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = dipendenteCommessaService.createDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-dipendente-commessa")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> readDipendenteCommessa(@RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		DipendenteCommessa dipendenteCommessa = dipendenteCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-dipendente-commessa")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> updateDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		DipendenteCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = dipendenteCommessaService.updateDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-dipendente-commessa")
	ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> deleteDipendenteCommessa(@RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		DipendenteCommessa dipendenteCommessa = dipendenteCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		dipendenteCommessaService.deleteDipendenteCommessa(dipendenteCommessa.getId());
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
