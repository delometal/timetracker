package com.perigea.tracker.timesheet.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "contatto")
@EqualsAndHashCode(callSuper = true)
public class Contatto extends BaseEntity {

	private static final long serialVersionUID = 3389190757084533155L;

	@Id
	@Column(name = "id_contatto", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cognome", nullable = false)
	private String cognome;
	
	@Column(name = "codice_fiscale", unique = true)
	private String codiceFiscale;

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

}