package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EstensioneCommessaKey implements Serializable{

	private static final long serialVersionUID = -4726010809087836808L;
	
	@Column(name = "Codice_commessa")
	private String codiceCommessa;
	
	@Column(name = "Data_estensione")
	private LocalDate dataEstensione;

}
