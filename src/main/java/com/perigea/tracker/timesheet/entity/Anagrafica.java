package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enums.AnagraficaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "anagrafica_persona")
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Anagrafica extends BaseEntity {

	private static final long serialVersionUID = -1364490410610646111L;

	@Id
	@Column(name = "codice_persona")
	private String codicePersona;
	
	@Column(name = "codice_fiscale")
	private String codiceFiscale;
	
	@Column(name = "tipo", nullable = false)
	@Enumerated(EnumType.STRING)
	private AnagraficaType tipo;
	
	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cognome", nullable = false)
	private String cognome;

	@Column(name = "mail_aziendale")
	private String mailAziendale;

	@Column(name = "mail_privata")
	private String mailPrivata;

	@Column(name = "cellulare")
	private String cellulare;
	
	@Column(name = "iban")
	private String iban;
	
	@Column(name = "luogo_di_nascita")
	private String luogoDiNascita;

	@Column(name = "data_di_nascita")
	private LocalDate dataDiNascita;
	
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
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "codice_azienda", insertable = false, updatable = false)
	private Azienda azienda;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Utente utente;

}