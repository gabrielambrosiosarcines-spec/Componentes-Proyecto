package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Votante;
import com.pe.idat.sistema_voto.repository.VotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotanteService {

    @Autowired
    private VotanteRepository repository;

    public List<Votante> listar() {
        return repository.findAll();
    }

    public Votante guardar(Votante votante) {
        return repository.save(votante);
    }
}