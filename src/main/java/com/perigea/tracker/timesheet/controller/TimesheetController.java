package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.TimesheetDataDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetDataWrapper;
import com.perigea.tracker.timesheet.service.TimesheetService;



@RestController
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private TimesheetService timeSheetService;

	// Metodo per creare un timesheet
//	@PostMapping(value = "/create-timesheet")
//	public ResponseEntity<GenericWrapperResponse<TimesheetResponseDto>> createTimeSheet(@RequestBody TimesheetDataWrapper wrapper) {
//		TimesheetResponseDto dto = timeSheetService.createTimeSheet(wrapper.getList(),wrapper.getDto());
//		GenericWrapperResponse<TimesheetResponseDto> genericDto = GenericWrapperResponse.<TimesheetResponseDto>builder()
//				.dataRichiesta(new Date()).risultato(dto).build();
//		return ResponseEntity.ok(genericDto);
//	}

}