package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.TipoCostoNotaSpeseType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotaSpeseDto {

	private Integer annoDiRiferimento;
	private Integer meseDiRiferimento;
	private String codicePersona;
	private String codiceCommessa;
	private Integer giornoDiRiferimento;
	private TipoCostoNotaSpeseType tipoCostoNotaSpeseType;
	private Double importo;
	private String createUser;
	private String lastUpdateUser;
	
	public NotaSpeseDto() {
		super();
	}
	
}
