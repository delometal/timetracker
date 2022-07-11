package com.perigea.tracker.timesheet.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.perigea.tracker.commons.enums.AnagraficaType;
import com.perigea.tracker.commons.enums.StatoUtenteType;
import com.perigea.tracker.timesheet.entity.CentroDiCosto;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.CentroDiCostoService;
import com.perigea.tracker.timesheet.service.ConversioneService;
import com.perigea.tracker.timesheet.service.DipendenteService;

@RestController
@RequestMapping("/dipendenti")
@CrossOrigin(allowedHeaders = "*", origins = "*",originPatterns = "*"  )
public class DipendenteController {

	@Autowired
	protected Logger logger;

	@Autowired
	private DipendenteService dipendenteService;

	@Autowired
	private CentroDiCostoService centroDiCostoService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;
	
	@Autowired
	private ConversioneService conversioneService;
	
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<DipendenteDto>> createDipendente(@RequestBody DipendenteDto dipendenteDto) {
		Utente utente = dtoEntityMapper.dtoToEntity(dipendenteDto.getUtente());
		Dipendente dipendente = dtoEntityMapper.dtoToEntity(dipendenteDto);
		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(dipendenteDto.getEconomics());
		loadResponsabile(dipendenteDto, dipendente);
		utente = dipendenteService.createUtenteDipendente(utente, dipendente, economics);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente) utente.getPersonale();
		dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto.<DipendenteDto>builder().data(dipendenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	private void loadResponsabile(DipendenteDto dipendenteDto, Dipendente dipendente) {
		try {
			Utente responsabile = dipendenteService.readUtente(dipendenteDto.getCodiceResponsabile());
			dipendente.setResponsabile(responsabile.getPersonale());
		} catch (Exception e) {
			dipendente.setResponsabile(null);
			logger.warn("Responsabile non presente");
		}
	}

	@GetMapping(value = "/read/{codicePersona}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR', 'ROLE_DIPENDENTE')")
	public ResponseEntity<ResponseDto<DipendenteDto>> readDipendente(
			@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = dipendenteService.readUtente(codicePersona);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente) utente.getPersonale();
		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(anagrafica);
		dipendenteDto.setUtente(utenteDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto.<DipendenteDto>builder().data(dipendenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/read-all")
	public ResponseEntity<ResponseDto<List<DipendenteDto>>> readAllDipendenti() {
		List<Dipendente> dipendenti = dipendenteService.getAllDipendenti();
		List<DipendenteDto> dtos = dtoEntityMapper.entityToDipendenteDtoList(dipendenti);
		ResponseDto<List<DipendenteDto>> genericResponse = ResponseDto.<List<DipendenteDto>>builder().data(dtos).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	
	@DeleteMapping(value = "/delete/{codicePersona}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<DipendenteDto>> deleteDipendente(
			@PathVariable(name = "codicePersona") String codicePersona) {
		Utente utente = dipendenteService.readUtente(codicePersona);
		Dipendente dipendente = (Dipendente) utente.getPersonale();
		dipendente.setEconomics(null);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		DipendenteDto dipendenteDto = dtoEntityMapper.entityToDto(dipendente);
		dipendenteDto.setUtente(utenteDto);
		dipendenteService.deleteUtente(codicePersona);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto.<DipendenteDto>builder().data(dipendenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR', 'ROLE_DIPENDENTE')")
	public ResponseEntity<ResponseDto<DipendenteDto>> updateUser(@RequestBody DipendenteDto dipendenteDto) {
		Dipendente dipendente = dtoEntityMapper.dtoToEntity(dipendenteDto);
		Utente utente = dipendente.getUtente();
		loadResponsabile(dipendenteDto, dipendente);
		dipendente.setCodicePersona(utente.getCodicePersona());
		utente.setPersonale(dipendente);
		utente = dipendenteService.updateUtente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		Dipendente anagrafica = (Dipendente) utente.getPersonale();
		DipendenteDto anagraficaResponseDto = dtoEntityMapper.entityToDto(anagrafica);
		anagraficaResponseDto.setUtente(utenteResponseDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto.<DipendenteDto>builder().data(anagraficaResponseDto)
				.build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-status/{codicePersona}/{status}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<UtenteDto>> editStatusUser(@PathVariable("codicePersona") String codicePersona,
			@PathVariable("status") StatoUtenteType status) {
		Utente utente = dipendenteService.updateUtenteStatus(codicePersona, status);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		ResponseDto<UtenteDto> genericResponse = ResponseDto.<UtenteDto>builder().data(utenteResponseDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-roles/{codicePersona}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<UtenteDto>> editRoleUser(
			@PathVariable(name = "codicePersona") String codicePersona, @RequestBody List<RuoloDto> ruoliDto) {
		Utente utente = dipendenteService.readUtente(codicePersona);
		List<Ruolo> ruoli = dtoEntityMapper.dtoToEntityRuoloList(ruoliDto);
		utente.setRuoli(ruoli);
		utente = dipendenteService.updateUtente(utente);
		UtenteDto utenteResponseDto = dtoEntityMapper.entityToDto(utente);
		ResponseDto<UtenteDto> genericResponse = ResponseDto.<UtenteDto>builder().data(utenteResponseDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PostMapping(value = "/create-economics")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<DatiEconomiciDipendenteDto>> createDatiEconomiciDipendente(
			@RequestBody DatiEconomiciDipendenteDto datiEconomiciDipendenteDto) {
		Utente utente = dipendenteService.readUtente(datiEconomiciDipendenteDto.getCodicePersona());
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciDipendenteDto.getCodiceCentroDiCosto());
		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(datiEconomiciDipendenteDto);
		Dipendente dipendente = (Dipendente) utente.getPersonale();
		dipendente.setEconomics(economics);
		economics.setPersonale(dipendente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(dipendente);
		dipendenteService.updateUtente(utente);
		datiEconomiciDipendenteDto = dtoEntityMapper.entityToDto(economics);

		ResponseDto<DatiEconomiciDipendenteDto> genericResponse = ResponseDto.<DatiEconomiciDipendenteDto>builder()
				.data(datiEconomiciDipendenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	@PutMapping(value = "/update-economics")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<DatiEconomiciDipendenteDto>> editDatiEconomiciDipendente(
			@RequestBody DatiEconomiciDipendenteDto datiEconomiciDipendenteDto) {
		Utente utente = dipendenteService.readUtente(datiEconomiciDipendenteDto.getCodicePersona());
		CentroDiCosto cdc = centroDiCostoService.readCentroDiCosto(datiEconomiciDipendenteDto.getCodiceCentroDiCosto());
		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(datiEconomiciDipendenteDto);
		dipendenteService.createStorico(economics);
		economics.setCodicePersona(utente.getCodicePersona());
		Dipendente dipendente = (Dipendente) utente.getPersonale();
		dipendente.setEconomics(economics);
		economics.setPersonale(dipendente);
		economics.setCentroDiCosto(cdc);
		utente.setPersonale(dipendente);
		
		
		utente = dipendenteService.updateUtente(utente);
		datiEconomiciDipendenteDto = dtoEntityMapper.entityToDto(economics);
		ResponseDto<DatiEconomiciDipendenteDto> genericResponse = ResponseDto.<DatiEconomiciDipendenteDto>builder()
				.data(datiEconomiciDipendenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping(value = "/checkSpecification/{ral}/{centroDiCosto}")
	public ResponseEntity<ResponseDto<List<DipendenteDto>>> readCommessaFatturabile(
			@PathVariable Float ral,
			@PathVariable String centroDiCosto) {
		List<Dipendente> dipendentiEntity = dipendenteService.searchDipendentiByRal(ral, centroDiCosto);
		List<DipendenteDto> dipendentiDto = dtoEntityMapper.entityToDipendenteDtoList(dipendentiEntity);
		ResponseDto<List<DipendenteDto>> genericDto = ResponseDto.<List<DipendenteDto>>builder().data(dipendentiDto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@GetMapping(value = "/read-dipendenti-attivi-totali/{status}")
	public ResponseEntity<ResponseDto<Integer>> getAllDipendentiAttivi(@PathVariable StatoUtenteType status) {
		Integer totaleDipendenti = dipendenteService.getAllDipendentiByActivityStatus(status);
		ResponseDto<Integer> genericResponse = ResponseDto.<Integer>builder().data(totaleDipendenti).build();
		return ResponseEntity.ok(genericResponse);

	}
	
	
	@PutMapping(value = "/from-consulente-to-dipendente/{dataCessazione}/{codiceConsulente}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGEMENT', 'ROLE_ADMIN', 'ROLE_AMMINISTRAZIONE', 'ROLE_HR')")
	public ResponseEntity<ResponseDto<DipendenteDto>> fromConsulenteToDipendente(
			@RequestBody DipendenteDto dipendenteDto, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataCessazione,
			@PathVariable String codiceConsulente) {
		Consulente consulente = conversioneService.readAnagraficaConsulente(codiceConsulente);
		consulente.getUtente().getRuoli().stream().forEach(a ->{});
		Dipendente dipendente = dtoEntityMapper.dtoToEntity(dipendenteDto);
		DatiEconomiciDipendente economics = dtoEntityMapper.dtoToEntity(dipendenteDto.getEconomics());
		loadResponsabile(dipendenteDto, dipendente);
		Utente newUtenteEntity = changeUtente(consulente, dipendente);
		dipendente = conversioneService.conversioneConsulenteToDipendente(consulente, newUtenteEntity, dipendente, economics, dataCessazione) ;
		dipendenteDto = dtoEntityMapper.entityToDto(dipendente);
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(dipendente.getUtente());
		dipendenteDto.setUtente(utenteDto);
		ResponseDto<DipendenteDto> genericResponse = ResponseDto.<DipendenteDto>builder().data(dipendenteDto).build();
		return ResponseEntity.ok(genericResponse);
	}

	private Utente changeUtente(Consulente consulente, Dipendente dipendente) {
		UtenteDto dtoNewUtente = dtoEntityMapper.entityToDto(consulente.getUtente());
		dtoNewUtente.setCodicePersona(null);
		dtoNewUtente.setStato(StatoUtenteType.A);
		dtoNewUtente.setTipo(AnagraficaType.I);
		dtoNewUtente.setMailAziendale(dipendente.getUtente().getMailAziendale());
		dtoNewUtente.setCodiceAzienda(dipendente.getUtente().getCodiceAzienda());
		Utente newUtenteEntity = dtoEntityMapper.dtoToEntity(dtoNewUtente);
		return newUtenteEntity;
	}

}