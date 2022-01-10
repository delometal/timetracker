package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import com.perigea.tracker.timesheet.enumerator.StatoType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSheetDto {
	
	private Integer annoDiRiferimento;
	private Integer meseDiRiferimento;
	private String codicePersona;
	private String codiceCommessa;
	private Integer giornoDiRiferimento;
	private Integer ore;
	private Boolean trasferta;
	private StatoType statoType;
	private String createUser;
	private String lastUpdateUser;
	
	public TimeSheetDto() {
		super();
	}
}
