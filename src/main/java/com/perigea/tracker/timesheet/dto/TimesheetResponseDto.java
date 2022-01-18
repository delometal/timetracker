package com.perigea.tracker.timesheet.dto;

import java.util.List;

import com.perigea.tracker.timesheet.enums.StatoType;

import lombok.Data;

@Data
public class TimesheetResponseDto {
	
	private String codicePersona;
	private Integer anno;
	private Integer mese;
	private Integer oreTotali;
	private StatoType statoType;
	private List<TimesheetDataDto> dataList;
}