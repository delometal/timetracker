package com.perigea.tracker.timesheet.enums;

public enum GiustificativoType {
	
	SC("Scontrino"),
	FT("Fattura"),
	FE("Fattura elettronica"),
	EC("Estratto conto banca"),
	BO("Bolletta"),
	QI("Quietanza"),
	NC("Nota di credito"),
	ST("Stipendi");
	
	GiustificativoType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static GiustificativoType getByDescrizioneStatus (String status) {
		for (GiustificativoType e : GiustificativoType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
