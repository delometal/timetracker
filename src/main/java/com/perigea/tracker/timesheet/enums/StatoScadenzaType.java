package com.perigea.tracker.timesheet.enums;

public enum StatoScadenzaType {

	S("Sospeso"),
	P("Pagata");
	
	StatoScadenzaType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static StatoScadenzaType getByDescrizioneStatus (String status) {
		for (StatoScadenzaType e : StatoScadenzaType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
