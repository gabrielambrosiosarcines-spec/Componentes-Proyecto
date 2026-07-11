package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Voto;
import com.pe.idat.sistema_voto.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votos")
public class VotoController {

    @Autowired
    private VotoService service;

    @GetMapping
    public List<Voto> listar() {
        return service.listar();
    }

    @PostMapping
    public Voto guardar(@RequestBody Voto voto) {
        return service.guardar(voto);
    }
    @GetMapping("/{id}")
    public Voto buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Voto actualizar(
            @PathVariable Integer id,
            @RequestBody Voto voto){

        return service.actualizar(id, voto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}