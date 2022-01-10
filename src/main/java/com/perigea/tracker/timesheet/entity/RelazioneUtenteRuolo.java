package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdUtenteRuoloKey;
import com.perigea.tracker.timesheet.enumerator.RuoloType;

@Entity
@IdClass(RelazioneIdUtenteRuoloKey.class)
@Table(name = "utente_ruolo")
public class RelazioneUtenteRuolo {

	@Id
	@Column(name="codice_persona")
	private String codicePersona;
	
	@Id
	@Column(name="ruolo")
	private RuoloType ruoloType;
	
	@MapsId
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", nullable = false)
	private Utente utente;
	
	@MapsId
	@ManyToOne
	@JoinColumn(name = "ruolo", referencedColumnName = "ruolo", nullable = false)
	private Ruoli ruolo;

	public String getCodicePersona() {
		return codicePersona;
	}

	public void setCodicePersona(String codicePersona) {
		this.codicePersona = codicePersona;
	}

	public RuoloType getRuoloType() {
		return ruoloType;
	}

	public void setRuoloType(RuoloType ruoloType) {
		this.ruoloType = ruoloType;
	} 
	
	
}
