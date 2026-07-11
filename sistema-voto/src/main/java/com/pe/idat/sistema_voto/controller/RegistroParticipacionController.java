package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.RegistroParticipacion;
import com.pe.idat.sistema_voto.service.RegistroParticipacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro-participacion")
public class RegistroParticipacionController {

    @Autowired
    private RegistroParticipacionService service;

    @GetMapping
    public List<RegistroParticipacion> listar() {
        return service.listar();
    }

    @PostMapping
    public RegistroParticipacion guardar(
            @RequestBody RegistroParticipacion registro) {
        return service.guardar(registro);
    }
    @GetMapping("/{id}")
    public RegistroParticipacion buscar(
            @PathVariable Integer id){

        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public RegistroParticipacion actualizar(
            @PathVariable Integer id,
            @RequestBody
            RegistroParticipacion registro){

        return service.actualizar(id,
                registro);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Integer id){

        service.eliminar(id);
    }
}