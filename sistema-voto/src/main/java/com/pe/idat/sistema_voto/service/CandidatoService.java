package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Candidato;
import com.pe.idat.sistema_voto.exception.RecursoNoEncontradoException;
import com.pe.idat.sistema_voto.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    public List<Candidato> listar() {
        return repository.findAll();
    }

    public Page<Candidato> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Candidato guardar(Candidato candidato) {
        return repository.save(candidato);
    }
    public Candidato buscar(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Candidato " + id + " no encontrado"));
    }

    public Candidato actualizar(Integer id,
                                Candidato candidato){

        Candidato existente = buscar(id);

        existente.setEleccion(candidato.getEleccion());
        existente.setNombre(candidato.getNombre());
        existente.setApellido(candidato.getApellido());
        existente.setPartidoPolitico(
                candidato.getPartidoPolitico());
        existente.setFotoUrl(candidato.getFotoUrl());

        return repository.save(existente);
    }

    public void eliminar(Integer id){
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Candidato " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}