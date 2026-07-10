package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Auditoria;
import com.pe.idat.sistema_voto.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    @Autowired
    private AuditoriaService service;

    @GetMapping
    public List<Auditoria> listar() {
        return service.listar();
    }

    @PostMapping
    public Auditoria guardar(
            @RequestBody Auditoria auditoria) {
        return service.guardar(auditoria);
    }
}