package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetDataWrapper;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.enums.EMese;
import com.perigea.tracker.timesheet.service.TimesheetService;
import com.perigea.tracker.timesheet.utility.DtoEntityMapper;



@RestController
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService;

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> createTimesheet(@RequestBody TimesheetDataWrapper wrapper) {
		Timesheet timesheet = timesheetService.createTimesheet(wrapper.getEntries(), wrapper.getTimesheet(), wrapper.getExpenseReport());
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

}