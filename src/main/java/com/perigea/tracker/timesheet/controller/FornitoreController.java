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
import com.perigea.tracker.commons.dto.ResponseDto;
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
	public ResponseEntity<ResponseDto<FornitoreDto>> saveFornitore(@RequestBody FornitoreDto fornitoreDto) {
		Fornitore fornitore = dtoEntityMapper.dtoToEntity(fornitoreDto);
		fornitore = fornitoreService.saveFornitore(fornitore);
		fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto
				.<FornitoreDto>builder().timestamp(Utils.now()).data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all")
	public ResponseEntity<ResponseDto<List<FornitoreDto>>> readAll() {
		List<Fornitore> fornitori = fornitoreService.readAll();
		List<FornitoreDto> clientiDto = dtoEntityMapper.entityToDtoFornitoreList(fornitori);
		ResponseDto<List<FornitoreDto>> genericResponse = ResponseDto
				.<List<FornitoreDto>>builder().timestamp(Utils.now()).data(clientiDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-partita-iva/{partitaIva}")
	public ResponseEntity<ResponseDto<FornitoreDto>> readFornitoreByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Fornitore fornitore = fornitoreService.readFornitoreByPartitaIva(partitaIva);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto
				.<FornitoreDto>builder().timestamp(Utils.now()).data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read-by-id/{id}")
	public ResponseEntity<ResponseDto<FornitoreDto>> readFornitoreById(@PathVariable(name = "id") String id) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto
				.<FornitoreDto>builder().timestamp(Utils.now()).data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<FornitoreDto>> updateFornitore(@RequestBody FornitoreDto anaFornitoreDto) {
		Fornitore fornitore = dtoEntityMapper.dtoToEntity(anaFornitoreDto);
		fornitore = fornitoreService.saveFornitore(fornitore);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto
				.<FornitoreDto>builder().timestamp(Utils.now()).data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-by-partita-iva/{partitaIva}")
	public ResponseEntity<ResponseDto<FornitoreDto>> deleteFornitoreByPartitaIva(@PathVariable(name = "partitaIva") String partitaIva) {
		Fornitore fornitore = fornitoreService.readFornitoreByPartitaIva(partitaIva);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		fornitoreService.deleteFornitoreById(fornitore.getCodiceAzienda());
		ResponseDto<FornitoreDto> genericResponse = ResponseDto
				.<FornitoreDto>builder().timestamp(Utils.now()).data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete-id/{id}")
	public ResponseEntity<ResponseDto<FornitoreDto>> deleteFornitoreById(@PathVariable(name = "id") String id) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		FornitoreDto fornitoreDto = dtoEntityMapper.entityToDto(fornitore);
		fornitoreService.deleteFornitoreById(id);
		ResponseDto<FornitoreDto> genericResponse = ResponseDto
				.<FornitoreDto>builder().timestamp(Utils.now()).data(fornitoreDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/add-contatto")
	public ResponseEntity<ResponseDto<UtenteDto>> createContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		Utente contatto = fornitoreService.findContatto(contattoDto.getCodicePersona());
		fornitoreService.addContatto(fornitore, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/{id}/contatti/remove-contatto")
	public ResponseEntity<ResponseDto<UtenteDto>> removeContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		Utente contatto = fornitoreService.findContatto(contattoDto.getCodicePersona());
		fornitoreService.removeContatto(fornitore, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@DeleteMapping(value = "/{id}/contatti/delete-contatto")
	public ResponseEntity<ResponseDto<UtenteDto>> deleteContatto(@PathVariable(name = "id") String id, @RequestBody UtenteDto contattoDto) {
		Fornitore fornitore = fornitoreService.readFornitoreById(id);
		Utente contatto = fornitoreService.findContatto(contattoDto.getCodicePersona());
		fornitoreService.deleteContatto(fornitore, contatto);
		UtenteDto dto = dtoEntityMapper.entityToDto(contatto);
		ResponseDto<UtenteDto> genericResponse = ResponseDto
				.<UtenteDto>builder().timestamp(Utils.now()).data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
