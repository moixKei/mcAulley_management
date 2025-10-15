package com.mcaulley.app.repository;

import com.mcaulley.app.entity.EstadoCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoCursoRepository extends JpaRepository<EstadoCurso, Integer> {
    Optional<EstadoCurso> findByNombreEstado(String nombreEstado);
}