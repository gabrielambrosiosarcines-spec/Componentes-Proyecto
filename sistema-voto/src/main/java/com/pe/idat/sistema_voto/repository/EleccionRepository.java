package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.Eleccion;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EleccionRepository
        extends JpaRepository<Eleccion, Integer> {
}