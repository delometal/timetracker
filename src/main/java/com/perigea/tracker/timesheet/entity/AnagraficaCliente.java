package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enums.PagamentoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "anagrafica_cliente")
@EqualsAndHashCode(callSuper = true)
public class AnagraficaCliente extends BaseEntity {

	private static final long serialVersionUID = -2863146642413765101L;

	@Id
	@Column(name = "partita_iva", nullable = false)
	private String partitaIva;
	
	@Column(name = "ragione_sociale_cliente")
	private String ragioneSociale;

	@Column(name = "codice_fiscale", nullable = false)
	private String codiceFiscale;

	@Column(name = "codice_destinatario")
	private String codiceDestinatario;

	@Column(name = "sede_legale_comune")
	private String sedeLegaleComune;

	@Column(name = "sede_legale_cap")
	private String sedeLegaleCap;

	@Column(name = "sede_legale_indirizzo")
	private String sedeLegaleIndirizzo;

	@Column(name = "sede_operativa_comune")
	private String sedeOperativaComune;

	@Column(name = "sede_operativa_cap")
	private String sedeOperativaCap;

	@Column(name = "sede_operativa_indirizzo")
	private String sedeOperativaIndirizzo;

	@Column(name = "acronimo_cliente")
	private String acronimoCliente;

	@Column(name = "progressivo_per_commesse")
	private Integer progressivoPerCommesse;

	@Column(name = "tipologia_di_pagamento")
	@Enumerated(EnumType.STRING)
	private PagamentoType tipologiaPagamentoType;

	@Column(name = "note_per_la_fatturazione")
	private String notePerLaFatturazione;

	@OneToMany(mappedBy = "cliente")
	private List<CommessaFatturabile> commesse = new ArrayList<>();

	@OneToMany(mappedBy = "cliente")
	private List<OrdineCommessa> ordiniCommesse = new ArrayList<>();

}
