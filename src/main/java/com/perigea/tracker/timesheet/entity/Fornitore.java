package com.perigea.tracker.timesheet.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "fornitore")
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("F")
public class Fornitore extends Azienda {

	private static final long serialVersionUID = 2483740541578886295L;

}
