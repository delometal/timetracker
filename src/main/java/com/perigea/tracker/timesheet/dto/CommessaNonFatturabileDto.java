package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import com.perigea.tracker.timesheet.enumerator.TipoCommessaType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommessaNonFatturabileDto extends CommessaDto {
	
	private String descrizione;
	
	public CommessaNonFatturabileDto() {
		super();
	}
}
