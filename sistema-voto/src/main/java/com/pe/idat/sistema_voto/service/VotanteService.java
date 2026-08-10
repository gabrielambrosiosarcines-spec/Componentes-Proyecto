package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Votante;
import com.pe.idat.sistema_voto.exception.RecursoNoEncontradoException;
import com.pe.idat.sistema_voto.repository.VotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotanteService {

    @Autowired
    private VotanteRepository repository;

    public List<Votante> listar() {
        return repository.findAll();
    }

    public Page<Votante> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Votante guardar(Votante votante) {
        return repository.save(votante);
    }
    public Votante buscar(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Votante " + id + " no encontrado"));
    }

    public Votante actualizar(Integer id, Votante votante){

        Votante existente = buscar(id);

        existente.setUsuario(votante.getUsuario());
        existente.setDni(votante.getDni());
        existente.setNombre(votante.getNombre());
        existente.setApellido(votante.getApellido());
        existente.setCorreo(votante.getCorreo());

        return repository.save(existente);
    }

    public void eliminar(Integer id){
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Votante " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}