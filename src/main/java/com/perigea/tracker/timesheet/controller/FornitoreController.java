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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.AnagraficaDto;
import com.perigea.tracker.timesheet.dto.FornitoreDto;
import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.entity.Anagrafica;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.service.FornitoreService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/fornitori")
public class FornitoreController {

	@Autowired
	private FornitoreService fornitoreService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> saveFornitore(@RequestBody FornitoreDto fornitoreDto) {
		Fornitore fornitore = dtoEntityMapper.dtoToEntity(fornitoreDto);
		fornitore = fornitoreService.saveFornitore(fornitore);
		fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().dataRichiesta(TSUtils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> readFornitore(@RequestParam String id) {
		Fornitore fornitore = fornitoreService.readFornitore(id);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().dataRichiesta(TSUtils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> updateFornitore(@RequestBody FornitoreDto anaFornitoreDto) {
		Fornitore fornitore = dtoEntityMapper.dtoToEntity(anaFornitoreDto);
		fornitore = fornitoreService.saveFornitore(fornitore);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().dataRichiesta(TSUtils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> deleteFornitore(@RequestParam String id) {
		Fornitore fornitore = fornitoreService.readFornitore(id);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		fornitoreService.deleteFornitore(id);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().dataRichiesta(TSUtils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/add-contatto")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> createContatto(@PathVariable(name = "id") String id, @RequestBody AnagraficaDto anagraficaDto) {
		Fornitore fornitore = fornitoreService.readFornitore(id);
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(anagraficaDto);
		fornitoreService.addContatto(fornitore, contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/remove-contatto")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> removeContatto(@PathVariable(name = "id") String id, @RequestBody AnagraficaDto anagraficaDto) {
		Fornitore fornitore = fornitoreService.readFornitore(id);
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(anagraficaDto);
		fornitoreService.removeContatto(fornitore, contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@DeleteMapping(value = "/{id}/contatti/delete-contatto")
	public ResponseEntity<GenericWrapperResponse<AnagraficaDto>> deleteContatto(@PathVariable(name = "id") String id, @RequestBody AnagraficaDto anagraficaDto) {
		Fornitore fornitore = fornitoreService.readFornitore(id);
		Anagrafica contatto = dtoEntityMapper.dtoToEntity(anagraficaDto);
		fornitoreService.deleteContatto(fornitore, contatto);
		AnagraficaDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<AnagraficaDto> genericResponse = GenericWrapperResponse
				.<AnagraficaDto>builder().dataRichiesta(TSUtils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
