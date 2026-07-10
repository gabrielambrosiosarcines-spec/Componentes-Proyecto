package com.pe.idat.sistema_voto.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_participacion")
@Data
public class RegistroParticipacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_eleccion")
    private Eleccion eleccion;

    @ManyToOne
    @JoinColumn(name = "id_votante")
    private Votante votante;

    @Column(name = "fecha_voto")
    private LocalDateTime fechaVoto;
}