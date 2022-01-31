package com.perigea.tracker.timesheet.dto.wrapper;

import com.perigea.tracker.timesheet.dto.AnagraficaDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;

import lombok.Data;

@Data
public class TimesheetExcelWrapper {

	private TimesheetResponseDto timesheet;
	private AnagraficaDto angrafica;
	
	public TimesheetExcelWrapper(TimesheetResponseDto timesheet, AnagraficaDto angrafica) {
		this.timesheet = timesheet;
		this.angrafica = angrafica;
	}
}
