package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.StatoUtenteType;

import lombok.Data;

@Data
public class UtenteViewDto {
	private String codicePersona;
	private String nome;
	private String cognome;
	private StatoUtenteType statoUtenteType;
	private UtenteViewDto responsabileDto;
}
