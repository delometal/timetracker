package com.perigea.tracker.timesheet.wrapper;

import com.perigea.tracker.timesheet.dto.TimeSheetDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Utente;

public class TimeSheetWrapper {

	private TimeSheetDto timeDto;
	private Utente utente;
	private Commessa commessa;
	
	public TimeSheetDto getTimeDto() {
		return timeDto;
	}
	public void setTimeDto(TimeSheetDto timeDto) {
		this.timeDto = timeDto;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Commessa getCommessa() {
		return commessa;
	}
	public void setCommessa(Commessa commessa) {
		this.commessa = commessa;
	}
	
	
}
