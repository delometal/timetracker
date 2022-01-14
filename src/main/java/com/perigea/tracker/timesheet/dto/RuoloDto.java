package com.perigea.tracker.timesheet.dto;

import com.perigea.tracker.timesheet.enumerator.RuoloType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RuoloDto extends BaseDto {

	private static final long serialVersionUID = 8808088437222733010L;

	private RuoloType ruoloType;
	private String descrizioneRuolo;

}
