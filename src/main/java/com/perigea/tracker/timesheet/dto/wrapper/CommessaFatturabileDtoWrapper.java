package com.perigea.tracker.timesheet.dto.wrapper;

import com.perigea.tracker.timesheet.dto.BaseDto;
import com.perigea.tracker.timesheet.dto.ClienteDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommessaFatturabileDtoWrapper extends BaseDto {

	private static final long serialVersionUID = 6731565659187101446L;
	private CommessaFatturabileDto commessaFatturabile;
	private OrdineCommessaDto ordineCommessa;
	private ClienteDto cliente;
}
