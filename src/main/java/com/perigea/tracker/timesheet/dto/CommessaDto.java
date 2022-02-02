package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.commons.enums.CommessaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CommessaDto extends BaseDto {

	private static final long serialVersionUID = -2208822176373154627L;
	
	private String codiceCommessa;
	private CommessaType tipoCommessa;
	private String descrizioneCommessa;
	
}
