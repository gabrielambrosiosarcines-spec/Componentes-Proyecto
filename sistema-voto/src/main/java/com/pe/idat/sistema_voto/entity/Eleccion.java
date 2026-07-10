package com.pe.idat.sistema_voto.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "elecciones")
@Data
public class Eleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eleccion")
    private Integer idEleccion;

    @Column(nullable = false, length = 100)
    private String titulo;

    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(nullable = false, length = 20)
    private String estado;
}