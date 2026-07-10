package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Auditoria;
import com.pe.idat.sistema_voto.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository repository;

    public List<Auditoria> listar() {
        return repository.findAll();
    }

    public Auditoria guardar(Auditoria auditoria) {
        return repository.save(auditoria);
    }
}