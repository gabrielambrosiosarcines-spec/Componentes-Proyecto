package com.pe.idat.sistema_voto.dto;

import lombok.Data;

@Data
public class EmitirVotoRequest {
    private Integer idEleccion;
    private Integer idCandidato;
}
