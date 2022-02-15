package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.commons.enums.LivelloContrattoType;
import com.perigea.tracker.timesheet.entity.keys.StoricoLivelloContrattualeKey;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The persistent class for the livello_contrattuale_storico database table.
 * 
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name="storico_livello_contrattuale")
public class StoricoLivelloContrattuale extends BaseEntity {

	private static final long serialVersionUID = -1927584912740493417L;

	@EmbeddedId
	private StoricoLivelloContrattualeKey id;

	@Enumerated(EnumType.STRING)
	@Column(name = "livelllo")
	private LivelloContrattoType livello;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Dipendente personale;

}