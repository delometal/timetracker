package com.perigea.tracker.timesheet.enums;


public enum StatoUtenteType {
	ATTIVO("A", "ATTIVO"),
	CESSATO("C", "CESSATO");

	StatoUtenteType(String id, String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
	}
	
	private String id;
	private String descrizione;

	public String getId() {
		return id;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
}

