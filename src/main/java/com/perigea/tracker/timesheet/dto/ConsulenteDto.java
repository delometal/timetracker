package com.perigea.tracker.timesheet.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ConsulenteDto extends DipendenteDto {

	private static final long serialVersionUID = -7178486744933294149L;
	
	private String partitaIva;
	private BigDecimal costo;

}
