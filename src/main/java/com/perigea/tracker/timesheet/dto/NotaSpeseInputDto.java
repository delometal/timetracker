package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enums.CostoNotaSpeseType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotaSpeseInputDto extends BaseDto {
	
	private static final long serialVersionUID = 7264225957922019578L;

	private Integer anno;
	private Integer mese;
	private String codicePersona;
	private String codiceCommessa;
	private Integer giorno;
	private CostoNotaSpeseType costoNotaSpese;
	private Double importo;
	private String createUser;
	private String lastUpdateUser;
	
}
