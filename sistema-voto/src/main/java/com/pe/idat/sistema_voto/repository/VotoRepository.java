package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VotoRepository
        extends JpaRepository<Voto, Integer> {
}