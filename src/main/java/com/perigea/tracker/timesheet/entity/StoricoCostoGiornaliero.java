package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.StoricoGiornalieroKey;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The persistent class for the costo_giornaliero_storico database table.
 * TODO FIXME solo per consulente
 * 
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="storico_costo_giornaliero")
public class StoricoCostoGiornaliero extends BaseEntity {

	private static final long serialVersionUID = -5503790109946009969L;

	@EmbeddedId
	private StoricoGiornalieroKey id;

	@Column(name="costo_giornaliero")
	private BigDecimal costoGiornaliero;
	
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Consulente personale;

}