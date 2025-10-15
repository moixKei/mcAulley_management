package com.mcaulley.app.controller;

import com.mcaulley.app.service.CursoService;
import com.mcaulley.app.service.AlumnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CursoService cursoService;

    // Si tienes AlumnaService, descomenta esto:
    // @Autowired
    // private AlumnaService alumnaService;

    @GetMapping("/")
    public String home() {
        return "redirect:/alumnas";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Agregar estadísticas al modelo
        model.addAttribute("totalCursos", cursoService.listarTodos().size());
        model.addAttribute("cursosActivos", cursoService.contarCursosActivos());
        
        // Si tienes AlumnaService, agrega estas líneas:
        // model.addAttribute("totalAlumnas", alumnaService.listarTodos().size());
        // model.addAttribute("totalInscripciones", 0); // Ajusta según tu lógica
        
        return "dashboard";
    }

    @GetMapping("/menu")
    public String menuPrincipal() {
        return "menu";
    }
}