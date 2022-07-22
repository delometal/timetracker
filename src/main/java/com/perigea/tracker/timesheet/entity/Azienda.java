package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.perigea.tracker.commons.enums.AziendaType;
import com.perigea.tracker.commons.enums.PagamentoType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "azienda")
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", discriminatorType = DiscriminatorType.STRING)
public class Azienda extends BaseEntity {

	private static final long serialVersionUID = -2863146642413765101L;

	@Id
	@Column(name = "codice_azienda", nullable = false)
	private String codiceAzienda;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false, insertable = false, updatable = false)
	private AziendaType tipo;
	
	@Column(name = "partita_iva", unique = true)
	private String partitaIva;
	
	@Column(name = "ragione_sociale")
	private String ragioneSociale;

	@Column(name = "codice_fiscale")
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
	
	@OneToMany(mappedBy = "azienda")
	private List<Utente> contatti = new ArrayList<>();
	
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(mappedBy = "azienda", cascade = CascadeType.ALL, optional = true)
	private LogoAzienda logo;
	
	public void addContatto(Utente contatto) {
		this.contatti.add(contatto);
		contatto.setAzienda(this);
	}

	public void removeContatto(Utente contatto) {
		this.contatti.remove(contatto);
		contatto.setAzienda(null);
	}

}
