package com.perigea.tracker.timesheet.enums;

public enum PrefatturazioneStatoType {
	
	ATTESA_ORDINE("Attesa ordine"),
	ATTESA_BENESTARE("Attesa benestare"),
	ATTESA_DELIVERABLE("Attesa deliverable TK"),
	FATTURABILE("Fatturabile");
	
	PrefatturazioneStatoType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static PrefatturazioneStatoType getByDescrizioneStatus (String status) {
		for (PrefatturazioneStatoType e : PrefatturazioneStatoType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
