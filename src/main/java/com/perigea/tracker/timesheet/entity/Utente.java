package com.perigea.tracker.timesheet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enums.StatoUtenteType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "utente")
@EqualsAndHashCode(callSuper = true)
public class Utente extends BaseEntity {

	private static final long serialVersionUID = -2342088709313716005L;

	@Id
	@Column(name = "codice_persona", nullable = false)
	private String codicePersona;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cognome", nullable = false)
	private String cognome;

	@Column(name = "password")
	private String password;

	@Column(name = "stato_utente")
	@Enumerated(EnumType.STRING)
	private StatoUtenteType statoUtente;

	@OneToMany(mappedBy = "utente")
	private List<NotaSpese> noteSpese = new ArrayList<>();

	@OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
	private AnagraficaDipendente dipendente;

	@OneToMany(mappedBy = "utente")
	private List<Timesheet> timesheet = new ArrayList<>();

	@OneToMany(mappedBy = "utente") // @TODO fare many to many come per ruoli
	private List<DipendenteCommessa> commesseDipendente = new ArrayList<>();

	@OneToMany(mappedBy = "utente") // @TODO fare many to many ruoli
	private List<UtenteRuoli> utenteRuoli = new ArrayList<>();

	@Column(name = "codice_responsabile", nullable = false, insertable = false, updatable = false)
	private String codiceResponsabile;
	
	@ManyToOne
	@JoinColumn(name = "codice_responsabile")
	private Utente responsabile;

	@OneToMany(mappedBy = "responsabile")
	private List<Utente> dipendenti = new ArrayList<>();

	public void addDipendente(Utente dipendente) {
		this.dipendenti.add(dipendente);
		dipendente.setResponsabile(this);
	}

	public void removeDipendente(Utente dipendente) {
		this.dipendenti.remove(dipendente);
		dipendente.setResponsabile(null);
	}
	
	public void addTimesheet(Timesheet timesheet) {
		this.timesheet.add(timesheet);
		timesheet.setUtente(this);
	}

	public void removeTimesheet(Timesheet timesheet) {
		this.timesheet.remove(timesheet);
		timesheet.setUtente(null);
	}
}