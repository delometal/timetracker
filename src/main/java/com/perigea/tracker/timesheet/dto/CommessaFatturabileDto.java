package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import com.perigea.tracker.timesheet.enums.CommessaFatturabileType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommessaFatturabileDto extends BaseDto {
	
	private static final long serialVersionUID = 7646888920117640691L;
	
	private CommessaDto commessa;
	private CommessaFatturabileType tipoCommessaFatturabile;
	private String descrizioneCommessaCliente;
	private Date dataInizioCommessa;
	private Date dataFineCommessa;
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

}
