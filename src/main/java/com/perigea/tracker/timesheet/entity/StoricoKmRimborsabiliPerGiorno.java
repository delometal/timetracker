package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.StoricoKmRimborsabiliPerGiornoKey;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the km_rimborsabili_per_giorno_storico database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="storico_km_rimborsabili_per_giorno")
public class StoricoKmRimborsabiliPerGiorno extends BaseEntity {

	private static final long serialVersionUID = 6811981237667182734L;

	@EmbeddedId
	private StoricoKmRimborsabiliPerGiornoKey id;

	@Column(name="km_per_giorno")
	private BigDecimal kmPerGiorno;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", updatable = false, insertable = false)
	private Dipendente personale;
	
}