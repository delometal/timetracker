package com.perigea.tracker.timesheet.enumerator;


public enum StatoUtenteType {
	A("ATTIVO"),
	C("CESSATO");

	StatoUtenteType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
}

