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
    public Eleccion buscar(Integer id){
        return repository.findById(id).orElse(null);
    }

    public Eleccion actualizar(Integer id,
                               Eleccion eleccion){

        Eleccion existente =
                repository.findById(id).orElse(null);

        if(existente != null){

            existente.setTitulo(eleccion.getTitulo());
            existente.setDescripcion(eleccion.getDescripcion());
            existente.setFechaInicio(eleccion.getFechaInicio());
            existente.setFechaFin(eleccion.getFechaFin());
            existente.setEstado(eleccion.getEstado());

            return repository.save(existente);
        }

        return null;
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}