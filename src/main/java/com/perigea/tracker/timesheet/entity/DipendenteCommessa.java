package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "dipendente_commessa")
@EqualsAndHashCode(callSuper = true)
@AssociationOverrides({
    @AssociationOverride(name = "id", joinColumns = @JoinColumn(name = "codice_persona")),
    @AssociationOverride(name = "id", joinColumns = @JoinColumn(name = "codice_commessa")) })
public class DipendenteCommessa extends BaseEntity {

	private static final long serialVersionUID = -2109547859283092218L;

	@EmbeddedId
	private DipendenteCommessaKey id;
	
	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", updatable = false, insertable = false)
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", updatable = false, insertable = false)
	private Commessa commessa;

	@Column(name = "data_inizio_allocazione")
	private Date dataInizioAllocazione;

	@Column(name = "data_fine_allocazione")
	private Date dataFineAllocazione;

	@Column(name = "tariffa")
	private Double tariffa;

	@Column(name = "giorni_previsti")
	private Integer giorniPrevisti;

	@Column(name = "giorni_erogati")
	private Integer giorniErogati;

	@Column(name = "giorni_residui")
	private Integer giorniResidui;

	@Column(name = "importo_previsto")
	private Double importoPrevisto;

	@Column(name = "importo_erogato")
	private Double importoErogato;

	@Column(name = "importo_residuo")
	private Double importoResiduo;
	
}