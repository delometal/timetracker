package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enums.StatoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TimesheetDataDto extends BaseDto {
	
	private static final long serialVersionUID = 8273187903186747431L;

	private Integer annoDiRiferimento;
	private Integer meseDiRiferimento;
	private String codicePersona;
	private String codiceCommessa;
	private Integer giornoDiRiferimento;
	private Integer ore;
	private Boolean trasferta;
	private String createUser;
	private String lastUpdateUser;
	
}