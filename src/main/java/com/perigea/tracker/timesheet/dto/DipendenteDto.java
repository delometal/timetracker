package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DipendenteDto extends AnagraficaDto {

	private static final long serialVersionUID = -8948320302365869807L;
	
	private UtenteDto utente;
	private Date dataAssunzione;
	private Date dataCessazione;
	private String provinciaDiDomicilio;
	private String comuneDiDomicilio;
	private String indirizzoDiDomicilio;
	private String provinciaDiResidenza;
	private String comuneDiResidenza;
	private String indirizzoDiResidenza;
	private String nomeContattoEmergenza;
	private String cellulareContattoEmergenza;

}
