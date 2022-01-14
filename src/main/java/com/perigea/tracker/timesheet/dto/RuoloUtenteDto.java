package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.RuoloType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RuoloUtenteDto extends BaseDto {

	private static final long serialVersionUID = 4550799214446522793L;
	
	private String codicePersona;
	private RuoloType tipo;

}
