package com.perigea.tracker.timesheet.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FestivitaDto {

	private LocalDate data;
	private String nomeFestivo;
}
