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

import com.perigea.tracker.commons.dto.ConsulenteDto;
import com.perigea.tracker.commons.dto.DatiEconomiciConsulenteDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.RuoloDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.timesheet.entity.CentroDiCosto;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciConsulente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.CentroDiCostoService;
import com.perigea.tracker.timesheet.service.ConsulenteService;

@RestController
@RequestMapping("/consulenti")
public class ConsulenteController {
	
	@Autowired
	protected Logger logger;
	
	@Autowired
	private ConsulenteService consulenteService;
	
	@Autowired
	private CentroDiCostoService centroDiCostoService;
	
	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<ConsulenteDto>> createConsulente(@RequestBody ConsulenteDto consulenteDto) {
		Utente utente = dtoEntityMapper.dtoToEntity(consulenteDto.getUtente());
		Consulente consulente = dtoEntityMapper.dtoToEntity(consulenteDto);
		DatiEconomiciConsulente economics = dtoEntityMapper.dtoToEntity(consulenteDto.getEconomics());
		loadResponsabile(consulenteDto, consulente);
		utente = consulenteService.createUtenteConsulente(utente, consulente, economics);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente) utente.getPersonale();
		consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteDto);
		ResponseDto<ConsulenteDto> genericResponse = ResponseDto.<ConsulenteDto>builder().data(consulenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	private void loadResponsabile(ConsulenteDto consulenteDto, Consulente consulente) {
		try {
			Utente responsabile= consulenteService.readUtente(consulenteDto.getCodiceResponsabile());			
			consulente.setResponsabile(responsabile.getPersonale());
		} catch(Exception e) {
			consulente.setResponsabile(null);
			logger.warn("Responsabile non presente");
		}
	}

	@GetMapping(value = "/read/{codicePersona}")
	public ResponseEntity<ResponseDto<ConsulenteDto>> readConsulente(@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = consulenteService.readUtente(codicePersona);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getPersonale();
		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteDto);
		ResponseDto<ConsulenteDto> genericResponse = ResponseDto.<ConsulenteDto>builder().data(consulenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete/{codicePersona}")
	public ResponseEntity<ResponseDto<ConsulenteDto>> deleteConsulente(@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = consulenteService.readUtente(codicePersona);
		Consulente consulente = (Consulente) utente.getPersonale();
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(consulente);
		consulenteDto.setUtente(utenteDto);
		consulenteService.deleteUtente(codicePersona);
		ResponseDto<ConsulenteDto> genericResponse = ResponseDto.<ConsulenteDto>builder().data(consulenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<ConsulenteDto>> updateUser(@RequestBody ConsulenteDto consulenteDto) {
		Consulente consulente = dtoEntityMapper.dtoToEntity(consulenteDto);
		Utente utente = consulenteService.readUtente(consulenteDto.getUtente().getCodicePersona());
		loadResponsabile(consulenteDto, consulente);
		consulente.setCodicePersona(utente.getCodicePersona());
		utente.setPersonale(consulente);
		utente = consulenteService.updateUtente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getPersonale();
		ConsulenteDto anagraficaResponseDto = dtoEntityMapper.entityToDto(anagrafica);
		anagraficaResponseDto.setUtente(utenteResponseDto);
		ResponseDto<ConsulenteDto> genericResponse = ResponseDto.<ConsulenteDto>builder().data(anagraficaResponseDto).build();
		return ResponseEntity.ok(genericResponse);
	}	
	
	@PutMapping(value = "/update-status/{codicePersona}/{status}")
	public ResponseEntity<ResponseDto<UtenteDto>> editStatusUser(@PathVariable("codicePersona") String codicePersona, @PathVariable("status") StatoUtenteType status) {
		Utente utente = consulenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		ResponseDto<UtenteDto> genericResponse = ResponseDto.<UtenteDto>builder().data(utenteResponseDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-roles/{codicePersona}")
	public ResponseEntity<ResponseDto<UtenteDto>> editRoleUser(@PathVariable(name = "codicePersona") String codicePersona, @RequestBody List<RuoloDto> ruoliDto) {
		Utente utente = consulenteService.readUtente(codicePersona);
		List<Ruolo> ruoli = dtoEntityMapper.dtoToEntityRuoloList(ruoliDto);
		utente.setRuoli(ruoli);
		utente = consulenteService.updateUtente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		ResponseDto<UtenteDto> genericResponse = ResponseDto.<UtenteDto>builder().data(utenteResponseDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/create-economics")
	public ResponseEntity<ResponseDto<DatiEconomiciConsulenteDto>> createDatiEconomiciConsulente(@RequestBody DatiEconomiciConsulenteDto datiEconomiciConsulenteDto) {
		Utente utente = consulenteService.readUtente(datiEconomiciConsulenteDto.getCodicePersona());
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciConsulenteDto.getCodiceCentroDiCosto());
		DatiEconomiciConsulente economics = dtoEntityMapper.dtoToEntity(datiEconomiciConsulenteDto);
		Consulente consulente = (Consulente) utente.getPersonale();
		consulente.setEconomics(economics);
		economics.setPersonale(consulente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(consulente);
		utente = consulenteService.updateUtente(utente);
		datiEconomiciConsulenteDto = dtoEntityMapper.entityToDto(economics);
		ResponseDto<DatiEconomiciConsulenteDto> genericResponse = ResponseDto.<DatiEconomiciConsulenteDto>builder().data(datiEconomiciConsulenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	
	
	@PutMapping(value = "/update-economics")
	public ResponseEntity<ResponseDto<DatiEconomiciConsulenteDto>> editDatiEconomiciConsulente(@RequestBody DatiEconomiciConsulenteDto datiEconomiciConsulenteDto) {
		Utente utente = consulenteService.readUtente(datiEconomiciConsulenteDto.getCodicePersona());
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciConsulenteDto.getCodiceCentroDiCosto());
		DatiEconomiciConsulente economics = dtoEntityMapper.dtoToEntity(datiEconomiciConsulenteDto);
		
		Consulente consulente = (Consulente) utente.getPersonale();
		consulente.setEconomics(economics);
		economics.setPersonale(consulente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(consulente);
		
		utente = consulenteService.updateUtente(utente);
		datiEconomiciConsulenteDto = dtoEntityMapper.entityToDto(economics);

		ResponseDto<DatiEconomiciConsulenteDto> genericResponse = ResponseDto.<DatiEconomiciConsulenteDto>builder().data(datiEconomiciConsulenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
}