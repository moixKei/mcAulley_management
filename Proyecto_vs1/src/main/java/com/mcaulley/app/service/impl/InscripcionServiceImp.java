package com.mcaulley.app.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mcaulley.app.entity.Inscripcion;
import com.mcaulley.app.repository.InscripcionRepository;
import com.mcaulley.app.service.InscripcionService;

@Service
public class InscripcionServiceImp implements InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Override
    public List<Inscripcion> listarTodas() {
        return inscripcionRepository.findAll();
    }

    @Override
    public Inscripcion buscarPorId(Integer id) {
        return inscripcionRepository.findById(id).orElse(null);
    }

    @Override
    public Inscripcion guardar(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public void eliminar(Integer id) {
        inscripcionRepository.deleteById(id);
    }
}
