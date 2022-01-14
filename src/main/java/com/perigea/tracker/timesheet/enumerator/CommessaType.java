package com.perigea.tracker.timesheet.enumerator;

public enum CommessaType {
	
	F("FATTURABILE"),
	NF("NON FATTURABILE");

	CommessaType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
}
