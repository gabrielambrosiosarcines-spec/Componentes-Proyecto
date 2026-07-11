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
    public Usuario buscar(Integer id){
        return repository.findById(id)
                .orElse(null);
    }

    public Usuario actualizar(
            Integer id,
            Usuario usuario){

        Usuario existente =
                repository.findById(id)
                        .orElse(null);

        if(existente != null){

            existente.setUsername(
                    usuario.getUsername());

            existente.setPassword(
                    usuario.getPassword());

            existente.setRol(
                    usuario.getRol());

            existente.setEstado(
                    usuario.getEstado());

            return repository.save(
                    existente);
        }

        return null;
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}