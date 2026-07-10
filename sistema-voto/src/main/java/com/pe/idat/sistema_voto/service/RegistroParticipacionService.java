package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.RegistroParticipacion;
import com.pe.idat.sistema_voto.repository.RegistroParticipacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroParticipacionService {

    @Autowired
    private RegistroParticipacionRepository repository;

    public List<RegistroParticipacion> listar() {
        return repository.findAll();
    }

    public RegistroParticipacion guardar(RegistroParticipacion registro) {
        return repository.save(registro);
    }
}