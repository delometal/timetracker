package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrdineCommessaKey implements Serializable {

	private static final long serialVersionUID = 4070032003779891639L;

	@Column(name = "codice_commessa")
	private String codiceCommessa;

	@Column(name = "numero_ordine_cliente")
	private String numeroOrdineCliente;
	
	@Column(name = "partita_iva", nullable = false)
	private String partitaIva;

}
