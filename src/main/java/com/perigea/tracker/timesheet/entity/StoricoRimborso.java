package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.StoricoRimborsiKmKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="storico_rimborso")
@EqualsAndHashCode(callSuper = true)
public class StoricoRimborso extends BaseEntity {

	private static final long serialVersionUID = -6346830402224475202L;

	@EmbeddedId
	private StoricoRimborsiKmKey id;

	@Column(name="importo_giornaliero_rimborso")
	private BigDecimal rimborsoPerKm;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Dipendente personale;

}
