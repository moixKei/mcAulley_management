package com.mcaulley.app.service.impl;

import com.mcaulley.app.entity.EstadoCurso;
import com.mcaulley.app.repository.EstadoCursoRepository;
import com.mcaulley.app.service.EstadoCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstadoCursoServiceImp implements EstadoCursoService {

    @Autowired
    private EstadoCursoRepository estadoCursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstadoCurso> listarTodos() {
        return estadoCursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoCurso> buscarPorId(Integer id) {
        return estadoCursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoCurso> buscarPorNombre(String nombre) {
        return estadoCursoRepository.findByNombreEstado(nombre);
    }
}