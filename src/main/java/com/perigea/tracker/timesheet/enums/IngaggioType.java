package com.perigea.tracker.timesheet.enums;

public enum IngaggioType {
	
	F("FATTURABILE"),
	NF("NON FATTURABILE");

	IngaggioType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
}
