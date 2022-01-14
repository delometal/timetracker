package com.perigea.tracker.timesheet.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.TimesheetKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "time_sheet")
@EqualsAndHashCode(callSuper=true)
public class Timesheet extends BaseEntity {

	private static final long serialVersionUID = -3241359472237290256L;

	@EmbeddedId
	private TimesheetKey id;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", nullable = false, insertable=false, updatable=false)
	private Utente utenteTimesheet;

	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable=false, updatable=false)
	private Commessa commessaTimesheet;

	@Column(name = "ore")
	private Integer ore;

	@Column(name = "trasferta")
	private Boolean trasferta;

	@Column(name = "stato_time_sheet")
	private String statoType;
	
}