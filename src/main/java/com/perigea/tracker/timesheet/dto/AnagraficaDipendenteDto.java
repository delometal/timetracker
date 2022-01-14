package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AnagraficaDipendenteDto extends BaseDto {

	private static final long serialVersionUID = -1157857116466220981L;
	
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

}
