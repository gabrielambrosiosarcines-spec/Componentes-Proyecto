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
}