package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Votante;
import com.pe.idat.sistema_voto.service.VotanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votantes")
public class VotanteController {

    @Autowired
    private VotanteService service;

    @GetMapping
    public List<Votante> listar() {
        return service.listar();
    }

    @PostMapping
    public Votante guardar(@RequestBody Votante votante) {
        return service.guardar(votante);
    }
}