package com.mcaulley.app.controller;

import com.mcaulley.app.entity.Usuario;
import com.mcaulley.app.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", "Gestión de Usuarios");
        return "usuarios/lista";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("roles", Arrays.asList(Usuario.RolUsuario.values()));
        return "usuarios/form";
    }
    
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, 
                               RedirectAttributes redirectAttributes) {
        try {
            usuarioService.guardarUsuario(usuario);
            redirectAttributes.addFlashAttribute("exito", "Usuario guardado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el usuario: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        usuarioService.obtenerPorId(id).ifPresent(usuario -> {
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", "Editar Usuario");
            model.addAttribute("roles", Arrays.asList(Usuario.RolUsuario.values()));
        });
        return "usuarios/form";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminarUsuario(id);
            redirectAttributes.addFlashAttribute("exito", "Usuario eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario");
        }
        return "redirect:/usuarios";
    }
    
    // Controlador para login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "usuarios/login";
    }
    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, 
                              @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        if (usuarioService.login(username, password)) {
            redirectAttributes.addFlashAttribute("exito", "Login exitoso");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
            return "redirect:/usuarios/login";
        }
    }
}