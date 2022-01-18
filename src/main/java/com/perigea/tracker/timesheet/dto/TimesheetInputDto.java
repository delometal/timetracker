package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enums.StatoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimesheetInputDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4872236196662689797L;

	private String codicePersona;
	private Integer annoDiRiferimento;
	private Integer meseDiRiferimento;
	private Integer oreMensiliTotali;
	private StatoType statoType;
	private String createUser;
	private String lastUpdateUser;
}
