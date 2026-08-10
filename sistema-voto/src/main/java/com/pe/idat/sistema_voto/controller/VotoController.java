package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.dto.EmitirVotoRequest;
import com.pe.idat.sistema_voto.entity.Voto;
import com.pe.idat.sistema_voto.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    // Endpoint aparte del CRUD genérico: este es el que debe usar el votante
    // desde la papeleta. Toma la identidad del JWT, no del body, y valida
    // que no haya votado antes en esa elección.
    @PostMapping("/emitir")
    public ResponseEntity<?> emitirVoto(
            @RequestBody EmitirVotoRequest request,
            Authentication authentication) {

        try {
            Voto voto = service.emitirVoto(
                    authentication.getName(),
                    request.getIdEleccion(),
                    request.getIdCandidato());

            return ResponseEntity.status(HttpStatus.CREATED).body(voto);

        } catch (IllegalStateException e) {
            // Ya había participado en esa elección.
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Elección/candidato inexistente, o candidato de otra elección.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}