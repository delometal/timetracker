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
	
	public static StatoRichiestaType getByDescrizioneStatus (String status) {
		for (StatoRichiestaType e : StatoRichiestaType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
