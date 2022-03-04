package com.perigea.tracker.timesheet.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.StoricoRalKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the ral_storico database table.
 * 
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="storico_ral")
@EqualsAndHashCode(callSuper = true)
public class StoricoRal extends BaseEntity {
	
	private static final long serialVersionUID = -3660747281800134789L;

	@EmbeddedId
	private StoricoRalKey id;

	@Getter @Setter 
	private BigDecimal ral;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable=false, updatable=false)
	private Dipendente personale;

}