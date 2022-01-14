package com.perigea.tracker.timesheet.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FestivitaDto extends BaseDto {

	private static final long serialVersionUID = 1622990893357416466L;
	
	private LocalDate data;
	private String nomeFestivo;
}
