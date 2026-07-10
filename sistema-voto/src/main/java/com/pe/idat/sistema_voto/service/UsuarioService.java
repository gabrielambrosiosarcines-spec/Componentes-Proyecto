package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Usuario;
import com.pe.idat.sistema_voto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }
}