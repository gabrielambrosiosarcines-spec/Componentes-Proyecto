package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.RegistroParticipacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroParticipacionRepository
        extends JpaRepository<RegistroParticipacion, Integer> {

    boolean existsByEleccion_IdEleccionAndVotante_IdVotante(
            Integer idEleccion, Integer idVotante);
}