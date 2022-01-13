package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.StatoUtenteType;

import lombok.Data;

@Data
public class UtentePostDto {

	private String codicePersona;
	private String nome;
	private String cognome;
	private String password;
	private StatoUtenteType statoUtenteType;
	private String createUser;
	private String lastUpdateUser;

}
