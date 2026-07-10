package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.Votante;
import com.pe.idat.sistema_voto.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VotanteRepository
        extends JpaRepository<Votante, Integer> {
}