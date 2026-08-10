package com.pe.idat.sistema_voto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 50)
    private String username;

    // WRITE_ONLY: se puede enviar en el body al crear/actualizar,
    // pero jamás se incluye en las respuestas JSON (ni siquiera hasheada).
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false, length = 30)
    private String rol;

    private Boolean estado;
}