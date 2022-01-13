package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.RuoloType;

import lombok.Data;

@Data
public class RuoliDto {
	private RuoloType ruoloType;
	private String descrizioneRuolo;

}
