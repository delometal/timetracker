package com.perigea.tracker.timesheet.dto;

import java.util.List;

import com.perigea.tracker.timesheet.enums.StatoUtenteType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UtenteBaseDto extends BaseDto {

	private static final long serialVersionUID = -2636700849377999776L;
	
	private String codicePersona;
	private String nome;
	private String cognome;
	private StatoUtenteType statoUtente;
	private String codiceResponsabile;
	private List<RuoloDto> ruoli;
}
