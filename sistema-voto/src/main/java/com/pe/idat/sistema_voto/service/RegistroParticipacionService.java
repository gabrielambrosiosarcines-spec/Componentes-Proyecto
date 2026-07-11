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
    public RegistroParticipacion buscar(
            Integer id){

        return repository.findById(id)
                .orElse(null);
    }

    public RegistroParticipacion actualizar(
            Integer id,
            RegistroParticipacion registro){

        RegistroParticipacion existente =
                repository.findById(id)
                        .orElse(null);

        if(existente != null){

            existente.setEleccion(
                    registro.getEleccion());

            existente.setVotante(
                    registro.getVotante());

            existente.setFechaVoto(
                    registro.getFechaVoto());

            return repository.save(existente);
        }

        return null;
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}