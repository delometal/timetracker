package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Utente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DipendenteCommessaKey implements Serializable {

	private static final long serialVersionUID = -2275333400404753544L;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona")
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa")
	private Commessa commessa;

}
