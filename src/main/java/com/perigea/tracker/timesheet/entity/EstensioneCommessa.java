package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.EstensioneCommessaKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "estensione_commessa")
@EqualsAndHashCode(callSuper = true)
public class EstensioneCommessa extends BaseEntity{

	private static final long serialVersionUID = 2452945618141513666L;

	@EmbeddedId
	private EstensioneCommessaKey id;
	
	@Column(name = "importo_ordine_interno_estensione")
	private Float importoInternoEstensione;
	
	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable = false, updatable = false)
	private Commessa commessa;
	
	
}
