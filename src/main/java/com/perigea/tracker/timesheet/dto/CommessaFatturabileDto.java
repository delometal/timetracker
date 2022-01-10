package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import com.perigea.tracker.timesheet.enumerator.TipoCommessaFatturabileType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommessaFatturabileDto extends CommessaDto {
	
	private String descrizioneCommessaPerigea;
	private String descrizioneCommessaCliente;
	private Date dataInizioCommessa;
	private Date dataFineCommessa;
	private TipoCommessaFatturabileType tipoCommessa;
	private Double importoCommessaInizialePresunto;
	private Double totaleEstensioni;
	private Double ordineInternoCorrente;
	private Double totaleOrdineClienteFormale;
	private Double totaleOrdine;
	private Double totaleRicaviDaInizioCommessa;
	private Double totaleRicaviDaInizioAnno;
	private Double totaleCostiDaInizioCommessa;
	private Double totaleCostiDaInizioAnno;
	private Double totaleFatturatoreDaInizioCommessa;
	private Double totaleFatturatoDaInizioAnno;
	private Double margineIniziale;
	private Double margineDaInizioCommessa;
	private Double margineDaInizioAnno;
	private Double percentualeAvanzamentoCosti;
	private Double percentualeAvanzamentoFatturazione;
	private Double percentualeSconto;
	private String responsabileCommerciale;
	
	public CommessaFatturabileDto() {
		super();
	}
}
