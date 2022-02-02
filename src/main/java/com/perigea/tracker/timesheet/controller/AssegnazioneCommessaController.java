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

import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.dto.DipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.service.AssegnazioneCommessaService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/assegnazione-commesse")
public class AssegnazioneCommessaController {
	
	@Autowired
	private AssegnazioneCommessaService assegnazioneCommessaService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> createDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		DipendenteCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = assegnazioneCommessaService.createDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> readDipendenteCommessa(@RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		DipendenteCommessa dipendenteCommessa = assegnazioneCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> updateDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		DipendenteCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = assegnazioneCommessaService.updateDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete")
	ResponseEntity<GenericWrapperResponse<DipendenteCommessaDto>> deleteDipendenteCommessa(@RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		DipendenteCommessa dipendenteCommessa = assegnazioneCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		assegnazioneCommessaService.deleteDipendenteCommessa(dipendenteCommessa.getId());
		GenericWrapperResponse<DipendenteCommessaDto> genericResponse = GenericWrapperResponse
				.<DipendenteCommessaDto>builder().dataRichiesta(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
