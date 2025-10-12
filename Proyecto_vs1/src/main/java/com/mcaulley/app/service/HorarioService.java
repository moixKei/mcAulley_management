package com.mcaulley.app.service;

import java.util.List;
import com.mcaulley.app.entity.Horario;

public interface HorarioService {

    List<Horario> listarTodos();

    Horario buscarPorId(Integer id);

    Horario guardar(Horario horario);

    void eliminar(Integer id);
}
