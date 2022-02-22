package com.perigea.tracker.timesheet.controller;
import java.util.List;

import org.slf4j.Logger;
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

import com.perigea.tracker.commons.dto.DatiEconomiciDipendenteDto;
import com.perigea.tracker.commons.dto.DipendenteDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.RuoloDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.exception.EntityNotFoundException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.CentroDiCosto;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.CentroDiCostoService;
import com.perigea.tracker.timesheet.service.DipendenteService;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

	@Autowired
	protected Logger logger;
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private CentroDiCostoService centroDiCostoService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<DipendenteDto>> createDipendente(@RequestBody DipendenteDto dipendenteDto) {
 		Utente utente = dtoEntityMapper.dtoToEntity(dipendenteDto.getUtente());
 		Dipendente dipendente = dtoEntityMapper.dtoToEntity(dipendenteDto);
 		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(dipendenteDto.getEconomics());
		Utente responsabile = null;
		try {
			responsabile = dipendenteService.readUtenteDipendente(dipendenteDto.getUtente().getCodiceResponsabile());
			dipendente.setResponsabile(responsabile.getPersonale());
		} catch(EntityNotFoundException e) {
			responsabile = null;
		}
		
		utente = dipendenteService.createUtenteDipendente(utente, dipendente, economics);

		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente) utente.getPersonale();
		dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.data(dipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read/{codicePersona}")
//	@PreAuthorize("hasAuthority('ROLE_MANAGEMENT')")
	public ResponseEntity<ResponseDto<DipendenteDto>> readDipendente(@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente)utente.getPersonale();
		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.data(dipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete/{codicePersona}")
	public ResponseEntity<ResponseDto<DipendenteDto>> deleteDipendente(@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		Dipendente dipendente = (Dipendente) utente.getPersonale();
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(dipendente);
		dipendenteDto.setUtente(utenteDto);

		dipendenteService.deleteUtenteDipendente(codicePersona);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.data(dipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<DipendenteDto>> updateUser(@RequestBody DipendenteDto dipendenteDto) {
		
		Dipendente dipendente = dtoEntityMapper.dtoToEntity(dipendenteDto);
		Utente utente = dipendenteService.readUtenteDipendente(dipendenteDto.getUtente().getCodicePersona());
		Utente responsabile = null;
		try {
			responsabile = dipendenteService.readUtenteDipendente(dipendenteDto.getUtente().getCodiceResponsabile());
			dipendente.setResponsabile(responsabile.getPersonale());
		} catch(EntityNotFoundException e) {
			responsabile = null;
		}
		
		dipendente.setCodicePersona(utente.getCodicePersona());
		utente.setPersonale(dipendente);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente)utente.getPersonale();

		DipendenteDto anagraficaResponseDto = dtoEntityMapper.entityToDto(anagrafica);
		anagraficaResponseDto.setUtente(utenteResponseDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.data(anagraficaResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-user")
	public ResponseEntity<ResponseDto<DipendenteDto>> updateUser(@RequestBody UtenteDto UtenteDto) {
		Utente utente = dtoEntityMapper.dtoToEntity(UtenteDto);
		Utente responsabile = dipendenteService.readUtenteDipendente(UtenteDto.getCodiceResponsabile());
		
		Dipendente dipendente = dipendenteService.readAnagraficaDipendente(UtenteDto.getCodicePersona());
		dipendente.setResponsabile(responsabile.getPersonale());
		utente.setPersonale(dipendente);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente)utente.getPersonale();

		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteResponseDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto
				.<DipendenteDto>builder()
				.timestamp(Utils.now())
				.data(dipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-status/{codicePersona}/{status}")
	public ResponseEntity<ResponseDto<UtenteDto>> editStatusUser(@PathVariable("codicePersona") String codicePersona, @PathVariable("status") StatoUtenteType status) {
		Utente utente = dipendenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		ResponseDto<UtenteDto> genericResponse = ResponseDto.<UtenteDto>builder()
				.timestamp(Utils.now())
				.data(utenteResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-roles/{codicePersona}")
	public ResponseEntity<ResponseDto<UtenteDto>> editRoleUser(@PathVariable(name = "codicePersona") String codicePersona, @RequestBody List<RuoloDto> ruoliDto) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		List<Ruolo> ruoli = dtoEntityMapper.dtoToEntityRuoloList(ruoliDto);
		utente.setRuoli(ruoli);
		utente = dipendenteService.updateUtenteDipendente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		ResponseDto<UtenteDto> genericResponse = ResponseDto.<UtenteDto>builder()
				.timestamp(Utils.now())
				.data(utenteResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	
	@PostMapping(value = "/create-economics/{codicePersona}")
	public ResponseEntity<ResponseDto<DatiEconomiciDipendenteDto>> createDatiEconomiciDipendente(@RequestBody DatiEconomiciDipendenteDto datiEconomiciDipendenteDto) {
		Utente utente = dipendenteService.readUtenteDipendente(datiEconomiciDipendenteDto.getCodicePersona());
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciDipendenteDto.getCodiceCentroDiCosto());
		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(datiEconomiciDipendenteDto);
		
		Dipendente dipendente = (Dipendente) utente.getPersonale();
		dipendente.setEconomics(economics);
		economics.setPersonale(dipendente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(dipendente);
		
		utente = dipendenteService.updateUtenteDipendente(utente);
		datiEconomiciDipendenteDto = dtoEntityMapper.entityToDto(economics);

		ResponseDto<DatiEconomiciDipendenteDto> genericResponse = ResponseDto.<DatiEconomiciDipendenteDto>builder()
				.timestamp(Utils.now())
				.data(datiEconomiciDipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-economics/{codicePersona}")
	public ResponseEntity<ResponseDto<DatiEconomiciDipendenteDto>> editDatiEconomiciDipendente(@PathVariable(name = "codicePersona") String codicePersona, @RequestBody DatiEconomiciDipendenteDto datiEconomiciDipendenteDto) {
		Utente utente = dipendenteService.readUtenteDipendente(codicePersona);
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciDipendenteDto.getCodiceCentroDiCosto());
		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(datiEconomiciDipendenteDto);
		economics.setCodicePersona(codicePersona);
		
		Dipendente dipendente = (Dipendente) utente.getPersonale();
		dipendente.setEconomics(economics);
		economics.setPersonale(dipendente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(dipendente);
		
		utente = dipendenteService.updateUtenteDipendente(utente);
		datiEconomiciDipendenteDto = dtoEntityMapper.entityToDto(economics);

		ResponseDto<DatiEconomiciDipendenteDto> genericResponse = ResponseDto.<DatiEconomiciDipendenteDto>builder()
				.timestamp(Utils.now())
				.data(datiEconomiciDipendenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
}