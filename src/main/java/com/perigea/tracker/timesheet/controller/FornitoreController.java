package com.perigea.tracker.timesheet.controller;

import java.util.List;

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

import com.perigea.tracker.commons.dto.FornitoreDto;
import com.perigea.tracker.commons.dto.GenericWrapperResponse;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Fornitore;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.FornitoreService;

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
				.<FornitoreDto>builder().timestamp(Utils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all")
	public ResponseEntity<GenericWrapperResponse<List<FornitoreDto>>> readAll() {
		List<Fornitore> fornitori = fornitoreService.readAll();
		List<FornitoreDto> clientiDto = dtoEntityMapper.entityToDtoFornitoreList(fornitori);
		GenericWrapperResponse<List<FornitoreDto>> genericResponse = GenericWrapperResponse
				.<List<FornitoreDto>>builder().timestamp(Utils.now()).risultato(clientiDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-partita-iva/{partitaIva}")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> readFornitoreByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Fornitore fornitore = fornitoreService.readFornitoreByPartitaIva(partitaIva);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().timestamp(Utils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-id/{id}")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> readFornitoreById(@PathVariable(name = "id") String id) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().timestamp(Utils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> updateFornitore(@RequestBody FornitoreDto anaFornitoreDto) {
		Fornitore fornitore = dtoEntityMapper.dtoToEntity(anaFornitoreDto);
		fornitore = fornitoreService.saveFornitore(fornitore);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().timestamp(Utils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-by-partita-iva/{partitaIva}")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> deleteFornitoreByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Fornitore fornitore = fornitoreService.readFornitoreByPartitaIva(partitaIva);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		fornitoreService.deleteFornitoreById(fornitore.getCodiceAzienda());
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().timestamp(Utils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-id/{id}")
	public ResponseEntity<GenericWrapperResponse<FornitoreDto>> deleteFornitoreById(@PathVariable(name = "id") String id) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		fornitoreService.deleteFornitoreById(id);
		GenericWrapperResponse<FornitoreDto> genericResponse = GenericWrapperResponse
				.<FornitoreDto>builder().timestamp(Utils.now()).risultato(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/add-contatto")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> createContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		Utente contatto = fornitoreService.findContatto(contattoDto.getCodicePersona());
		fornitoreService.addContatto(fornitore, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse
				.<UtenteDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/remove-contatto")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> removeContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		Utente contatto = fornitoreService.findContatto(contattoDto.getCodicePersona());
		fornitoreService.removeContatto(fornitore, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse
				.<UtenteDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@DeleteMapping(value = "/{id}/contatti/delete-contatto")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> deleteContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		Utente contatto = fornitoreService.findContatto(contattoDto.getCodicePersona());
		fornitoreService.deleteContatto(fornitore, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse
				.<UtenteDto>builder().timestamp(Utils.now()).risultato(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
