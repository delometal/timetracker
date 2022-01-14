package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.TimeSheetDto;
import com.perigea.tracker.timesheet.service.TimesheetService;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private TimesheetService timeSheetService;

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimeSheetDto>> createTimeSheet(@RequestBody TimeSheetDto timeDto, @RequestParam String codicePersona, @RequestParam String codiceCommessa) {
		TimeSheetDto dto = timeSheetService.createTimeSheet(codicePersona, codiceCommessa, timeDto);
		GenericWrapperResponse<TimeSheetDto> genericDto = GenericWrapperResponse.<TimeSheetDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}

}