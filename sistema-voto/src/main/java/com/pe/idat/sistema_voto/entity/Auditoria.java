package com.pe.idat.sistema_voto.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Data
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Integer idAuditoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(length = 100)
    private String accion;

    @Column(length = 50)
    private String proceso;

    private String detalles;

    @Column(name = "fecha_evento")
    private LocalDateTime fechaEvento;
}