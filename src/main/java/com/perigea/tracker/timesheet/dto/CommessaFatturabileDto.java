package com.perigea.tracker.timesheet.dto;

import java.util.Date;

import com.perigea.tracker.timesheet.enumerator.CommessaFatturabileType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommessaFatturabileDto extends BaseDto {
	
	private static final long serialVersionUID = 7646888920117640691L;
	
	private String codiceCommessa;
	private String descrizioneCommessaPerigea;
	private String descrizioneCommessaCliente;
	private Date dataInizioCommessa;
	private Date dataFineCommessa;
	private CommessaFatturabileType tipoCommessa;
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
	private CommessaDto commessa;

}
