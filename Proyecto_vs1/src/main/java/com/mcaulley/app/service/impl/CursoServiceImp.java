package com.mcaulley.app.service.impl;

import com.mcaulley.app.entity.Curso;
import com.mcaulley.app.entity.EstadoCurso;
import com.mcaulley.app.repository.CursoRepository;
import com.mcaulley.app.service.CursoService;
import com.mcaulley.app.service.EstadoCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CursoServiceImp implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EstadoCursoService estadoCursoService;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return cursoRepository.findById(id);
    }

    @Override
    public Curso guardar(Curso curso) {
        if (!validarCurso(curso)) {
            throw new IllegalArgumentException("Los datos del curso no son válidos");
        }
        return cursoRepository.save(curso);
    }

    @Override
    public Curso actualizar(Integer id, Curso cursoActualizado) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de curso inválido");
        }
        
        if (!validarCurso(cursoActualizado)) {
            throw new IllegalArgumentException("Los datos del curso no son válidos");
        }
        
        return cursoRepository.findById(id)
                .map(cursoExistente -> {
                    cursoExistente.setNombre(cursoActualizado.getNombre());
                    cursoExistente.setDescripcion(cursoActualizado.getDescripcion());
                    cursoExistente.setDuracionHoras(cursoActualizado.getDuracionHoras());
                    cursoExistente.setEstado(cursoActualizado.getEstado());
                    return cursoRepository.save(cursoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }

    @Override
    public void eliminar(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de curso inválido");
        }
        
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        
        cursoRepository.delete(curso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return List.of();
        }
        return cursoRepository.buscarPorNombreConteniendo(nombre.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> buscarCursosActivos() {
        return cursoRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> buscarCursosInactivos() {
        return cursoRepository.findByActivoFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> buscarPorEstado(Integer idEstado) {
        if (idEstado == null) {
            return List.of();
        }
        return cursoRepository.findByEstado_IdEstado(idEstado);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarCursosActivos() {
        return cursoRepository.contarCursosActivos();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCursoConNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return cursoRepository.findByNombre(nombre.trim()).isPresent();
    }

    @Override
    public void activarCurso(Integer id) {
        cambiarEstadoActivoCurso(id, true);
    }

    @Override
    public void desactivarCurso(Integer id) {
        cambiarEstadoActivoCurso(id, false);
    }

    @Override
    public boolean validarCurso(Curso curso) {
        return curso != null && 
               curso.getNombre() != null && !curso.getNombre().trim().isEmpty() &&
               curso.getEstado() != null;
    }

    // CORRECCIÓN: Cambiar estado activo, no el estado del curso
    private void cambiarEstadoActivoCurso(Integer id, boolean activo) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        
        curso.setActivo(activo);
        cursoRepository.save(curso);
    }

    // Método adicional para cambiar el estado del curso (activo/inactivo)
    public void cambiarEstadoCurso(Integer idCurso, Integer idEstado) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        
        EstadoCurso estado = estadoCursoService.buscarPorId(idEstado)
                .orElseThrow(() -> new RuntimeException("Estado de curso no encontrado"));
        
        curso.setEstado(estado);
        cursoRepository.save(curso);
    }
}