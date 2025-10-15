package com.mcaulley.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mcaulley.app.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    // Puedes agregar consultas personalizadas si lo necesitas
    Curso findByNombre(String nombre);
}
