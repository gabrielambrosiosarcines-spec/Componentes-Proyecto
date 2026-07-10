package com.pe.idat.sistema_voto.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "votos")
@Data
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voto")
    private Integer idVoto;

    @ManyToOne
    @JoinColumn(name = "id_eleccion")
    private Eleccion eleccion;

    @ManyToOne
    @JoinColumn(name = "id_candidato")
    private Candidato candidato;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}