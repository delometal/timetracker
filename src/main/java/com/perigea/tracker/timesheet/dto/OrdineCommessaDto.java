package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrdineCommessaDto extends BaseDto {
	
	private static final long serialVersionUID = -6403812927893782554L;
	
	private String codiceCommessa;
	private String numeroOrdineCliente;
	private String ragioneSocialeCliente;
	private Date dataOrdine;
	private Double importoOrdine;
	private Date dataInizio;
	private Date dataFine;
	private Double importoResiduo;
	private String createUser;
	private String lastUpdateUser;

}
