package com.mcaulley.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mcaulley.app.entity.Horario;
import com.mcaulley.app.entity.Curso;
import com.mcaulley.app.service.HorarioService;
import com.mcaulley.app.service.CursoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private CursoService cursoService;

    // ✅ Listar todos los horarios
    @GetMapping
    public String listarHorarios(Model model) {
        model.addAttribute("horarios", horarioService.listarTodos());
        return "horarios/lista";
    }

    // ✅ Mostrar formulario para nuevo horario
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("horario", new Horario());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"));
        return "horarios/form";
    }

    // ✅ Guardar nuevo horario
    @PostMapping("/guardar")
    public String guardarHorario(@ModelAttribute Horario horario,
                                RedirectAttributes redirectAttributes) {
        try {
            horarioService.guardar(horario);
            redirectAttributes.addFlashAttribute("success", 
                "Horario creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al crear horario: " + e.getMessage());
        }
        return "redirect:/horarios";
    }

    // ✅ Mostrar horarios por curso
    @GetMapping("/curso/{cursoId}")
    public String horariosPorCurso(@PathVariable Integer cursoId, Model model) {
        List<Horario> horarios = horarioService.buscarPorCurso(cursoId);
        model.addAttribute("horarios", horarios);
        Optional<Curso> curso = cursoService.buscarPorId(cursoId);
        curso.ifPresent(c -> model.addAttribute("curso", c));
        return "horarios/lista";
    }

    // ✅ Eliminar horario
    @PostMapping("/eliminar/{id}")
    public String eliminarHorario(@PathVariable Integer id,
                                 RedirectAttributes redirectAttributes) {
        try {
            horarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Horario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al eliminar horario: " + e.getMessage());
        }
        return "redirect:/horarios";
    }
}