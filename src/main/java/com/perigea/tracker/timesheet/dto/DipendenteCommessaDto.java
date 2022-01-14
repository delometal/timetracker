package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DipendenteCommessaDto extends BaseDto {

	private static final long serialVersionUID = 7390177479583346093L;

	private String codiceCommessa;
	private String codicePersona;
	private Date dataInizioAllocazione;
	private Date dataFineAllocazione;
	private Double tariffa;
	private Integer giorniPrevisti;
	private Integer giorniErogati;
	private Integer giorniResidui;
	private Double importoPrevisto;
	private Double importoErogato;
	private Double importoResiduo;
	private String createUser;
	private String lastUpdateUser;

}
