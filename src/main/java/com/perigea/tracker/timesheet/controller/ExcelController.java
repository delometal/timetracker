package com.perigea.tracker.timesheet.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetExcelWrapper;
import com.perigea.tracker.timesheet.enums.EMese;
import com.perigea.tracker.timesheet.service.ExcelTimesheetService;

@RestController
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired 
	private ExcelTimesheetService excelService;

	@PostMapping(value = "/create-excel-timesheet")
	public void createExcelTimesheet(@RequestBody TimesheetExcelWrapper timesheetExcelWrapper) {
		excelService.createExcelTimesheet(timesheetExcelWrapper);
	}
	
	@PostMapping(value = "/download-excel-timesheet/{mese}")
	public void downloadExcelTimesheet(@RequestParam Integer anno,@PathVariable(value = "mese") EMese mese,@RequestBody UtenteViewDto utente) {
		excelService.downloadExcelTimesheet(anno, mese, utente);
	}
	
}



