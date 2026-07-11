package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Eleccion;
import com.pe.idat.sistema_voto.service.EleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elecciones")
public class EleccionController {

    @Autowired
    private EleccionService service;

    @GetMapping
    public List<Eleccion> listar() {
        return service.listar();
    }

    @PostMapping
    public Eleccion guardar(@RequestBody Eleccion eleccion) {
        return service.guardar(eleccion);
    }
    @GetMapping("/{id}")
    public Eleccion buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Eleccion actualizar(
            @PathVariable Integer id,
            @RequestBody Eleccion eleccion){

        return service.actualizar(id, eleccion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}