package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.commons.enums.CostoNotaSpeseType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotaSpeseDto extends BaseDto {

	private static final long serialVersionUID = -2095879361695786844L;
	
	private CostoNotaSpeseType costoNotaSpese;
	private Double importo;
	private String createUser;
	private String lastUpdateUser;
	
}
