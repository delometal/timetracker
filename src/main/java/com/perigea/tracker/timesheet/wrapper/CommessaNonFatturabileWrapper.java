package com.perigea.tracker.timesheet.wrapper;

import com.perigea.tracker.timesheet.dto.CommessaDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;

public class CommessaNonFatturabileWrapper {
	
	private CommessaNonFatturabileDto commessaParam;
	private CommessaDto commessa;
	public CommessaNonFatturabileDto getCommessaParam() {
		return commessaParam;
	}
	public void setCommessaParam(CommessaNonFatturabileDto commessaParam) {
		this.commessaParam = commessaParam;
	}
	public CommessaDto getCommessa() {
		return commessa;
	}
	public void setCommessa(CommessaDto commessa) {
		this.commessa = commessa;
	}
	
	
	
	

}
