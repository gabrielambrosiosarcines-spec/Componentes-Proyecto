package com.pe.idat.sistema_voto.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "votantes")
@Data
public class Votante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_votante")
    private Integer idVotante;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false, length = 8)
    private String dni;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, length = 100)
    private String correo;
}