package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.StoricoIngaggioKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name="storico_ingaggio")
public class StoricoIngaggio extends BaseEntity{

	private static final long serialVersionUID = 4716569365008934404L;

	@EmbeddedId
	private StoricoIngaggioKey id;
	
	@Column(name = "costo_giornaliero")
	private BigDecimal costoGiornaliero;
	
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Consulente personale;
	
}
