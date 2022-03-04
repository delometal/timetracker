package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.StoricoAssegnazioneCentroCostoKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the assegnazione_centro_costo_storico database table.
 * 
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name="storico_assegnazione_centro_costo")
public class StoricoAssegnazioneCentroCosto extends BaseEntity {

	private static final long serialVersionUID = 7635721021537306676L;

	@EmbeddedId
	private StoricoAssegnazioneCentroCostoKey id;

	@Column(name="codice_centro_di_costo")
	private String codiceCentroDiCosto;
	
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Personale personale;

}