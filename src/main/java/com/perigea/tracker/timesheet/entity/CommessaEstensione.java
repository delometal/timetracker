package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.CommessaEstensioneKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estensione_commessa")
@EqualsAndHashCode(callSuper = true)
public class CommessaEstensione extends BaseEntity{

	private static final long serialVersionUID = 2452945618141513666L;

	@EmbeddedId
	private CommessaEstensioneKey id;
	
	@Column(name = "importo_ordine_interno_estensione")
	private Float importoInternoEstensione;
	
	@Column(name = "data_fine_estensione")
	private LocalDate dataFineEstensione;
	
	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable = false, updatable = false)
	private Commessa commessa;
	
	
}
