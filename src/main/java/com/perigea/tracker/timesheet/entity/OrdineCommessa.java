package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.OrdineCommessaKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "ordine_commessa")
@EqualsAndHashCode(callSuper = true)
public class OrdineCommessa extends BaseEntity {

	private static final long serialVersionUID = -133148349143213116L;

	@EmbeddedId
	private OrdineCommessaKey id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "codice_commessa", nullable = false, insertable = false, updatable = false)
	private CommessaFatturabile commessaFatturabile;
	
	@ManyToOne
	@JoinColumn(name = "codice_azienda", nullable = false, insertable = false, updatable = false)
	private Cliente cliente;

	@Column(name = "data_ordine")
	private LocalDate dataOrdine;

	@Column(name = "importo_ordine")
	private Double importoOrdine;

	@Column(name = "data_inizio")
	private LocalDate dataInizio;

	@Column(name = "data_fine")
	private LocalDate dataFine;

	@Column(name = "importo_residuo")
	private Double importoResiduo;

}