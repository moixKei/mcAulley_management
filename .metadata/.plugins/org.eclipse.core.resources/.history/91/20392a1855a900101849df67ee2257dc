package com.mcaulley.app.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mcaulley.app.entity.Horario;
import com.mcaulley.app.repository.HorarioRepository;
import com.mcaulley.app.service.HorarioService;

@Service
public class HorarioServiceImp implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Horario> listarTodos() {
        return horarioRepository.findAll();
    }

    @Override
    public Horario buscarPorId(Integer id) {
        return horarioRepository.findById(id).orElse(null);
    }

    @Override
    public Horario guardar(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public void eliminar(Integer id) {
        horarioRepository.deleteById(id);
    }
}
