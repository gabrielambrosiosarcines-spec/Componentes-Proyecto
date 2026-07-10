package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CandidatoRepository
        extends JpaRepository<Candidato, Integer> {
}