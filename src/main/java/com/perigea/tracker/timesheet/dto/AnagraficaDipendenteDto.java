package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnagraficaDipendenteDto {
	
	private String codicePersona;
	private String luogoDiNascita;
	private Date dataDiNascita;
	private String mailAziendale;
	private String mailPrivata;
	private String cellulare;
	private String provinciaDiDomicilio;
	private String comuneDiDomicilio;
	private String indirizzoDiDomicilio;
	private String provinciaDiResidenza;
	private String comuneDiResidenza;
	private String indirizzoDiResidenza;
	private String nomeContattoEmergenza;
	private String cellulareContattoEmergenza;
	private Date dataAssunzione;
	private String iban;
	private Date dataCessazione;
	private String codiceFiscale;
	private String createUser;
	private String lastUpdateUser;
	//private String codiceResponsabile;
	
	public AnagraficaDipendenteDto() {
		super();
	}

}
