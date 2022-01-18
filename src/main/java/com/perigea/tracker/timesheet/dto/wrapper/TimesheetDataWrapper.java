package com.perigea.tracker.timesheet.dto.wrapper;


import java.util.ArrayList;
import java.util.List;

import com.perigea.tracker.timesheet.dto.TimesheetDataDto;
import com.perigea.tracker.timesheet.dto.TimesheetInputDto;

import lombok.Data;

@Data
public class TimesheetDataWrapper {
	private List<TimesheetDataDto> list = new ArrayList<>(25);
	private TimesheetInputDto dto;
}