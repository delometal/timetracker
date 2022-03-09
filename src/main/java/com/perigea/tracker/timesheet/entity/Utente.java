package com.perigea.tracker.timesheet.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.perigea.tracker.commons.annotations.NotNull;
import com.perigea.tracker.commons.enums.AnagraficaType;
import com.perigea.tracker.commons.enums.StatoUtenteType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utente")
@EqualsAndHashCode(callSuper = true, exclude = {"password", "avatar", "azienda", "ruoli", "cv"})
@ToString(exclude = {"password", "avatar", "azienda", "ruoli", "cv"})
public class Utente extends BaseEntity {

	private static final long serialVersionUID = -2342088709313716005L;

	@Id
	@Column(name = "codice_persona", nullable = false)
	private String codicePersona;

	@Column(name = "password", updatable = false)
	private String password;

	@Column(name = "username", unique = true)
	private String username;
	
	@Column(name = "avatar")
	private String avatar;

	@Column(name = "stato_utente")
	@Enumerated(EnumType.STRING)
	private StatoUtenteType stato;
	
	@Column(name = "account_locked")
	private boolean accountNonLocked;
	
	@Column(name = "account_expired")
	private boolean accountNonExpired;

	@Column(name = "credentials_expired")
	private boolean credentialsNonExpired;

	@Column(name = "codice_fiscale")
	private String codiceFiscale;
	
	@Column(name = "tipo", nullable = false)
	@Enumerated(EnumType.STRING)
	private AnagraficaType tipo;
	
	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cognome", nullable = false)
	private String cognome;

	@NotNull
	@Column(name = "mail_aziendale")
	private String mailAziendale;

	@Column(name = "mail_privata")
	private String mailPrivata;

	@Column(name = "cellulare")
	private String cellulare;
	
	@Column(name = "iban")
	private String iban;
	
	@Column(name = "luogo_di_nascita")
	private String luogoDiNascita;

	@Column(name = "data_di_nascita")
	private LocalDate dataDiNascita;
	
	@Column(name = "provincia_di_domicilio")
	private String provinciaDiDomicilio;

	@Column(name = "comune_di_domicilio")
	private String comuneDiDomicilio;

	@Column(name = "indirizzo_di_domicilio")
	private String indirizzoDiDomicilio;
	
	@Column(name = "provincia_di_residenza")
	private String provinciaDiResidenza;

	@Column(name = "comune_di_residenza")
	private String comuneDiResidenza;

	@Column(name = "indirizzo_di_residenza")
	private String indirizzoDiResidenza;

	@Column(name = "nome_contatto_emergenza")
	private String nomeContattoEmergenza;

	@Column(name = "cellulare_contatto_emergenza")
	private String cellulareContattoEmergenza;
	
	@Column(name = "codice_azienda", nullable = true, insertable=false, updatable=false)
	private String codiceAzienda;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(mappedBy = "utente", cascade = CascadeType.ALL, optional = true)
	private Personale personale;
	
	
	@ManyToOne(optional = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "codice_azienda")
	private Azienda azienda;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(mappedBy = "utente", cascade = CascadeType.ALL, optional = true)
	private CurriculumVitae cv;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "utente_ruolo", 
        joinColumns = { @JoinColumn(name = "codice_persona") }, 
        inverseJoinColumns = { @JoinColumn(name = "id") }
    )
	
	@Builder.Default
	private List<Ruolo> ruoli = new ArrayList<>();

	public void addRuolo(Ruolo ruolo) {
		this.ruoli.add(ruolo);
	}
	
	public void removeRuolo(Ruolo ruolo) {
		this.ruoli.remove(ruolo);
	}
	
}