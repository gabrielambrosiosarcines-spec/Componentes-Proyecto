package com.pe.idat.sistema_voto.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "candidatos")
@Data
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidato")
    private Integer idCandidato;

    @ManyToOne
    @JoinColumn(name = "id_eleccion")
    private Eleccion eleccion;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(name = "partido_politico", nullable = false, length = 100)
    private String partidoPolitico;

    @Column(name = "foto_url")
    private String fotoUrl;
}