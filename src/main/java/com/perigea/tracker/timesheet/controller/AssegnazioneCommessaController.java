package com.perigea.tracker.timesheet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.commons.dto.DipendenteCommessaDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.timesheet.entity.PersonaleCommessa;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.AssegnazioneCommessaService;
import com.perigea.tracker.timesheet.service.UtenteService;

@RestController
@RequestMapping("/assegnazione-commesse")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AssegnazioneCommessaController {
	
	@Autowired
	private AssegnazioneCommessaService assegnazioneCommessaService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@Autowired
	private UtenteService utenteService;
	
	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<DipendenteCommessaDto>> createDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		PersonaleCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = assegnazioneCommessaService.createDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		ResponseDto<DipendenteCommessaDto> genericResponse = ResponseDto.<DipendenteCommessaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read/commessa/{codiceCommessa}/dipendente/{codicePersona}")
	public ResponseEntity<ResponseDto<DipendenteCommessaDto>> readDipendenteCommessa(@PathVariable(name = "codicePersona") String codicePersona, @PathVariable(name = "codiceCommessa") String codiceCommessa) {
		PersonaleCommessa dipendenteCommessa = assegnazioneCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		ResponseDto<DipendenteCommessaDto> genericResponse = ResponseDto.<DipendenteCommessaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-utenti-assegnati-by-commessa/{codiceCommessa}")
	public ResponseEntity<ResponseDto<List<UtenteDto>>>	readAllUtentiAssegnatiByCommessa(@PathVariable String codiceCommessa) {		
		List<UtenteDto> utentiDto = new ArrayList<>();
		List<PersonaleCommessa> dipendentiCommessa = assegnazioneCommessaService
				.readAllByCodiceCommessa(codiceCommessa);
		for (PersonaleCommessa pc : dipendentiCommessa) {
			Utente user = utenteService.readUtente(pc.getId().getCodicePersona());
			UtenteDto utenteDto = dtoEntityMapper.entityToDto(user);
			utentiDto.add(utenteDto);
		}
		ResponseDto<List<UtenteDto>> genericResponse = ResponseDto.<List<UtenteDto>>builder().data(utentiDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all-assegnazioni-by-commessa/{codiceCommessa}")
	public ResponseEntity<ResponseDto<List<DipendenteCommessaDto>>> readAllUtentiByCommessa(@PathVariable(name = "codiceCommessa") String codiceCommessa) {
		List<PersonaleCommessa> dipendentiCommessa = assegnazioneCommessaService.readAllByCodiceCommessa(codiceCommessa);
		List<DipendenteCommessaDto> dto = dtoEntityMapper.entityToDtoListDipendenteCommessa(dipendentiCommessa);
		ResponseDto<List<DipendenteCommessaDto>> genericResponse = ResponseDto.<List<DipendenteCommessaDto>>builder()
				.data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<DipendenteCommessaDto>> updateDipendenteCommessa(@RequestBody DipendenteCommessaDto dipendenteCommessaDto) {
		PersonaleCommessa dipendenteCommessa = dtoEntityMapper.dtoToEntity(dipendenteCommessaDto);
		dipendenteCommessa = assegnazioneCommessaService.updateDipendenteCommessa(dipendenteCommessa);
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		ResponseDto<DipendenteCommessaDto> genericResponse = ResponseDto.<DipendenteCommessaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete/commessa/{codiceCommessa}/dipendente/{codicePersona}")
	ResponseEntity<ResponseDto<DipendenteCommessaDto>> deleteDipendenteCommessa(@PathVariable(name = "codicePersona") String codicePersona, @PathVariable(name = "codiceCommessa") String codiceCommessa) {
		PersonaleCommessa dipendenteCommessa = assegnazioneCommessaService.readDipendenteCommessa(new DipendenteCommessaKey(codicePersona, codiceCommessa));
		DipendenteCommessaDto dto = dtoEntityMapper.entityToDto(dipendenteCommessa);
		assegnazioneCommessaService.deleteDipendenteCommessa(dipendenteCommessa.getId());
		ResponseDto<DipendenteCommessaDto> genericResponse = ResponseDto.<DipendenteCommessaDto>builder().data(dto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}
