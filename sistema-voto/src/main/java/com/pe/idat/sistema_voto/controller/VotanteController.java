package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.entity.Votante;
import com.pe.idat.sistema_voto.service.VotanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // Endpoint aparte, no reemplaza a /votantes.
    // Ej: GET /votantes/paginado?page=0&size=10&sort=apellido,asc
    @GetMapping("/paginado")
    public Page<Votante> listarPaginado(
            @PageableDefault(size = 10, sort = "idVotante", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return service.listarPaginado(pageable);
    }

    @PostMapping
    public Votante guardar(@RequestBody Votante votante) {
        return service.guardar(votante);
    }
    @GetMapping("/{id}")
    public Votante buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Votante actualizar(
            @PathVariable Integer id,
            @RequestBody Votante votante){

        return service.actualizar(id, votante);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}