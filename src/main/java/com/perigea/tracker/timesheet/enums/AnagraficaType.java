package com.perigea.tracker.timesheet.enums;

public enum AnagraficaType {
	
	E("DIPENDENTE ESTERNO, consulente"),
	I("DIPENDENTE INTERNO, dipendente"),
	C("CONTATTO, contatto");
	
	AnagraficaType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static AnagraficaType getByDescrizioneStatus (String status) {
		for (AnagraficaType e : AnagraficaType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
