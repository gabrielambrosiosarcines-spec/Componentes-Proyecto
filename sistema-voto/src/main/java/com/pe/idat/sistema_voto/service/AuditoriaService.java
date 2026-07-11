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
    public Auditoria buscar(Integer id){
        return repository.findById(id)
                .orElse(null);
    }

    public Auditoria actualizar(
            Integer id,
            Auditoria auditoria){

        Auditoria existente =
                repository.findById(id)
                        .orElse(null);

        if(existente != null){

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

        return null;
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}