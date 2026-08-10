package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Auditoria;
import com.pe.idat.sistema_voto.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // Endpoint aparte, no reemplaza a /auditoria.
    // Ej: GET /auditoria/paginado?page=0&size=10&sort=fechaEvento,desc
    @GetMapping("/paginado")
    public Page<Auditoria> listarPaginado(
            @PageableDefault(size = 10, sort = "fechaEvento", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return service.listarPaginado(pageable);
    }

    @PostMapping
    public Auditoria guardar(
            @RequestBody Auditoria auditoria) {
        return service.guardar(auditoria);
    }
    @GetMapping("/{id}")
    public Auditoria buscar(
            @PathVariable Integer id){

        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Auditoria actualizar(
            @PathVariable Integer id,
            @RequestBody
            Auditoria auditoria){

        return service.actualizar(
                id, auditoria);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Integer id){

        service.eliminar(id);
    }
}