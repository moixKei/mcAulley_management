package com.mcaulley.app.service;

import com.mcaulley.app.entity.Horario;
import java.util.List;
import java.util.Optional;

public interface HorarioService {

    List<Horario> listarTodos();
    
    Optional<Horario> buscarPorId(Integer id);
    
    Horario guardar(Horario horario);
    
    Horario actualizar(Integer id, Horario horario);
    
    void eliminar(Integer id);
    
    List<Horario> buscarPorCurso(Integer idCurso);
    
    List<Horario> buscarPorDiaSemana(String diaSemana);
    
    List<Horario> buscarHorariosConCupos();
    
    List<Horario> buscarHorariosActivos();
    
    void reducirCupo(Integer idHorario);
    
    void aumentarCupo(Integer idHorario);
    
    boolean validarHorario(Horario horario);
}