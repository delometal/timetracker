package com.perigea.tracker.timesheet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="bacheca")
@NoArgsConstructor
@AllArgsConstructor
public class AvvisoBacheca extends BaseEntity{

    private static final long serialVersionUID = -211063501319745848L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column(name="tipo_avviso")
    private String tipo;

    @Column
    private Boolean pinned;

    @Column(name="data_evento")
    private Date dataEvento;
}
