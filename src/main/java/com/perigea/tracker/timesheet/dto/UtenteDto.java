package com.perigea.tracker.timesheet.dto;


import java.util.Date;

import com.perigea.tracker.timesheet.enumerator.StatoUtenteType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteDto {
	
	private String codicePersona;
	private String nome;
	private String cognome;
	private String password;
	private StatoUtenteType statoUtenteType;
	private String createUser;
	private String lastUpdateUser;
	
	public UtenteDto() {
		
	}
}
