package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class DipendenteCommessaKey implements Serializable {

	private static final long serialVersionUID = -2275333400404753544L;

	@Column(name="codice_persona")
	private String codicePersona;
	
	@Column(name="codice_commessa")
	private String codiceCommessa;

}
