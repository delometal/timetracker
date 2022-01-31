package com.perigea.tracker.timesheet.enums;

import java.time.DayOfWeek;

public enum EGiorno {

	LUN("LUNEDI", "L"),
	MAR("MARTEDI", "M"),
	MER("MERCOLEDI", "M"),
	GIO("GIOVEDI", "G"),
	VEN("VENERDI", "V"),
	SAB("SABATO", "S"),
	DOM("DOMENICA", "D");

	EGiorno(String description, String initial) {
		this.description = description;
		this.initial = initial;
	}
	
	private String description;
	private String initial;
	
	public static EGiorno getGiorno(DayOfWeek day) {
		if(day.equals(DayOfWeek.SATURDAY)) {
			return EGiorno.SAB;
		}else if(day.equals(DayOfWeek.SUNDAY)) {
			return EGiorno.DOM;
		}else if(day.equals(DayOfWeek.MONDAY)) {
			return EGiorno.LUN;
		}else if(day.equals(DayOfWeek.TUESDAY)) {
			return EGiorno.MAR;
		}else if(day.equals(DayOfWeek.WEDNESDAY)) {
			return EGiorno.MER;
		}else if(day.equals(DayOfWeek.THURSDAY)) {
			return EGiorno.GIO;
		}else {
			return EGiorno.VEN;
		}
	}

	public String getDescription() {
		return description;
	}

	public String getInitial() {
		return initial;
	}

	
	
}