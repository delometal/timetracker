package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "anagrafica_dipendente")
@EqualsAndHashCode(callSuper = true)
public class AnagraficaDipendente extends BaseEntity {

	private static final long serialVersionUID = -1364490410610646111L;

	@Id
	@Column(name = "codice_persona")
	private String codicePersona;

	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Utente utenteDipendente;

	@Column(name = "luogo_di_nascita")
	private String luogoDiNascita;

	@Column(name = "data_di_nascita")
	private Date dataDiNascita;

	@Column(name = "mail_aziendale")
	private String mailAziendale;

	@Column(name = "mail_privata")
	private String mailPrivata;

	@Column(name = "cellulare")
	private String cellulare;

	@Column(name = "provincia_di_domicilio")
	private String provinciaDiDomicilio;

	@Column(name = "comune_di_domicilio")
	private String comuneDiDomicilio;

	@Column(name = "indirizzo_di_domicilio")
	private String indirizzoDiDomicilio;

	@Column(name = "provincia_di_residenza")
	private String provinciaDiResidenza;

	@Column(name = "comune_di_residenza")
	private String comuneDiResidenza;

	@Column(name = "indirizzo_di_residenza")
	private String indirizzoDiResidenza;

	@Column(name = "nome_contatto_emergenza")
	private String nomeContattoEmergenza;

	@Column(name = "cellulare_contatto_emergenza")
	private String cellulareContattoEmergenza;

	@Column(name = "data_assunzione")
	private Date dataAssunzione;

	@Column(name = "iban")
	private String iban;

	@Column(name = "data_cessazione")
	private Date dataCessazione;

	@Column(name = "codice_fiscale")
	private String codiceFiscale;

}