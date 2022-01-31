package com.perigea.tracker.timesheet.enums;

public enum LivelloContrattoType {
	
	LIVELLO_1("1"), LIVELLO_2("2"), LIVELLO_3("3"), LIVELLO_4("4"), LIVELLO_5("5"), LIVELLO_5S("5S"), LIVELLO_6("6"),
	LIVELLO_7("7"), LIVELLO_7Q("7Q"), LIVELLO_D("D");

	private String id;

	LivelloContrattoType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
