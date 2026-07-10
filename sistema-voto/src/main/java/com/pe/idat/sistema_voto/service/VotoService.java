package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Voto;
import com.pe.idat.sistema_voto.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotoService {

    @Autowired
    private VotoRepository repository;

    public List<Voto> listar() {
        return repository.findAll();
    }

    public Voto guardar(Voto voto) {
        return repository.save(voto);
    }
}