package com.perigea.tracker.timesheet.enums;

public enum InOutMovimentoType {
	
	E("Entrata"),
	U("UScita");
	
	InOutMovimentoType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static InOutMovimentoType getByDescrizioneStatus (String status) {
		for (InOutMovimentoType e : InOutMovimentoType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
