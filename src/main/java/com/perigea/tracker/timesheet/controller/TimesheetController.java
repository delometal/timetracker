package com.perigea.tracker.timesheet.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.perigea.tracker.commons.dto.InfoAutoDto;
import com.perigea.tracker.commons.dto.ResponseDto;
import com.perigea.tracker.commons.dto.TimesheetEntryDto;
import com.perigea.tracker.commons.dto.TimesheetEventDto;
import com.perigea.tracker.commons.dto.TimesheetResponseDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.dto.wrapper.TimesheetWrapper;
import com.perigea.tracker.commons.enums.ApprovalStatus;
import com.perigea.tracker.commons.enums.EMese;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Personale;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.TimesheetEntry;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.mapper.DtoEntityMapper;
import com.perigea.tracker.timesheet.service.DipendenteService;
import com.perigea.tracker.timesheet.service.TimesheetService;

@RestController
@RequestMapping("/timesheet")
@CrossOrigin(allowedHeaders = "*", origins = "*", originPatterns = "*", exposedHeaders = "*")
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService;

	@Autowired
	private DtoEntityMapper dtoEntityMapper;

	@Autowired
	private DipendenteService dipendenteService;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDto<TimesheetResponseDto>> createTimesheet(@RequestBody TimesheetWrapper wrapper) {
		Timesheet timesheet = timesheetService.createTimesheet(wrapper.getEntries(), wrapper.getTimesheet());
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheet);
		ResponseDto<TimesheetResponseDto> genericDto = ResponseDto.<TimesheetResponseDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read/{anno}/{mese}/{codicePersona}")
	public ResponseEntity<ResponseDto<TimesheetResponseDto>> readTimesheet(@PathVariable Integer anno,
			@PathVariable Integer mese, @PathVariable String codicePersona) {
		Timesheet timesheet = timesheetService.getTimesheet(anno, mese, codicePersona);
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheet);
		ResponseDto<TimesheetResponseDto> genericDto = ResponseDto.<TimesheetResponseDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);

	}
	
	@GetMapping(value = "/read-all")
	public ResponseEntity<ResponseDto<List<TimesheetResponseDto>>> readAllTimesheet() {
		List<Timesheet> timesheets = timesheetService.getAllTimesheet();
		List<TimesheetResponseDto> dto = dtoEntityMapper.entityToDto(timesheets);
		ResponseDto<List<TimesheetResponseDto>> genericDto = ResponseDto.<List<TimesheetResponseDto>>builder().data(dto)
				.build();
		return ResponseEntity.ok(genericDto);
	}

	@GetMapping(value = "/read-all-by-anno-codicePersona/{anno}/{codicePersona}")
	public ResponseEntity<ResponseDto<List<TimesheetResponseDto>>> readAllAnnualTimesheet(@PathVariable Integer anno,
			@PathVariable String codicePersona) {
		List<Timesheet> timesheetList = timesheetService.findAllByAnnoAndCodicePersona(anno, codicePersona);
		List<TimesheetResponseDto> refDtoList = dtoEntityMapper.entityToDto(timesheetList);
		ResponseDto<List<TimesheetResponseDto>> genericDto = ResponseDto.<List<TimesheetResponseDto>>builder()
				.data(refDtoList).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<ResponseDto<TimesheetResponseDto>> deleteTimesheet(@RequestBody TimesheetMensileKey id) {
		Timesheet timesheet = timesheetService.deleteTimesheet(id.getAnno(), id.getMese(), id.getCodicePersona());
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheet);
		ResponseDto<TimesheetResponseDto> genericDto = ResponseDto.<TimesheetResponseDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@DeleteMapping(value = "/delete-entry")
	public ResponseEntity<ResponseDto<TimesheetEntryDto>> deleteTimesheetEntry(@RequestBody TimesheetEntryKey id) {
		TimesheetEntry entry = timesheetService.getTimesheetEntry(id);
		timesheetService.deleteTimesheetEntry(entry);
		TimesheetEntryDto dto = dtoEntityMapper.entityToDto(entry);
		ResponseDto<TimesheetEntryDto> genericDto = ResponseDto.<TimesheetEntryDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<ResponseDto<TimesheetResponseDto>> updateTimesheet(@RequestBody TimesheetWrapper wrapper) {
		Timesheet timesheetEntry = timesheetService.updateTimesheet(wrapper.getEntries(), wrapper.getTimesheet());
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheetEntry);
		ResponseDto<TimesheetResponseDto> genericDto = ResponseDto.<TimesheetResponseDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-status/{status}")
	public ResponseEntity<ResponseDto<Boolean>> updateTimesheetStatus(@RequestBody TimesheetEventDto timesheetEvent,
			@PathVariable(value = "status") ApprovalStatus newStatus) {
		Boolean update = timesheetService.editTimesheetStatus(timesheetEvent, newStatus);
		ResponseDto<Boolean> genericDto = ResponseDto.<Boolean>builder().data(update).build();
		if (update) {
			return ResponseEntity.ok(genericDto);
		}
		return ResponseEntity.badRequest().body(genericDto);
	}

	@PutMapping(value = "/update-multiple-status/{status}")
	public ResponseEntity<ResponseDto<Boolean>> updateMultiTimesheetStatus(
			@RequestBody List<TimesheetEventDto> timesheetDtos,
			@PathVariable(value = "status") ApprovalStatus newStatus) {

		Boolean update = timesheetService.approveMultiTimesheet(timesheetDtos, newStatus);
		ResponseDto<Boolean> genericDto = ResponseDto.<Boolean>builder().data(update).build();
		if (update) {
			return ResponseEntity.ok(genericDto);
		}
		return ResponseEntity.badRequest().body(genericDto);
	}

	@GetMapping(value = "/download-report/{anno}/{mese}/{codicePersona}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadExcelTimesheet(@PathVariable(value = "anno") Integer anno,
			@PathVariable(value = "mese") Integer mese, @PathVariable(value = "codicePersona") String codicePersona) {
		Utente utente = dipendenteService.readUtente(codicePersona);
		InfoAutoDto infoAuto = timesheetService.getInfoAuto(utente);
		String fileName = Utils.removeAllSpaces(anno + "/" + mese + "_" + utente.getCognome() + ".xlsx").trim();
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		byte[] excel = timesheetService.downloadExcelTimesheet(anno, mese, utenteDto, infoAuto);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(excel);
	}

	@GetMapping(value = "/download-zip-reports/{anno}/{mese}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadZipTimesheets(@PathVariable(value = "anno") Integer anno,
			@PathVariable(value = "mese") Integer mese) {
		String month = EMese.getByMonthId(mese).name();
		String fileName = Utils.removeAllSpaces(anno + month + "timesheets" + Utils.ZIP_EXT).trim();

		Map<String, byte[]> excelsMap = timesheetService.getExcelTimesheetsMap(anno, mese);
		byte[] zip = Utils.zipMultipleFiles(excelsMap);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(zip);
	}
	
	
	

	@GetMapping(value = "/download-zip-reports-by-responsabile/{anno}/{mese}/{codiceResponsabile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadZipTimesheets(@PathVariable(value = "anno") Integer anno,
			@PathVariable(value = "mese") Integer mese,
			@PathVariable(value = "codiceResponsabile") String codiceResponsabile) {
		String month = EMese.getByMonthId(mese).name();
		Personale responsabile = dipendenteService.readAnagraficaDipendente(codiceResponsabile);
		List<Personale> sottoposti = dipendenteService.readAllDipendentiByResponsabile(responsabile);
		String fileName = Utils.removeAllSpaces(anno + "_" + month + "_" + responsabile.getUtente().getUsername()
				+ "_sottoposti_timesheets" + Utils.ZIP_EXT).trim();

		Map<String, byte[]> excelsMap = timesheetService.getExcelTimesheetsMap(anno, mese, sottoposti);
		byte[] zip = Utils.zipMultipleFiles(excelsMap);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(zip);
	}

	@GetMapping(value = "/read-ore-totali-lavorate-per-commessa-mensili/{anno}/{mese}/{codicePersona}/{codiceCommessa}")
	public ResponseEntity<ResponseDto<Integer>> getOreTotaliCommessaMese(@PathVariable(value = "anno") Integer anno,
			@PathVariable(value = "mese") Integer mese, @PathVariable(value = "codicePersona") String codicePersona,
			@PathVariable(value = "codiceCommessa") String codiceCommessa) {
		Integer oreTotali = timesheetService.getOreTotaliPerCommessa(codiceCommessa, anno, mese, codicePersona);
		ResponseDto<Integer> genericResponse = ResponseDto.<Integer>builder().data(oreTotali).build();
		return ResponseEntity.ok(genericResponse);

	}

	@GetMapping(value = "/read-ore-totali-lavorate-per-commessa-annuali/{anno}/{codicePersona}/{codiceCommessa}")
	public ResponseEntity<ResponseDto<Integer>> getOreTotaliCommessaMese(@PathVariable(value = "anno") Integer anno,
			@PathVariable(value = "codicePersona") String codicePersona,
			@PathVariable(value = "codiceCommessa") String codiceCommessa) {
		Integer oreTotali = timesheetService.getOreTotaliPerCommessa(codiceCommessa, anno, codicePersona);
		ResponseDto<Integer> genericResponse = ResponseDto.<Integer>builder().data(oreTotali).build();
		return ResponseEntity.ok(genericResponse);

	}

}