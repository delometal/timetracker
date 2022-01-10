package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.RuoloType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteRuoloDto {
	private String codicePersona;
	private RuoloType ruoloType; 
	
	public UtenteRuoloDto() {
		super();
	}
}
