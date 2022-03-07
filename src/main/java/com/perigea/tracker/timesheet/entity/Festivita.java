package com.perigea.tracker.timesheet.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "festivita")
@EqualsAndHashCode(callSuper = true)
public class Festivita extends BaseEntity {

	private static final long serialVersionUID = 1163648805713862098L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "data")
	private LocalDate data;

	@Column(name = "nome_festivo", unique = true)
	private String nomeFestivo;

}