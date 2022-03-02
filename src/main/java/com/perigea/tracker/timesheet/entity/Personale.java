package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.PersonaleType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "personale")
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public class Personale extends BaseEntity {

	private static final long serialVersionUID = -1364490410610646111L;

	@Id
	@Column(name = "codice_persona")
	private String codicePersona;

	@Column(name = "tipo", nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private PersonaleType tipo;

	@Column(name = "codice_responsabile", insertable = false, updatable = false)
	private String codiceResponsabile;

	@Column(name = "data_assunzione")
	private LocalDate dataAssunzione;

	@Column(name = "data_cessazione")
	private LocalDate dataCessazione;

	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_persona")
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "codice_responsabile")
	private Personale responsabile;

	@OneToMany(mappedBy = "personale")
	private List<NotaSpese> noteSpese = new ArrayList<>();

	@OneToMany(mappedBy = "personale")
	private List<Timesheet> timesheet = new ArrayList<>();

	@OneToMany(mappedBy = "personale", cascade = CascadeType.ALL)
	private List<PersonaleCommessa> commessePersonale = new ArrayList<>();

	@OneToMany(mappedBy = "personale")
	private List<StoricoAssegnazioneCentroCosto> storicoAssegnazioneCentroCosto = new ArrayList<>();

	@OneToMany(mappedBy = "responsabile")
	private List<Personale> sottoposti = new ArrayList<>();

	public void addSottoposto(Personale dipendente) {
		this.sottoposti.add(dipendente);
		dipendente.setResponsabile(this);
	}

	public void removeSottoposto(Personale dipendente) {
		this.sottoposti.remove(dipendente);
		dipendente.setResponsabile(null);
	}

	public void addTimesheet(Timesheet timesheet) {
		this.timesheet.add(timesheet);
		timesheet.setPersonale(this);
	}

	public void removeTimesheet(Timesheet timesheet) {
		this.timesheet.remove(timesheet);
		timesheet.setPersonale(null);
	}

}