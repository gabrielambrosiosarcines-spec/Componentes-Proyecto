package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditoriaRepository
        extends JpaRepository<Auditoria, Integer> {
}