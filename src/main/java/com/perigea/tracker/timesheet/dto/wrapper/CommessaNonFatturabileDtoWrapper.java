package com.perigea.tracker.timesheet.dto.wrapper;

import com.perigea.tracker.timesheet.dto.BaseDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommessaNonFatturabileDtoWrapper extends BaseDto {

	private static final long serialVersionUID = -8479993455768944044L;
	private CommessaNonFatturabileDto commessaNonFatturabile;

}
