package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetWrapper;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.entity.keys.TimesheetMensileKey;
import com.perigea.tracker.timesheet.enums.EMese;
import com.perigea.tracker.timesheet.enums.StatoRichiestaType;
import com.perigea.tracker.timesheet.service.TimesheetService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService;

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> createTimesheet(@RequestBody TimesheetWrapper wrapper) {
		Timesheet timesheet = timesheetService.createTimesheet(wrapper.getEntries(), wrapper.getTimesheet()/*, wrapper.getExpenseReport()*/);
		TimesheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	// Metodo per creare un timesheet
	@GetMapping(value = "/read-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> readTimesheet() {
		Timesheet timesheet = timesheetService.getTimesheet(2020, EMese.GEN, "01");
		TimesheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timesheet);
		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}
	
	//Metodo per eliminare un timesheet
	@DeleteMapping(value = "/delete-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> deleteTimesheet(@RequestParam Integer anno,
			EMese mese,@RequestParam String codicePersona) {
		Timesheet timesheet = timesheetService.getTimesheet(anno, mese, codicePersona);
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
//	public ResponseEntity<GenericWrapperResponse<Boolean>> updateTimesheetStatus(@RequestBody TimesheetInputDto timesheetDto, @RequestParam StatoRichiestaType newStatus) {	
		
		TimesheetMensileKey tsKey = new TimesheetMensileKey(timesheetDto.getAnno(), timesheetDto.getMese(), timesheetDto.getCodicePersona());
		Boolean update = timesheetService.editTimesheetStatus(tsKey, newStatus);
		GenericWrapperResponse<Boolean> genericDto = GenericWrapperResponse.<Boolean>builder()
				.dataRichiesta(new Date()).risultato(update).build();
		if(update) {
			return ResponseEntity.ok(genericDto);
		}
		return ResponseEntity.badRequest().body(genericDto);
	}
}