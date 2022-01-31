package com.perigea.tracker.timesheet.enums;

public enum JobTitle {
	
	JUNIOR_PROF("Junior Prof."),
	PROFESSIONAL("Professional"),
	SENIOR_PROF("Senior Prof."),
	EXPERT("Expert"),
	MANAGER("Manager"),
	SENIOR_MANAGER("Senior Manager"),
	DIRECTOR("Director");

	private String descrizione;
	
	JobTitle(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

}
