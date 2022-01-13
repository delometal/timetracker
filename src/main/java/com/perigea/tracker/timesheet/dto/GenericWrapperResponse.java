package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericWrapperResponse<T> {
	
	private Date dataRichiesta;
	private T risultato;

}
