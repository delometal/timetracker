package com.perigea.tracker.timesheet.enums;

public enum SceltaTredicesimaType {
	
	A("Annuale"),
	M("Mensile");

	private String descrizione;
	
	SceltaTredicesimaType(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

}
