package com.perigea.tracker.timesheet.dto.wrapper;

import com.perigea.tracker.timesheet.dto.AnagraficaDto;
import com.perigea.tracker.timesheet.dto.InfoAutoDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;

import lombok.Data;

@Data
public class TimesheetExcelWrapper {

	private TimesheetResponseDto timesheet;
	private AnagraficaDto angrafica;
	private InfoAutoDto infoAuto;
	
	public TimesheetExcelWrapper(TimesheetResponseDto timesheet, AnagraficaDto angrafica, InfoAutoDto infoAuto) {
		this.timesheet = timesheet;
		this.angrafica = angrafica;
		this.infoAuto = infoAuto;
	}
}
