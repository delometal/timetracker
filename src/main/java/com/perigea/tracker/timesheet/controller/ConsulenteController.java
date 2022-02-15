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
import com.perigea.tracker.commons.dto.GenericWrapperResponse;
import com.perigea.tracker.commons.dto.RuoloDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.commons.utils.Utils;
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
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> createConsulente(@RequestBody ConsulenteDto consulenteDto) {
		Utente responsabile = consulenteService.readUtenteConsulente(consulenteDto.getUtente().getCodiceResponsabile());
		Utente utente = dtoEntityMapper.dtoToEntity(consulenteDto.getUtente());
		Consulente consulente = dtoEntityMapper.dtoToEntity(consulenteDto);
		consulente.setResponsabile(responsabile.getPersonale());
		utente = consulenteService.createUtenteConsulente(utente, consulente);

		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente) utente.getPersonale();
		consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.timestamp(Utils.now())
				.risultato(consulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@GetMapping(value = "/read/{codicePersona}")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> readConsulente(@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = consulenteService.readUtenteConsulente(codicePersona);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getPersonale();
		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.timestamp(Utils.now())
				.risultato(consulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@DeleteMapping(value = "/delete/{codicePersona}")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> deleteConsulente(@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = consulenteService.readUtenteConsulente(codicePersona);
		Consulente consulente = (Consulente) utente.getPersonale();
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(consulente);
		consulenteDto.setUtente(utenteDto);

		consulenteService.deleteUtenteConsulente(codicePersona);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.timestamp(Utils.now())
				.risultato(consulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> updateUser(@RequestBody ConsulenteDto consulenteDto) {
		Utente responsabile = consulenteService.readUtenteConsulente(consulenteDto.getUtente().getCodiceResponsabile());
		Utente utente = consulenteService.readUtenteConsulente(consulenteDto.getUtente().getCodicePersona());
		Consulente consulente = dtoEntityMapper.dtoToEntity(consulenteDto);
		consulente.setCodicePersona(utente.getCodicePersona());
		consulente.setResponsabile(responsabile.getPersonale());
		utente.setPersonale(consulente);
		utente = consulenteService.updateUtenteConsulente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getPersonale();

		ConsulenteDto anagraficaResponseDto = dtoEntityMapper.entityToDto(anagrafica);
		anagraficaResponseDto.setUtente(utenteResponseDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.timestamp(Utils.now())
				.risultato(anagraficaResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-user")
	public ResponseEntity<GenericWrapperResponse<ConsulenteDto>> updateUser(@RequestBody UtenteDto UtenteDto) {
		Utente utente = dtoEntityMapper.dtoToEntity(UtenteDto);
		Utente responsabile = consulenteService.readUtenteConsulente(UtenteDto.getCodiceResponsabile());
		
		Consulente consulente = consulenteService.readAnagraficaConsulente(UtenteDto.getCodicePersona());
		consulente.setResponsabile(responsabile.getPersonale());
		utente.setPersonale(consulente);
		utente = consulenteService.updateUtenteConsulente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Consulente anagrafica = (Consulente)utente.getPersonale();

		ConsulenteDto consulenteDto = dtoEntityMapper.entityToDto(anagrafica);
		consulenteDto.setUtente(utenteResponseDto);
		GenericWrapperResponse<ConsulenteDto> genericResponse = GenericWrapperResponse
				.<ConsulenteDto>builder()
				.timestamp(Utils.now())
				.risultato(consulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-status/{codicePersona}/{status}")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> editStatusUser(@PathVariable("codicePersona") String codicePersona, @PathVariable("status") StatoUtenteType status) {
		Utente utente = consulenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse.<UtenteDto>builder()
				.timestamp(Utils.now())
				.risultato(utenteResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PutMapping(value = "/update-roles/{codicePersona}")
	public ResponseEntity<GenericWrapperResponse<UtenteDto>> editRoleUser(@PathVariable(name = "codicePersona") String codicePersona, @RequestBody List<RuoloDto> ruoliDto) {
		Utente utente = consulenteService.readUtenteConsulente(codicePersona);
		List<Ruolo> ruoli = dtoEntityMapper.dtoToEntityRuoloList(ruoliDto);
		utente.setRuoli(ruoli);
		utente = consulenteService.updateUtenteConsulente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		GenericWrapperResponse<UtenteDto> genericResponse = GenericWrapperResponse.<UtenteDto>builder()
				.timestamp(Utils.now())
				.risultato(utenteResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping(value = "/create-economics/{codicePersona}")
	public ResponseEntity<GenericWrapperResponse<DatiEconomiciConsulenteDto>> createDatiEconomiciConsulente(@RequestBody DatiEconomiciConsulenteDto datiEconomiciConsulenteDto) {
		Utente utente = consulenteService.readUtenteConsulente(datiEconomiciConsulenteDto.getCodicePersona());
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciConsulenteDto.getCodiceCentroDiCosto());
		DatiEconomiciConsulente economics = dtoEntityMapper.dtoToEntity(datiEconomiciConsulenteDto);

		Consulente consulente = (Consulente) utente.getPersonale();
		consulente.setEconomics(economics);
		economics.setPersonale(consulente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(consulente);
		
		utente = consulenteService.updateUtenteConsulente(utente);
		datiEconomiciConsulenteDto = dtoEntityMapper.entityToDto(economics);

		GenericWrapperResponse<DatiEconomiciConsulenteDto> genericResponse = GenericWrapperResponse.<DatiEconomiciConsulenteDto>builder()
				.timestamp(Utils.now())
				.risultato(datiEconomiciConsulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	
	
	@PutMapping(value = "/update-economics/{codicePersona}")
	public ResponseEntity<GenericWrapperResponse<DatiEconomiciConsulenteDto>> editDatiEconomiciConsulente(@RequestBody DatiEconomiciConsulenteDto datiEconomiciConsulenteDto) {
		Utente utente = consulenteService.readUtenteConsulente(datiEconomiciConsulenteDto.getCodicePersona());
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciConsulenteDto.getCodiceCentroDiCosto());
		DatiEconomiciConsulente economics = dtoEntityMapper.dtoToEntity(datiEconomiciConsulenteDto);
		
		Consulente consulente = (Consulente) utente.getPersonale();
		consulente.setEconomics(economics);
		economics.setPersonale(consulente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(consulente);
		
		utente = consulenteService.updateUtenteConsulente(utente);
		datiEconomiciConsulenteDto = dtoEntityMapper.entityToDto(economics);

		GenericWrapperResponse<DatiEconomiciConsulenteDto> genericResponse = GenericWrapperResponse.<DatiEconomiciConsulenteDto>builder()
				.timestamp(Utils.now())
				.risultato(datiEconomiciConsulenteDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}
	
}