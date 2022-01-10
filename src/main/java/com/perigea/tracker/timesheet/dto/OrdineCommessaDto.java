package com.perigea.tracker.timesheet.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdineCommessaDto {
	
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
	
	public OrdineCommessaDto() {
		super();
	}
}
