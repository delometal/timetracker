package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdOrdineCommessaKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ordine_commessa")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrdineCommessa extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -133148349143213116L;

	@EmbeddedId
	private RelazioneIdOrdineCommessaKey id;

	@OneToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable = false, updatable = false)
	private CommessaFatturabile commessaFatturabile;
	
	@ManyToOne
	@JoinColumn(name = "ragione_sociale_cliente", referencedColumnName = "ragione_sociale_cliente")
	private AnagraficaCliente ragioneSocialeCliente;

	@Column(name = "data_ordine")
	private Date dataOrdine;

	@Column(name = "importo_ordine")
	private Double importoOrdine;

	@Column(name = "data_inizio")
	private Date dataInizio;

	@Column(name = "data_fine")
	private Date dataFine;

	@Column(name = "importo_residuo")
	private Double importoResiduo;

}