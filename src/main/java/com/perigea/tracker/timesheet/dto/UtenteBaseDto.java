package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.StatoUtenteType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UtenteBaseDto extends BaseDto {

	private static final long serialVersionUID = -2636700849377999776L;
	
	private String codicePersona;
	private String nome;
	private String cognome;
	private StatoUtenteType statoUtenteType;

}
