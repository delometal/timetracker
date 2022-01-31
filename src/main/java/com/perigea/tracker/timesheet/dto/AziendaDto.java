package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enums.PagamentoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AziendaDto extends BaseDto {

	private static final long serialVersionUID = -7928482382601116096L;
	
	private String codiceAzienda;
	private String ragioneSociale;
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
	
}
