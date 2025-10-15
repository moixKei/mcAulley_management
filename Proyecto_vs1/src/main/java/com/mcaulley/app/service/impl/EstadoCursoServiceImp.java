package com.mcaulley.app.service.impl;

import com.mcaulley.app.entity.EstadoCurso;
import com.mcaulley.app.repository.EstadoCursoRepository;
import com.mcaulley.app.service.EstadoCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoCursoServiceImp implements EstadoCursoService {

    @Autowired
    private EstadoCursoRepository estadoCursoRepository;

    @Override
    public List<EstadoCurso> listarTodos() {
        return estadoCursoRepository.findAll();
    }

    @Override
    public Optional<EstadoCurso> buscarPorId(Integer id) {
        return estadoCursoRepository.findById(id);
    }

    @Override
    public Optional<EstadoCurso> buscarPorNombreEstado(String nombreEstado) {
        return estadoCursoRepository.findByNombreEstado(nombreEstado);
    }

    @Override
    public EstadoCurso guardar(EstadoCurso estadoCurso) {
        return estadoCursoRepository.save(estadoCurso);
    }

    @Override
    public void eliminar(Integer id) {
        estadoCursoRepository.deleteById(id);
    }

}