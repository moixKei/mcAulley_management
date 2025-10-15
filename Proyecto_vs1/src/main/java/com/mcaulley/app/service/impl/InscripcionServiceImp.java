package com.mcaulley.app.service.impl;

import com.mcaulley.app.entity.Inscripcion;
import com.mcaulley.app.entity.EstadoPago;
import com.mcaulley.app.repository.InscripcionRepository;
import com.mcaulley.app.service.InscripcionService;
import com.mcaulley.app.service.EstadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InscripcionServiceImp implements InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private EstadoPagoService estadoPagoService;

    @Override
    @Transactional(readOnly = true)
    public List<Inscripcion> listarTodas() {
        return inscripcionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inscripcion> buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return inscripcionRepository.findById(id);
    }

    @Override
    public Inscripcion guardar(Inscripcion inscripcion) {
        if (!validarInscripcion(inscripcion)) {
            throw new IllegalArgumentException("Los datos de la inscripción no son válidos");
        }
        
        // Verificar si ya existe la inscripción
        if (existeInscripcion(inscripcion.getAlumna().getIdAlumna(), inscripcion.getHorario().getIdHorario())) {
            throw new IllegalArgumentException("La alumna ya está inscrita en este horario");
        }
        
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public Inscripcion actualizar(Integer id, Inscripcion inscripcionActualizada) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de inscripción inválido");
        }
        
        if (!validarInscripcion(inscripcionActualizada)) {
            throw new IllegalArgumentException("Los datos de la inscripción no son válidos");
        }
        
        return inscripcionRepository.findById(id)
                .map(inscripcionExistente -> {
                    inscripcionExistente.setAlumna(inscripcionActualizada.getAlumna());
                    inscripcionExistente.setHorario(inscripcionActualizada.getHorario());
                    inscripcionExistente.setEstadoPago(inscripcionActualizada.getEstadoPago());
                    inscripcionExistente.setQrCodigo(inscripcionActualizada.getQrCodigo());
                    return inscripcionRepository.save(inscripcionExistente);
                })
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + id));
    }

    @Override
    public void eliminar(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de inscripción inválido");
        }
        
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + id));
        
        inscripcionRepository.delete(inscripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inscripcion> buscarPorAlumna(Integer idAlumna) {
        if (idAlumna == null || idAlumna <= 0) {
            return List.of();
        }
        return inscripcionRepository.findByAlumna_IdAlumna(idAlumna);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inscripcion> buscarPorHorario(Integer idHorario) {
        if (idHorario == null || idHorario <= 0) {
            return List.of();
        }
        return inscripcionRepository.findByHorario_IdHorario(idHorario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inscripcion> buscarPorEstadoPago(Integer idEstadoPago) {
        if (idEstadoPago == null) {
            return List.of();
        }
        return inscripcionRepository.findByEstadoPago_IdEstadoPago(idEstadoPago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inscripcion> buscarInscripcionesActivasPorAlumna(Integer idAlumna) {
        if (idAlumna == null || idAlumna <= 0) {
            return List.of();
        }
        return inscripcionRepository.findInscripcionesActivasPorAlumna(idAlumna);
    }

    @Override
    public void actualizarEstadoPago(Integer idInscripcion, Integer idEstadoPago) {
        Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
        
        EstadoPago estadoPago = estadoPagoService.buscarPorId(idEstadoPago)
                .orElseThrow(() -> new RuntimeException("Estado de pago no encontrado"));
        
        inscripcion.setEstadoPago(estadoPago);
        inscripcionRepository.save(inscripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeInscripcion(Integer idAlumna, Integer idHorario) {
        return inscripcionRepository.existsByAlumna_IdAlumnaAndHorario_IdHorario(idAlumna, idHorario);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarInscripcionesPorHorario(Integer idHorario) {
        return inscripcionRepository.contarInscripcionesPorHorario(idHorario);
    }

    @Override
    public boolean validarInscripcion(Inscripcion inscripcion) {
        return inscripcion != null && 
               inscripcion.getAlumna() != null &&
               inscripcion.getHorario() != null &&
               inscripcion.getEstadoPago() != null;
    }
}