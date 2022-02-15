package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.NotaSpeseKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "nota_spese")
@EqualsAndHashCode(callSuper = true)
public class NotaSpese extends BaseEntity {

	private static final long serialVersionUID = 4591180507735178202L;
	
	@EmbeddedId
	private NotaSpeseKey id;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", nullable = false, insertable = false, updatable = false)
	private Personale personale;

	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable = false, updatable = false)
	private Commessa commessa;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable = false, updatable = false),
		@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", insertable = false, updatable = false),
		@JoinColumn(name = "giorno_di_riferimento", referencedColumnName = "giorno_di_riferimento", insertable = false, updatable = false),
		@JoinColumn(name = "anno_di_riferimento", referencedColumnName = "anno_di_riferimento", insertable = false, updatable = false),
		@JoinColumn(name = "mese_di_riferimento", referencedColumnName = "mese_di_riferimento", insertable = false, updatable = false) })
	private TimesheetEntry timesheetEntry;

	@Column(name = "importo")
	private Double importo;

}