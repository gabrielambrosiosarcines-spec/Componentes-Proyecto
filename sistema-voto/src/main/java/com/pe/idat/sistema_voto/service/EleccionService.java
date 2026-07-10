package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Eleccion;
import com.pe.idat.sistema_voto.repository.EleccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleccionService {

    @Autowired
    private EleccionRepository repository;

    public List<Eleccion> listar() {
        return repository.findAll();
    }

    public Eleccion guardar(Eleccion eleccion) {
        return repository.save(eleccion);
    }
}