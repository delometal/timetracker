package com.perigea.tracker.timesheet.controller;

import java.util.Date;

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

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetWrapper;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.enums.StatoRichiestaType;
import com.perigea.tracker.timesheet.service.DipendenteService;
import com.perigea.tracker.timesheet.service.ExcelTimesheetService;
import com.perigea.tracker.timesheet.service.TimesheetService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;
import com.perigea.tracker.timesheet.utility.TSUtils;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService;
	
	@Autowired 
	private ExcelTimesheetService excelService;
	
	@Autowired
	private DipendenteService dipendenteService;

	@PostMapping(value = "/create-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> createTimesheet(@RequestBody TimesheetWrapper wrapper) {
		Timesheet timesheet = timesheetService.createTimesheet(wrapper.getEntries(), wrapper.getTimesheet());
		TimesheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@GetMapping(value = "/read-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> readTimesheet(@RequestBody TimesheetMensileKey id) {
		Timesheet timesheet = timesheetService.getTimesheet(id);
		TimesheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@DeleteMapping(value = "/delete-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> deleteTimesheet(@RequestBody TimesheetMensileKey id) {
		Timesheet timesheet = timesheetService.getTimesheet(id);
		TimesheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}

	@PutMapping(value = "/update-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> updateTimesheet(@RequestBody TimesheetWrapper wrapper) {
		Timesheet timesheetEntry = timesheetService.updateTimesheet(wrapper.getEntries(), wrapper.getTimesheet());
		TimesheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timesheetEntry);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	@PutMapping(value = "/update-timesheet-status/{status}")
	public ResponseEntity<GenericWrapperResponse<Boolean>> updateTimesheetStatus(@RequestBody TimesheetInputDto timesheetDto, @PathVariable(value = "status") StatoRichiestaType newStatus) {
		TimesheetMensileKey tsKey = new TimesheetMensileKey(timesheetDto.getAnno(), timesheetDto.getMese(), timesheetDto.getCodicePersona());
		Boolean update = timesheetService.editTimesheetStatus(tsKey, newStatus);
		GenericWrapperResponse<Boolean> genericDto = GenericWrapperResponse.<Boolean>builder()
				.dataRichiesta(new Date()).risultato(update).build();
		if(update) {
			return ResponseEntity.ok(genericDto);
		}
		return ResponseEntity.badRequest().body(genericDto);
	}
	
	@GetMapping(
			  value = "/download-excel-timesheet/{mese}",
			  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
			)
	public ResponseEntity<byte[]> downloadExcelTimesheet(@RequestParam Integer anno,@PathVariable(value = "mese") EMese mese,@RequestParam String codicePersona) {
		Utente utente = dipendenteService.readDipendente(codicePersona);
		String fileName = anno + mese.getMonthPart() + utente.getCognome() + TSUtils.EXCEL_EXT;
		UtenteViewDto utenteDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
		byte[] excel = excelService.downloadExcelTimesheet(anno, mese, utenteDto);
		return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName + "\"")
	            .body(excel);
	}
}