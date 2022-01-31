package com.perigea.tracker.timesheet.dto;

import java.time.LocalDate;

import com.perigea.tracker.timesheet.enums.AnagraficaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AnagraficaDto extends BaseDto {

	private static final long serialVersionUID = -1157857116466220981L;
	
	private String codicePersona;
	private String codiceFiscale;
	private AnagraficaType tipo;
	private String nome;
	private String cognome;
	private String mailAziendale;
	private String mailPrivata;
	private String cellulare;
	private String provinciaDiDomicilio;
	private String comuneDiDomicilio;
	private String indirizzoDiDomicilio;
	private String luogoDiNascita;
	private LocalDate dataDiNascita;
	private String provinciaDiResidenza;
	private String comuneDiResidenza;
	private String indirizzoDiResidenza;
	private String nomeContattoEmergenza;
	private String cellulareContattoEmergenza;
	private String iban;
}
