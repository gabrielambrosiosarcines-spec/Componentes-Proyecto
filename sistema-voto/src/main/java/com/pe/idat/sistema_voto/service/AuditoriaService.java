package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Auditoria;
import com.pe.idat.sistema_voto.exception.RecursoNoEncontradoException;
import com.pe.idat.sistema_voto.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository repository;

    public List<Auditoria> listar() {
        return repository.findAll();
    }

    public Page<Auditoria> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Auditoria guardar(Auditoria auditoria) {
        return repository.save(auditoria);
    }
    public Auditoria buscar(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Registro de auditoría " + id + " no encontrado"));
    }

    public Auditoria actualizar(
            Integer id,
            Auditoria auditoria){

        Auditoria existente = buscar(id);

        existente.setUsuario(
                auditoria.getUsuario());

        existente.setAccion(
                auditoria.getAccion());

        existente.setProceso(
                auditoria.getProceso());

        existente.setDetalles(
                auditoria.getDetalles());

        existente.setFechaEvento(
                auditoria.getFechaEvento());

        return repository.save(existente);
    }

    public void eliminar(Integer id){
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Registro de auditoría " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}