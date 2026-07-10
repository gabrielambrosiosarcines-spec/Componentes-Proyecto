package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Candidato;
import com.pe.idat.sistema_voto.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService service;

    @GetMapping
    public List<Candidato> listar() {
        return service.listar();
    }

    @PostMapping
    public Candidato guardar(@RequestBody Candidato candidato) {
        return service.guardar(candidato);
    }
}