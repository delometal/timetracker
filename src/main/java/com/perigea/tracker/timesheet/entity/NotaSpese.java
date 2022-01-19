package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	private Utente utente;

	@OneToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable = false, updatable = false)
	private Commessa commessa;

	@Column(name = "importo")
	private Double importo;

}