package com.perigea.tracker.timesheet.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdTimeSheetKey;

@Entity
@IdClass(RelazioneIdTimeSheetKey.class)
@Table(name = "time_sheet")
public class TimeSheet extends BaseEntity{


	@Id
	@Column(name = "anno_di_riferimento")
	private Integer annoDiRiferimento;

	@Id
	@Column(name = "mese_di_riferimento")
	private Integer meseDiRiferimento;
	
	@Id
	@Column(name = "giorno_di_riferimento")
	private Integer giornoDiRiferimento;


	@MapsId
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", nullable = false)
	private Utente utenteTimeSheet;

	
	@MapsId
	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false)
	private Commessa commessaTimeSheet;

	@Column(name = "ore")
	private Integer ore;

	@Column(name = "trasferta")
	private Boolean trasferta;

	@Column(name = "stato_time_Sheet")
	private String statoType;
	
	
	public Integer getAnnoDiRiferimento() {
		return annoDiRiferimento;
	}

	public void setAnnoDiRiferimento(Integer annoDiRiferimento) {
		this.annoDiRiferimento = annoDiRiferimento;
	}

	public Integer getMeseDiRiferimento() {
		return meseDiRiferimento;
	}

	public void setMeseDiRiferimento(Integer meseDiRiferimento) {
		this.meseDiRiferimento = meseDiRiferimento;
	}

	public Utente getUtenteTime() {
		return utenteTimeSheet;
	}

	public void setUtenteTime(Utente utenteTime) {
		this.utenteTimeSheet = utenteTime;
	}

	public Commessa getCommessaTimeSheet() {
		return commessaTimeSheet;
	}

	public void setCommessaTimeSheed(Commessa commessaTimeSheet) {
		this.commessaTimeSheet = commessaTimeSheet;
	}

	public Integer getGiornoDiRiferimento() {
		return giornoDiRiferimento;
	}

	public void setGiornoDiRiferimento(Integer giornoDiRiferimento) {
		this.giornoDiRiferimento = giornoDiRiferimento;
	}

	public Integer getOre() {
		return ore;
	}

	public void setOre(Integer ore) {
		this.ore = ore;
	}

	public Boolean getTrasferta() {
		return trasferta;
	}

	public void setTrasferta(Boolean trasferta) {
		this.trasferta = trasferta;
	}

	public String getStatoType() {
		return statoType;
	}

	public void setStatoType(String statoType) {
		this.statoType = statoType;
	}

}