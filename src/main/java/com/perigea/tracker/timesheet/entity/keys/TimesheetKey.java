package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TimesheetKey implements Serializable {

	private static final long serialVersionUID = 3117858353342771303L;

	@Column(name = "anno_di_riferimento")
	private Integer annoDiRiferimento;

	@Column(name = "mese_di_riferimento")
	private Integer meseDiRiferimento;

	@Column(name = "giorno_di_riferimento")
	private Integer giornoDiRiferimento;

	@Column(name = "codice_persona")
	private String codicePersona;

	@Column(name = "codice_commessa")
	private String codiceCommessa;

}
