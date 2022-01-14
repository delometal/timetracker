package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.perigea.tracker.timesheet.enums.CostoNotaSpeseType;

import lombok.Data;

@Data
@Embeddable
public class NotaSpeseKey implements Serializable {

	private static final long serialVersionUID = 1084967773079390935L;

	@Column(name = "anno_di_riferimento")
	private Integer annoDiRiferimento;

	@Column(name = "mese_di_riferimento")
	private Integer meseDiRiferimento;

	@Column(name = "codice_persona")
	private String codicePersona;

	@Column(name = "codice_commessa")
	private String codiceCommessa;

	@Column(name = "giorno_di_riferimento")
	private Integer giornoDiRiferimento;

	@Column(name = "tipo_costo")
	@Enumerated(EnumType.STRING)
	private CostoNotaSpeseType tipoCostoNotaSpeseType;

}
