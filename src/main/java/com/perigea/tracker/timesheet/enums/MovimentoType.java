package com.perigea.tracker.timesheet.enums;

public enum MovimentoType {
	
	S("Spesa"),
	G("Giroconto"),
	I("Iva"),
	R("Ricarica"),
	U("Utili"),
	P("Prelievo");
	
	MovimentoType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static MovimentoType getByDescrizioneStatus (String status) {
		for (MovimentoType e : MovimentoType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
