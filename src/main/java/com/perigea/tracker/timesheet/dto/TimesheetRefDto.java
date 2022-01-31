package com.perigea.tracker.timesheet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TimesheetRefDto extends BaseDto {

	private static final long serialVersionUID = 4872236196662689797L;

	private String codicePersona;
	private Integer anno;
	private Integer mese;
	
}
