package com.perigea.tracker.timesheet.enums;

public enum RuoloType {
	M("MANAGEMENT"),
	A("AMMINISTRAZIONE"),
	H("HR"),
	D("DIPENDENTE"),
	C("CONSULENTE"),
	R("REFERENTE/CAPO PROGETTO"),
	X("ADMIN"),
	S("SALES");
	
	RuoloType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
}
