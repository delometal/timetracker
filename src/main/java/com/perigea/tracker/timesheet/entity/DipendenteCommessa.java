package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.perigea.tracker.timesheet.entity.keys.DipendenteCommessaKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "dipendente_commessa")
@EqualsAndHashCode(callSuper = true)
@AssociationOverrides({
    @AssociationOverride(name = "id.utente",
        joinColumns = @JoinColumn(name = "codice_persona")),
    @AssociationOverride(name = "id.commessa",
        joinColumns = @JoinColumn(name = "codice_commessa")) })
public class DipendenteCommessa extends BaseEntity {

	private static final long serialVersionUID = -2109547859283092218L;

	@EmbeddedId
	private DipendenteCommessaKey id;

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
	
	@Transient
	public Utente getUtente() {
		return id.getUtente();
	}

	@Transient
	private Commessa getCommessa() {
		return id.getCommessa();
	}

}