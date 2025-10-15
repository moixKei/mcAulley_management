package com.mcaulley.app.service;

import com.mcaulley.app.entity.Inscripcion;
import java.util.List;
import java.util.Optional;

public interface InscripcionService {

    List<Inscripcion> listarTodas();
    
    Optional<Inscripcion> buscarPorId(Integer id);
    
    Inscripcion guardar(Inscripcion inscripcion);
    
    Inscripcion actualizar(Integer id, Inscripcion inscripcion);
    
    void eliminar(Integer id);
    
    List<Inscripcion> buscarPorAlumna(Integer idAlumna);
    
    List<Inscripcion> buscarPorHorario(Integer idHorario);
    
    List<Inscripcion> buscarPorEstadoPago(Integer idEstadoPago);
    
    List<Inscripcion> buscarInscripcionesActivasPorAlumna(Integer idAlumna);
    
    void actualizarEstadoPago(Integer idInscripcion, Integer idEstadoPago);
    
    boolean existeInscripcion(Integer idAlumna, Integer idHorario);
    
    Long contarInscripcionesPorHorario(Integer idHorario);
    
    boolean validarInscripcion(Inscripcion inscripcion);
}