package com.mcaulley.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mcaulley.app.entity.Inscripcion;

import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    List<Inscripcion> findByAlumna_IdAlumna(Integer idAlumna);
    
    List<Inscripcion> findByHorario_IdHorario(Integer idHorario);
    
    List<Inscripcion> findByEstadoPago_IdEstadoPago(Integer idEstadoPago);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.alumna.idAlumna = :idAlumna AND i.horario.activo = true")
    List<Inscripcion> findInscripcionesActivasPorAlumna(@Param("idAlumna") Integer idAlumna);
    
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.horario.idHorario = :idHorario")
    Long contarInscripcionesPorHorario(@Param("idHorario") Integer idHorario);
    
    @Query("SELECT i FROM Inscripcion i WHERE i.horario.curso.idCurso = :idCurso")
    List<Inscripcion> findInscripcionesPorCurso(@Param("idCurso") Integer idCurso);
    
    boolean existsByAlumna_IdAlumnaAndHorario_IdHorario(Integer idAlumna, Integer idHorario);
}