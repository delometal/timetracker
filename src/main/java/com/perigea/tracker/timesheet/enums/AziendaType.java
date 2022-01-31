package com.perigea.tracker.timesheet.enums;

public enum AziendaType {
	
	C("Cliente"),
	F("Fornitore");
	
	AziendaType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static AziendaType getByDescrizioneStatus (String status) {
		for (AziendaType e : AziendaType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
