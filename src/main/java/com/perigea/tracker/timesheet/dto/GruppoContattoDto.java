package com.perigea.tracker.timesheet.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GruppoContattoDto extends BaseDto {

	private static final long serialVersionUID = -7487811450222629459L;

	private Long id;
	private String nome;
	private String descrizione;
	private List<ContattoDto> contatti = new ArrayList<>();

}
