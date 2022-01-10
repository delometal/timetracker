package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import com.perigea.tracker.timesheet.enumerator.TipologiaPagamentoType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericWrapperResponse<T> {
	
	private Date dataRichiesta;
	private String utenteModifica;
	private T risultato;

}
