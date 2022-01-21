package com.perigea.tracker.timesheet.dto.wrapper;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.TimesheetEntryDto;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;

import lombok.Data;

@Data
public class TimesheetWrapper implements Serializable {
	
	private static final long serialVersionUID = -8392790428900594810L;
	
	private List<TimesheetEntryDto> entries = new ArrayList<>();
	private TimesheetInputDto timesheet;
//	private List<NotaSpeseDto> expenseReport = new ArrayList<>();
}