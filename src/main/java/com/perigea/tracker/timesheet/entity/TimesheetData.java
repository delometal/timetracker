package com.perigea.tracker.timesheet.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import com.perigea.tracker.timesheet.entity.keys.TimesheetDataKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "time_sheet_data")
@EqualsAndHashCode(callSuper = true)
public class TimesheetData extends BaseEntity {

	private static final long serialVersionUID = -3241359472237290256L;

	@EmbeddedId
	private TimesheetDataKey id;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable = false, updatable = false),
			@JoinColumn(name = "anno_di_riferimento", referencedColumnName = "anno_di_riferimento", insertable = false, updatable = false),
			@JoinColumn(name = "mese_di_riferimento", referencedColumnName = "mese_di_riferimento", insertable = false, updatable = false) })
	private Timesheet timeSheet;

//	@ManyToOne
//	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable=false, updatable=false)
//	private Commessa commessaTimesheet;

	@AnyMetaDef(name = "CommessaMetaDef", metaType = "string", idType = "string", metaValues = {
			@MetaValue(value = "F", targetEntity = CommessaFatturabile.class),
			@MetaValue(value = "NF", targetEntity = CommessaNonFatturabile.class) })
	@Any(metaDef = "CommessaMetaDef", metaColumn = @Column(name = "tipo_commessa"),fetch = FetchType.EAGER)
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, updatable = false, insertable = false)
	private Commessa commessa;

	@Column(name = "ore")
	private Integer ore;

	@Column(name = "trasferta")
	private Boolean trasferta;

}