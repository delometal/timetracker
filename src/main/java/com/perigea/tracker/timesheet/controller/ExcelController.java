package com.perigea.tracker.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.wrapper.TimesheetExcelWrapper;
import com.perigea.tracker.timesheet.service.ExcelService;

@RestController
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired 
	private ExcelService excelService;

	@PostMapping(value = "/create-excel")
	public void writeExcelFromDB(@RequestBody TimesheetExcelWrapper timesheetExcelWrapper) {
		excelService.statementTimesheet(timesheetExcelWrapper);
	}
}



