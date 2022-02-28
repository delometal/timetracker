package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.ContrattoType;
import com.perigea.tracker.timesheet.entity.keys.StoricoLivelloContrattualeKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="storico_tipo_contratto")
public class StoricoContrattoType extends BaseEntity {

	private static final long serialVersionUID = 5385049400595527969L;

	@EmbeddedId
	private StoricoLivelloContrattualeKey id;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_contratto")
	private ContrattoType tipo;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Dipendente personale;

}