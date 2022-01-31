package com.perigea.tracker.timesheet.enums;

public enum CostoNotaSpeseType {

	AEREO, ALLOGGIO, TRASPORTI_E_CARBURANTE, PASTI, CONFERENZE_E_SEMINARI, KILOMETRI, RIMBORSO_KILOMETRICO, SPESE_VARIE;

	public static CostoNotaSpeseType getCostoNotaSpeseType(String string) {
		if(string.equalsIgnoreCase("AEREO")) {
			return CostoNotaSpeseType.AEREO;
		}else if(string.equalsIgnoreCase("ALLOGGIO")) {
			return CostoNotaSpeseType.ALLOGGIO;
		}else if(string.equalsIgnoreCase("TRASPORTI_E_CARBURANTE")) {
			return CostoNotaSpeseType.TRASPORTI_E_CARBURANTE;
		}else if(string.equalsIgnoreCase("PASTI")) {
			return CostoNotaSpeseType.PASTI;
		}else if (string.equalsIgnoreCase("CONFERENZE_E_SEMINARI")) {
			return CostoNotaSpeseType.CONFERENZE_E_SEMINARI;
		}else if(string.equalsIgnoreCase("KILOMETRI")) {
			return CostoNotaSpeseType.KILOMETRI;
		}else if (string.equalsIgnoreCase("RIMBORSO_KILOMETRICO")){
			return CostoNotaSpeseType.RIMBORSO_KILOMETRICO;
		} else {
			return CostoNotaSpeseType.SPESE_VARIE;
		}
	}
}
