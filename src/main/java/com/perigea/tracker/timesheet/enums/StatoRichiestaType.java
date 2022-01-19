package com.perigea.tracker.timesheet.enums;

public enum StatoRichiestaType {
	I("INSERITO"),
	C("CONFERMATO"),
	V("VERIFICATO");

	StatoRichiestaType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
}
