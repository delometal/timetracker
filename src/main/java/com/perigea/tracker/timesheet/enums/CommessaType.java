package com.perigea.tracker.timesheet.enums;

public enum CommessaType {
	
	F("Free lancer a Partita Iva"),
	S("Società");

	CommessaType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
}
