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
    public Votante buscar(Integer id){
        return repository.findById(id).orElse(null);
    }

    public Votante actualizar(Integer id, Votante votante){

        Votante existente =
                repository.findById(id).orElse(null);

        if(existente != null){

            existente.setUsuario(votante.getUsuario());
            existente.setDni(votante.getDni());
            existente.setNombre(votante.getNombre());
            existente.setApellido(votante.getApellido());
            existente.setCorreo(votante.getCorreo());

            return repository.save(existente);
        }

        return null;
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}