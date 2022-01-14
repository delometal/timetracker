package com.perigea.tracker.timesheet.enumerator;

public enum StatoType {
	I("INSERITO"),
	C("CONFERMATO"),
	V("VERIFICATO");

	StatoType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
}
