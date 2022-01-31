package com.perigea.tracker.timesheet.dto.wrapper;

import com.perigea.tracker.timesheet.dto.TimesheetEntryDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;

import lombok.Data;

@Data
public class TimesheetExcelWrapper {

	private UtenteViewDto utente;
	private TimesheetResponseDto timesheet;
	
	public TimesheetExcelWrapper( TimesheetResponseDto timesheet, UtenteViewDto utente) {
		this.utente = utente;
		this.timesheet = timesheet;
	}
}
