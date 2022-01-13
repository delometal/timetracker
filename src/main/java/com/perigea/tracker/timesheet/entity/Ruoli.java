package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;
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

import lombok.Data;

@Entity
@Table(name = "ruoli")
@Data
public class Ruoli implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -870008112526011657L;

	@Id
	@Column(name = "ruolo")
	@Enumerated(EnumType.STRING)
	private RuoloType ruoloType;

	@Column(name = "descrizione_ruolo")
	private String descrizioneRuolo;

	@OneToMany(mappedBy = "ruolo")
	private List<RelazioneUtenteRuolo> utenteRuolo = new ArrayList<>();

}