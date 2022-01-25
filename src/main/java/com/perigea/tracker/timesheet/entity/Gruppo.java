package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "gruppo")
public class Gruppo implements Serializable {

	private static final long serialVersionUID = -6564545853025079938L;

	@Id
	@Column(name = "id_gruppo", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descrizione")
	private String descrizione;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "gruppo_contatto", 
        joinColumns = { @JoinColumn(name = "id_gruppo") }, 
        inverseJoinColumns = { @JoinColumn(name = "id_contatto") }
    )
	private List<Contatto> contatti = new ArrayList<>();

}