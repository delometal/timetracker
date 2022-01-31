package com.perigea.tracker.timesheet.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrdineCommessaDto extends BaseDto {
	
	private static final long serialVersionUID = -6403812927893782554L;
	
	private String codiceCommessa;
	private String numeroOrdineCliente;
	private String codiceAzienda;
	private Double importoOrdine;
	private LocalDate dataOrdine;
	private LocalDate dataInizio;
	private LocalDate dataFine;	
	private Double importoResiduo;

}
