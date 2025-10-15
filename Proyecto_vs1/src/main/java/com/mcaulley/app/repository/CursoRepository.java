package com.mcaulley.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mcaulley.app.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    Optional<Curso> findByNombre(String nombre);
    
    List<Curso> findByEstado_IdEstado(Integer idEstado);
    
    List<Curso> findByActivoTrue();
    
    List<Curso> findByActivoFalse();
    
    @Query("SELECT c FROM Curso c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Curso> buscarPorNombreConteniendo(@Param("nombre") String nombre);
    
    @Query("SELECT COUNT(c) FROM Curso c WHERE c.activo = true")
    Long contarCursosActivos();
    
    @Query("SELECT c FROM Curso c WHERE c.estado.idEstado = 1 ORDER BY c.nombre")
    List<Curso> findCursosActivosOrdenados();
}