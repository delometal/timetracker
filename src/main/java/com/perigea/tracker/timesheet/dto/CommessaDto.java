package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enums.CommessaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommessaDto extends BaseDto {

	private static final long serialVersionUID = -2208822176373154627L;
	
	private String codiceCommessa;
	private CommessaType commessaType;
	private String descrizioneCommessa;
	private String createUser;
	private String lastUpdateUser;
	
}
