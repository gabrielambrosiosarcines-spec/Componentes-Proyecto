package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Usuario;
import com.pe.idat.sistema_voto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return service.guardar(usuario);
    }
    @GetMapping("/{id}")
    public Usuario buscar(
            @PathVariable Integer id){

        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuario){

        return service.actualizar(
                id, usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Integer id){

        service.eliminar(id);
    }
}
