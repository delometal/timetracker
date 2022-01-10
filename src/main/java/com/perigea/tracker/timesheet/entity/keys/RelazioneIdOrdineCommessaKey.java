package com.perigea.tracker.timesheet.entity.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class RelazioneIdOrdineCommessaKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4070032003779891639L;

	@Column(name="codice_commessa")
	private String codiceCommessa;
	
	@Column(name="numero_ordine_cliente")
	private String numeroOrdineCliente;
	
	public String getCodiceCommessa() {
		return codiceCommessa;
	}
	public void setCodiceCommessa(String codiceCommessa) {
		this.codiceCommessa = codiceCommessa;
	}
	public String getNumeroOrdineCliente() {
		return numeroOrdineCliente;
	}
	public void setNumeroOrdineCliente(String numeroOrdineCliente) {
		this.numeroOrdineCliente = numeroOrdineCliente;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codiceCommessa, numeroOrdineCliente);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelazioneIdOrdineCommessaKey other = (RelazioneIdOrdineCommessaKey) obj;
		return Objects.equals(codiceCommessa, other.codiceCommessa)
				&& Objects.equals(numeroOrdineCliente, other.numeroOrdineCliente);
	}
	
	
}
