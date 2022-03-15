package com.perigea.tracker.timesheet.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@DiscriminatorValue("S")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CommessaNonFatturabile extends Commessa {

	private static final long serialVersionUID = 2256677862368637963L;
	
	public CommessaNonFatturabile(String descrizioneCommessa) {
		this.setDescrizioneCommessa(descrizioneCommessa);
	}

}