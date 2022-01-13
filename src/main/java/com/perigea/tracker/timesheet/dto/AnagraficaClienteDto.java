package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.TipologiaPagamentoType;

import lombok.Data;

@Data
public class AnagraficaClienteDto {

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
	private TipologiaPagamentoType tipologiaPagamentoType;
	private String notePerLaFatturazione;
	private String createUser;
	private String lastUpdateUser;



}
