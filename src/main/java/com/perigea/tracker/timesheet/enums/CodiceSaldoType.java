package com.perigea.tracker.timesheet.enums;

public enum CodiceSaldoType {

	CA("Cassa"),
	AG("Credit Agricole"),
	FI("Fineco"),
	CP("Carta prepagata");
	
	CodiceSaldoType(String descrizione) {
		this.descrizione = descrizione;
	}
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}
	
	public static CodiceSaldoType getByDescrizioneStatus (String status) {
		for (CodiceSaldoType e : CodiceSaldoType.values()) {
			if(e.getDescrizione().equalsIgnoreCase(status)) {
				return e;
			}
		}
		return null;
	}
}
