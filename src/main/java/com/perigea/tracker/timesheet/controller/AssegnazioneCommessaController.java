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

import com.perigea.tracker.commons.dto.DipendenteCommessaDto;
import com.perigea.tracker.commons.dto.GenericWrapperResponse;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.PersonaleCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.AssegnazioneCommessaService;

@RestController
@RequestMapping("/assegnazione-commesse")
public class AssegnazioneCommessaController {
	
	@Autowired
	private AssegnazioneCommessaService assegnazioneCommessaService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> createDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		PersonaleCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = assegnazioneCommessaService.createDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read/commessa/{codiceCommessa}/dipendente/{codicePersona}")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> readDipendenteCommessa(@PathVariable(name = "codicePersona") String codicePersona, @PathVariable(name = "codiceCommessa") String codiceCommessa) {
		PersonaleCommessa dipendenteCommessa = assegnazioneCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> updateDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		PersonaleCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = assegnazioneCommessaService.updateDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete/commessa/{codiceCommessa}/dipendente/{codicePersona}")
	ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> deleteDipendenteCommessa(@PathVariable(name = "codicePersona") String codicePersona, @PathVariable(name = "codiceCommessa") String codiceCommessa) {
		PersonaleCommessa dipendenteCommessa = assegnazioneCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		assegnazioneCommessaService.deleteDipendenteCommessa(dipendenteCommessa.getId());
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
