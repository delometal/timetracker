package com.perigea.tracker.timesheet.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("F")
@EqualsAndHashCode(callSuper = true)
public class Fornitore extends Azienda {

	private static final long serialVersionUID = 2483740541578886295L;

}
