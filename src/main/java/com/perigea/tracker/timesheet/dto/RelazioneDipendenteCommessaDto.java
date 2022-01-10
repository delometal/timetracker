package com.perigea.tracker.timesheet.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelazioneDipendenteCommessaDto {

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

	public RelazioneDipendenteCommessaDto() {
		super();
	}
}
