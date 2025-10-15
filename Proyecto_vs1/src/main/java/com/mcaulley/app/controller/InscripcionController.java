package com.mcaulley.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mcaulley.app.entity.Inscripcion;
import com.mcaulley.app.entity.Alumna;
import com.mcaulley.app.entity.Horario;
import com.mcaulley.app.entity.EstadoPago;
import com.mcaulley.app.service.InscripcionService;
import com.mcaulley.app.service.AlumnaService;
import com.mcaulley.app.service.HorarioService;
import com.mcaulley.app.service.EstadoPagoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private AlumnaService alumnaService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private EstadoPagoService estadoPagoService;

    // ✅ Listar todas las inscripciones
    @GetMapping
    public String listarInscripciones(Model model) {
        model.addAttribute("inscripciones", inscripcionService.listarTodas());
        return "inscripciones/lista";
    }

    // ✅ Mostrar formulario para nueva inscripción
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("inscripcion", new Inscripcion());
        model.addAttribute("alumnas", alumnaService.listarTodas());
        model.addAttribute("horarios", horarioService.listarTodos());
        model.addAttribute("estadosPago", estadoPagoService.listarTodos());
        return "inscripciones/form";
    }

    // ✅ Guardar nueva inscripción
    @PostMapping("/guardar")
    public String guardarInscripcion(@ModelAttribute Inscripcion inscripcion,
                                    RedirectAttributes redirectAttributes) {
        try {
            // Verificar cupos disponibles
            Horario horario = horarioService.buscarPorId(inscripcion.getHorario().getIdHorario())
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
            
            if (horario.getCuposDisponibles() <= 0) {
                redirectAttributes.addFlashAttribute("error", 
                    "No hay cupos disponibles para este horario");
                return "redirect:/inscripciones/nuevo";
            }

            inscripcionService.guardar(inscripcion);
            
            // Actualizar cupos disponibles
            horario.setCuposDisponibles(horario.getCuposDisponibles() - 1);
            horarioService.guardar(horario);
            
            redirectAttributes.addFlashAttribute("success", 
                "Inscripción realizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al realizar inscripción: " + e.getMessage());
        }
        return "redirect:/inscripciones";
    }

    // ✅ Ver inscripciones por alumna
    @GetMapping("/alumna/{alumnaId}")
    public String inscripcionesPorAlumna(@PathVariable Integer alumnaId, Model model) {
        List<Inscripcion> inscripciones = inscripcionService.buscarPorAlumna(alumnaId);
        model.addAttribute("inscripciones", inscripciones);
        Optional<Alumna> alumna = alumnaService.buscarPorId(alumnaId);
        alumna.ifPresent(a -> model.addAttribute("alumna", a));
        return "inscripciones/lista";
    }

    // ✅ Ver inscripciones por horario
    @GetMapping("/horario/{horarioId}")
    public String inscripcionesPorHorario(@PathVariable Integer horarioId, Model model) {
        List<Inscripcion> inscripciones = inscripcionService.buscarPorHorario(horarioId);
        model.addAttribute("inscripciones", inscripciones);
        Optional<Horario> horario = horarioService.buscarPorId(horarioId);
        horario.ifPresent(h -> model.addAttribute("horario", h));
        return "inscripciones/lista";
    }

    // ✅ Actualizar estado de pago
    @PostMapping("/actualizar-pago/{id}")
    public String actualizarEstadoPago(@PathVariable Integer id,
                                      @RequestParam Integer idEstadoPago,
                                      RedirectAttributes redirectAttributes) {
        try {
            inscripcionService.actualizarEstadoPago(id, idEstadoPago);
            redirectAttributes.addFlashAttribute("success", 
                "Estado de pago actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al actualizar estado de pago: " + e.getMessage());
        }
        return "redirect:/inscripciones";
    }

    // ✅ Eliminar inscripción
    @PostMapping("/eliminar/{id}")
    public String eliminarInscripcion(@PathVariable Integer id,
                                     RedirectAttributes redirectAttributes) {
        try {
            inscripcionService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", 
                "Inscripción eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al eliminar inscripción: " + e.getMessage());
        }
        return "redirect:/inscripciones";
    }
}