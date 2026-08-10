package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.Votante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotanteRepository
        extends JpaRepository<Votante, Integer> {

    Optional<Votante> findByUsuario_Username(String username);
}