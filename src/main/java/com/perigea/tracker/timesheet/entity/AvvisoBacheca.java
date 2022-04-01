package com.perigea.tracker.timesheet.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name="bacheca")
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
    private Boolean fissato;

    @Column(name="data_evento")
    private LocalDate dataEvento;
}
