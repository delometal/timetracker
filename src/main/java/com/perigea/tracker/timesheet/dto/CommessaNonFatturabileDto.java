package com.perigea.tracker.timesheet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommessaNonFatturabileDto extends BaseDto {

	private static final long serialVersionUID = -9149910105719253932L;
	
	private String codiceCommessa;
	private String descrizione;
	private CommessaDto commessa;
	
}
