package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Usuario;
import com.pe.idat.sistema_voto.exception.RecursoNoEncontradoException;
import com.pe.idat.sistema_voto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }
    public Usuario buscar(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario " + id + " no encontrado"));
    }

    public Usuario actualizar(
            Integer id,
            Usuario usuario){

        Usuario existente = buscar(id);

        existente.setUsername(
                usuario.getUsername());

        // Solo se re-cifra la contraseña si viene una nueva en la petición.
        if (usuario.getPassword() != null
                && !usuario.getPassword().isBlank()) {
            existente.setPassword(
                    passwordEncoder.encode(usuario.getPassword()));
        }

        existente.setRol(
                usuario.getRol());

        existente.setEstado(
                usuario.getEstado());

        return repository.save(
                existente);
    }

    public void eliminar(Integer id){
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    /**
     * Permite que cualquier usuario autenticado cambie su propia contraseña,
     * validando primero la contraseña actual. No requiere rol ADMIN.
     *
     * @return true si se actualizó, false si la contraseña actual no coincide.
     */
    public boolean cambiarPassword(String username, String passwordActual, String passwordNuevo) {

        Usuario usuario = repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            return false;
        }

        usuario.setPassword(passwordEncoder.encode(passwordNuevo));
        repository.save(usuario);
        return true;
    }
}