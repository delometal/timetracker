package com.perigea.tracker.timesheet.wrapper;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.CommessaDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//nome timesheet wrapper
public class CommessaFatturabileWrapper {
	
	private CommessaFatturabileDto commessaFatturabileDto;
	private OrdineCommessaDto ordineCommessa;
	private AnagraficaClienteDto anagraficaCliente;
	private CommessaDto dtoCommessa;

}
