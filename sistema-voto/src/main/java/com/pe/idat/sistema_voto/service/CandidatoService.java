package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Candidato;
import com.pe.idat.sistema_voto.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    public List<Candidato> listar() {
        return repository.findAll();
    }

    public Candidato guardar(Candidato candidato) {
        return repository.save(candidato);
    }
    public Candidato buscar(Integer id){
        return repository.findById(id).orElse(null);
    }

    public Candidato actualizar(Integer id,
                                Candidato candidato){

        Candidato existente =
                repository.findById(id).orElse(null);

        if(existente != null){

            existente.setEleccion(candidato.getEleccion());
            existente.setNombre(candidato.getNombre());
            existente.setApellido(candidato.getApellido());
            existente.setPartidoPolitico(
                    candidato.getPartidoPolitico());
            existente.setFotoUrl(candidato.getFotoUrl());

            return repository.save(existente);
        }

        return null;
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}