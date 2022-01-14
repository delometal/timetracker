package com.perigea.tracker.timesheet.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericWrapperResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 2634932840012305168L;
	
	private Date dataRichiesta;
	private T risultato;

}
