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
}