package com.perigea.tracker.timesheet.dto.wrapper;

import com.perigea.tracker.timesheet.dto.DipendenteDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.UtenteDto;

import lombok.Data;

@Data
public class TimesheetExcelWrapper {

	private TimesheetResponseDto timesheet;
	private UtenteDto utente;
	private DipendenteDto dipendente;

}
