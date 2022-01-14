package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enums.CostoNotaSpeseType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotaSpeseDto extends BaseDto {

	private static final long serialVersionUID = -2095879361695786844L;
	
	private Integer annoDiRiferimento;
	private Integer meseDiRiferimento;
	private String codicePersona;
	private String codiceCommessa;
	private Integer giornoDiRiferimento;
	private CostoNotaSpeseType tipoCostoNotaSpeseType;
	private Double importo;
	private String createUser;
	private String lastUpdateUser;
	
}
