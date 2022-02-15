package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.StoricoRimborsiKmKey;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The persistent class for the rimborsi_km_storico database table.
 * 
 */
@Data
@Entity
@Table(name="storico_rimborsi_km")
@EqualsAndHashCode(callSuper = true)
public class StoricoRimborsiKm extends BaseEntity {

	private static final long serialVersionUID = 7113352745900934103L;

	@EmbeddedId
	private StoricoRimborsiKmKey id;

	@Column(name="modello_auto")
	private String modelloAuto;

	@Column(name="rimborso_per_km")
	private BigDecimal rimborsoPerKm;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Dipendente personale;

}