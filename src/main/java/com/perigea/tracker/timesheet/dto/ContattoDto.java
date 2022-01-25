package com.perigea.tracker.timesheet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContattoDto extends BaseDto {

	private static final long serialVersionUID = -1300906776655166252L;

	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String mailAziendale;
	private String mailPrivata;
	private String cellulare;
	private String provinciaDiDomicilio;
	private String comuneDiDomicilio;
	private String indirizzoDiDomicilio;
	
}
