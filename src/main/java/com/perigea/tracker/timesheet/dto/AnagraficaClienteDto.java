package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.PagamentoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AnagraficaClienteDto extends BaseDto {

	private static final long serialVersionUID = 3875267041004676543L;
	
	private String ragioneSocialeCliente;
	private String partitaIva;
	private String codiceFiscale;
	private String codiceDestinatario;
	private String sedeLegaleComune;
	private String sedeLegaleCap;
	private String sedeLegaleIndirizzo;
	private String sedeOperativaComune;
	private String sedeOperativaCap;
	private String sedeOperativaIndirizzo;
	private String acronimoCliente;
	private Integer progressivoPerCommesse;
	private PagamentoType tipologiaPagamentoType;
	private String notePerLaFatturazione;
	private String createUser;
	private String lastUpdateUser;

}
