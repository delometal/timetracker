package com.perigea.tracker.timesheet.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DatiEconomiciConsulenteDto extends BaseDto {

	private static final long serialVersionUID = 6422776391515180586L;
	
	private String codicePersona;
	private LocalDate datIngaggio;
	private LocalDate decorrenzaAssegnazioneCentroDiCosto;
	private String codiceCentroDiCosto;
	private Float tipoIngaggio;
	private Float costoGiornaliero;
	
}
