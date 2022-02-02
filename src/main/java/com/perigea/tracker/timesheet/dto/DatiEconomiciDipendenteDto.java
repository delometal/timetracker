package com.perigea.tracker.timesheet.dto;

import java.time.LocalDate;

import com.perigea.tracker.commons.enums.ContrattoType;
import com.perigea.tracker.commons.enums.JobTitle;
import com.perigea.tracker.commons.enums.LivelloContrattoType;
import com.perigea.tracker.commons.enums.SceltaTredicesimaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DatiEconomiciDipendenteDto extends BaseDto {

	private static final long serialVersionUID = -8822970128086429184L;
	
	private String codicePersona;
	private LivelloContrattoType livelloIniziale;
	private ContrattoType tipoContrattoIniziale;
	private Float ralIniziale;
	private Float ralAttuale;
	private LocalDate decorrenzaRalAttuale;
	private LocalDate dataAssegnazioneTicket;
	private Float rimborsGiornaliero;
	private LocalDate decorrenzaRimborso;
	private LivelloContrattoType livelloAttuale;
	private LocalDate decorrenzaLivello;
	private ContrattoType tipoContrattoAttuale;
	private JobTitle jobTitle;
	private SceltaTredicesimaType sceltaTredicesima;
	private Float ultimoPremio;
	private LocalDate dataUltimoBonus;
	private String modelloAuto;
	private Float rimborsoPerKm;
	private Float kmPerGiorno;
	private Float costoGiornaliero;
	private LocalDate dataDecorrenzaCosto;
	private String codiceCentroDiCosto;
	private LocalDate decorrenzaAssegnazioneCentroDiCosto;
	
}
