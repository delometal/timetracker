package com.perigea.tracker.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.perigea.tracker.commons.dto.TimesheetRefDto;
import com.perigea.tracker.commons.dto.TimesheetResponseDto;
import com.perigea.tracker.commons.dto.UtenteDto;
import com.perigea.tracker.commons.dto.wrapper.TimesheetWrapper;
import com.perigea.tracker.commons.enums.ApprovalStatus;
import com.perigea.tracker.commons.enums.EMese;
import com.perigea.tracker.commons.exception.TimesheetException;
import com.perigea.tracker.commons.utils.Utils;
import com.perigea.tracker.timesheet.entity.Consulente;
import com.perigea.tracker.timesheet.entity.DatiEconomiciDipendente;
import com.perigea.tracker.timesheet.entity.Dipendente;
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
	
	@GetMapping(value = "/read")
	public ResponseEntity<ResponseDto<TimesheetResponseDto>> readTimesheet(@RequestBody TimesheetMensileKey id) {
		Timesheet timesheet = timesheetService.getTimesheet(id);
		TimesheetResponseDto dto = dtoEntityMapper.entityToDto(timesheet);
		ResponseDto<TimesheetResponseDto> genericDto = ResponseDto.<TimesheetResponseDto>builder().data(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<ResponseDto<TimesheetResponseDto>> deleteTimesheet(@RequestBody TimesheetMensileKey id) {
		EMese mese = EMese.getByMonthId(id.getMese());
		Timesheet timesheet = timesheetService.deleteTimesheet(id.getAnno(), mese, id.getCodicePersona());
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
	public ResponseEntity<ResponseDto<Boolean>> updateTimesheetStatus(@RequestBody TimesheetRefDto timesheetDto, @PathVariable(value = "status") ApprovalStatus newStatus) {
		TimesheetMensileKey tsKey = new TimesheetMensileKey(timesheetDto.getAnno(), timesheetDto.getMese(), timesheetDto.getCodicePersona());
		Boolean update = timesheetService.editTimesheetStatus(tsKey, newStatus);
		ResponseDto<Boolean> genericDto = ResponseDto.<Boolean>builder().data(update).build();
		if(update) {
			return ResponseEntity.ok(genericDto);
		}
		return ResponseEntity.badRequest().body(genericDto);
	}
	
	@GetMapping(value = "/download-report/{anno}/{mese}/{codicePersona}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadExcelTimesheet(@PathVariable(value = "anno") Integer anno, @PathVariable(value = "mese") EMese mese, @PathVariable(value = "codicePersona") String codicePersona) {
		Utente utente = dipendenteService.readUtente(codicePersona);
		InfoAutoDto infoAuto = null;
		if(utente.getPersonale().getClass().isAssignableFrom(Dipendente.class)) {
			Dipendente dipendente = (Dipendente) utente.getPersonale();
			DatiEconomiciDipendente economics = dipendente.getEconomics();
			infoAuto = new InfoAutoDto(economics.getModelloAuto(), economics.getRimborsoPerKm(), economics.getKmPerGiorno());
		} else if(utente.getPersonale().getClass().isAssignableFrom(Consulente.class)) {
			infoAuto = new InfoAutoDto("", 0.0f, 0.0f);
		} else {
			throw new TimesheetException("Tipo utente non valido");
		}

		String fileName = Utils.removeAllSpaces(anno + mese.getMonthPart() + "_" + utente.getCognome() + Utils.EXCEL_EXT).trim();
		UtenteDto utenteDto = dtoEntityMapper.entityToDto(utente);
		byte[] excel = timesheetService.downloadExcelTimesheet(anno, mese, utenteDto, infoAuto);
		return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName + "\"")
	            .body(excel);
	}
}