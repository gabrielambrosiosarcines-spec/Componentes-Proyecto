package com.pe.idat.sistema_voto.dto;

import lombok.Data;

@Data
public class CambiarPasswordRequest {
    private String passwordActual;
    private String passwordNuevo;
}
