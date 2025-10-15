package com.mcaulley.app.service;

import com.mcaulley.app.entity.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listarTodos();
    
    Optional<Curso> buscarPorId(Integer id);
    
    Curso guardar(Curso curso);
    
    Curso actualizar(Integer id, Curso curso);
    
    void eliminar(Integer id);
    
    List<Curso> buscarPorNombre(String nombre);
    
    List<Curso> buscarCursosActivos();
    
    List<Curso> buscarCursosInactivos();
    
    List<Curso> buscarPorEstado(Integer idEstado);
    
    Long contarCursosActivos();
    
    boolean existeCursoConNombre(String nombre);
    
    void activarCurso(Integer id);
    
    void desactivarCurso(Integer id);
    
    boolean validarCurso(Curso curso);
}