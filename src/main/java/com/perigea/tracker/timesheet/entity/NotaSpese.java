package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdNotaSpeseKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "nota_spese")
@Data
@EqualsAndHashCode(callSuper = true)
public class NotaSpese extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4591180507735178202L;
	@EmbeddedId
	private RelazioneIdNotaSpeseKey id;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable = false, updatable = false)
	private Utente utenteSpesa;

	@OneToOne
	@JoinColumn(name = "codice_commessa", nullable = false, referencedColumnName = "codice_commessa", insertable = false, updatable = false)
	private Commessa commessaSpesa;

	@Column(name = "importo")
	private Double importo;

}