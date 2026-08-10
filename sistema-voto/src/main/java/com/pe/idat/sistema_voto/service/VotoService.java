package com.pe.idat.sistema_voto.service;

import com.pe.idat.sistema_voto.entity.Candidato;
import com.pe.idat.sistema_voto.entity.Eleccion;
import com.pe.idat.sistema_voto.entity.RegistroParticipacion;
import com.pe.idat.sistema_voto.entity.Votante;
import com.pe.idat.sistema_voto.entity.Voto;
import com.pe.idat.sistema_voto.exception.RecursoNoEncontradoException;
import com.pe.idat.sistema_voto.repository.CandidatoRepository;
import com.pe.idat.sistema_voto.repository.EleccionRepository;
import com.pe.idat.sistema_voto.repository.RegistroParticipacionRepository;
import com.pe.idat.sistema_voto.repository.VotanteRepository;
import com.pe.idat.sistema_voto.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VotoService {

    @Autowired
    private VotoRepository repository;

    @Autowired
    private VotanteRepository votanteRepository;

    @Autowired
    private EleccionRepository eleccionRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private RegistroParticipacionRepository registroParticipacionRepository;

    public List<Voto> listar() {
        return repository.findAll();
    }

    public Voto guardar(Voto voto) {
        return repository.save(voto);
    }
    public Voto buscar(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Voto " + id + " no encontrado"));
    }

    public Voto actualizar(Integer id,
                           Voto voto){

        Voto existente = buscar(id);

        existente.setEleccion(voto.getEleccion());
        existente.setCandidato(voto.getCandidato());
        existente.setFechaRegistro(
                voto.getFechaRegistro());

        return repository.save(existente);
    }

    public void eliminar(Integer id){
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Voto " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    /**
     * Emite un voto para el usuario autenticado (identificado por su username del JWT).
     * - El voto (Voto) queda anónimo: no se guarda relación con el votante.
     * - La trazabilidad de "quién participó" queda aparte, en RegistroParticipacion.
     * - Impide que el mismo votante participe dos veces en la misma elección.
     *
     * Todo ocurre en una sola transacción: si algo falla, no queda ni el voto
     * ni el registro de participación a medias.
     */
    @Transactional
    public Voto emitirVoto(String username, Integer idEleccion, Integer idCandidato) {

        Votante votante = votanteRepository.findByUsuario_Username(username)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El usuario autenticado no tiene un votante asociado"));

        Eleccion eleccion = eleccionRepository.findById(idEleccion)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La elección indicada no existe"));

        Candidato candidato = candidatoRepository.findById(idCandidato)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El candidato indicado no existe"));

        if (candidato.getEleccion() == null
                || !candidato.getEleccion().getIdEleccion().equals(idEleccion)) {
            throw new IllegalArgumentException(
                    "El candidato no pertenece a la elección indicada");
        }

        boolean yaParticipo = registroParticipacionRepository
                .existsByEleccion_IdEleccionAndVotante_IdVotante(
                        idEleccion, votante.getIdVotante());

        if (yaParticipo) {
            throw new IllegalStateException(
                    "El votante ya participó en esta elección");
        }

        Voto voto = new Voto();
        voto.setEleccion(eleccion);
        voto.setCandidato(candidato);
        voto.setFechaRegistro(LocalDateTime.now());
        repository.save(voto);

        RegistroParticipacion registro = new RegistroParticipacion();
        registro.setEleccion(eleccion);
        registro.setVotante(votante);
        registro.setFechaVoto(LocalDateTime.now());
        registroParticipacionRepository.save(registro);

        return voto;
    }
}