package com.mcaulley.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mcaulley.app.entity.Horario;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    List<Horario> findByCurso_IdCurso(Integer idCurso);
    
    List<Horario> findByDiaSemana(String diaSemana);
    
    List<Horario> findByCuposDisponiblesGreaterThan(Integer cupos);
    
    List<Horario> findByActivoTrue();
    
    @Query("SELECT h FROM Horario h WHERE h.curso.idCurso = :idCurso AND h.activo = true")
    List<Horario> findHorariosActivosPorCurso(@Param("idCurso") Integer idCurso);
    
    @Query("SELECT h FROM Horario h WHERE h.cuposDisponibles > 0 AND h.activo = true")
    List<Horario> findHorariosConCuposDisponibles();
    
    @Query("SELECT COUNT(h) FROM Horario h WHERE h.curso.idCurso = :idCurso")
    Long contarHorariosPorCurso(@Param("idCurso") Integer idCurso);
}