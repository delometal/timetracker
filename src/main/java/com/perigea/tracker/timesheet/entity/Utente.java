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
	private StatoUtenteType statoUtenteType;

	@OneToMany(mappedBy = "utenteSpesa")
	private List<NotaSpese> noteSpese = new ArrayList<>();

	@OneToOne(mappedBy = "utenteDipendente", cascade = CascadeType.ALL)
	private AnagraficaDipendente dipendente;

	@OneToMany(mappedBy = "utenteTimesheet")
	private List<Timesheet> timesheet = new ArrayList<>();

	@OneToMany(mappedBy = "utente")
	private List<DipendenteCommessa> relazioneCommessa = new ArrayList<>();

	@OneToMany(mappedBy = "utente")
	private List<UtenteRuoli> utenteRuoli = new ArrayList<>();

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

	public void addTimeSheet(Timesheet timesheet) {
		this.timesheet.add(timesheet);
		timesheet.setUtenteTimesheet(this);
	}

	public void removeTimeSheet(Timesheet timesheet) {
		this.timesheet.remove(timesheet);
		timesheet.setUtenteTimesheet(null);
	}
}