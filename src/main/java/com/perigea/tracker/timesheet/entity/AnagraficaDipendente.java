package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "anagrafica_dipendente")
public class AnagraficaDipendente extends BaseEntity {

	@Id
	@Column(name="codice_persona")
	private String codicePersona;

	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona")
	private Utente utenteDipendente;
	
//	@ManyToOne
//	@JoinColumn(name="codiceResponsabile", referencedColumnName = "cognome")
//	private Utente codiceResponsabile;

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
	

	
	public String getCodicePersona() {
		return codicePersona;
	}

	public void setCodicePersona(String codicePersona) {
		this.codicePersona = codicePersona;
	}

//	public Utente getResponsabile() {
//		return codiceResponsabile;
//	}
//
//	public void setResponsabile(Utente codiceResponsabile) {
//		this.codiceResponsabile = codiceResponsabile;
//	}

	public Utente getUtenteDipendente() {
		return utenteDipendente;
	}

	public void setUtenteDipendente(Utente utenteDipendente) {
		this.utenteDipendente = utenteDipendente;
	}

	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}

	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getMailAziendale() {
		return mailAziendale;
	}

	public void setMailAziendale(String mailAziendale) {
		this.mailAziendale = mailAziendale;
	}

	public String getMailPrivata() {
		return mailPrivata;
	}

	public void setMailPrivata(String mailPrivata) {
		this.mailPrivata = mailPrivata;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getProvinciaDiDomicilio() {
		return provinciaDiDomicilio;
	}

	public void setProvinciaDiDomicilio(String provinciaDiDomicilio) {
		this.provinciaDiDomicilio = provinciaDiDomicilio;
	}

	public String getComuneDiDomicilio() {
		return comuneDiDomicilio;
	}

	public void setComuneDiDomicilio(String comuneDiDomicilio) {
		this.comuneDiDomicilio = comuneDiDomicilio;
	}

	public String getIndirizzoDiDomicilio() {
		return indirizzoDiDomicilio;
	}

	public void setIndirizzoDiDomicilio(String indirizzoDiDomicilio) {
		this.indirizzoDiDomicilio = indirizzoDiDomicilio;
	}

	public String getProvinciaDiResidenza() {
		return provinciaDiResidenza;
	}

	public void setProvinciaDiResidenza(String provinciaDiResidenza) {
		this.provinciaDiResidenza = provinciaDiResidenza;
	}

	public String getComuneDiResidenza() {
		return comuneDiResidenza;
	}

	public void setComuneDiResidenza(String comuneDiResidenza) {
		this.comuneDiResidenza = comuneDiResidenza;
	}

	public String getIndirizzoDiResidenza() {
		return indirizzoDiResidenza;
	}

	public void setIndirizzoDiResidenza(String indirizzoDiResidenza) {
		this.indirizzoDiResidenza = indirizzoDiResidenza;
	}

	public String getNomeContattoEmergenza() {
		return nomeContattoEmergenza;
	}

	public void setNomeContattoEmergenza(String nomeContattoEmergenza) {
		this.nomeContattoEmergenza = nomeContattoEmergenza;
	}

	public String getCellulareContattoEmergenza() {
		return cellulareContattoEmergenza;
	}

	public void setCellulareContattoEmergenza(String cellulareContattoEmergenza) {
		this.cellulareContattoEmergenza = cellulareContattoEmergenza;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Date getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
}