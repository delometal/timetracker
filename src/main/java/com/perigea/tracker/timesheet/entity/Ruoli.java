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

import com.perigea.tracker.timesheet.enumerator.RuoloType;

@Entity
@Table(name="ruoli")
public class Ruoli {

	@Id
	@Column(name="ruolo")
	@Enumerated(EnumType.STRING)
	private RuoloType ruoloType;
	
	@Column(name="descrizione_ruolo")
	private String descrizioneRuolo;
	
	@OneToMany(mappedBy = "ruolo")
	private List<RelazioneUtenteRuolo> utenteRuolo = new ArrayList<>();

	public RuoloType getRuoloType() {
		return ruoloType;
	}

	public void setRuoloType(RuoloType ruoloType) {
		this.ruoloType = ruoloType;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}
	
	
}