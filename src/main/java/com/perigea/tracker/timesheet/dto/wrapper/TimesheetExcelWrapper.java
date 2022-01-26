package com.perigea.tracker.timesheet.dto.wrapper;

import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;

import lombok.Data;

@Data
public class TimesheetExcelWrapper {

	private TimesheetResponseDto timesheet;
	private UtenteViewDto utente;

}
