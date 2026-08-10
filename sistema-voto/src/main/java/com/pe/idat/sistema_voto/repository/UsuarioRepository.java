package com.pe.idat.sistema_voto.repository;

import com.pe.idat.sistema_voto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);
}