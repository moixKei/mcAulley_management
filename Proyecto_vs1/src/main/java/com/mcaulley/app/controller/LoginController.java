package com.mcaulley.app.controller;

import com.mcaulley.app.entity.Usuario;
import com.mcaulley.app.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "usuarios/login";
    }
    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, 
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            if (usuarioService.login(username, password)) {
                Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(username);
                if (usuarioOpt.isPresent()) {
                    session.setAttribute("usuario", usuarioOpt.get());
                    redirectAttributes.addFlashAttribute("exito", "Bienvenido " + username);
                    return "redirect:/";
                }
            }
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en el sistema: " + e.getMessage());
        }
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("exito", "Sesión cerrada correctamente");
        return "redirect:/login";
    }
    
    @GetMapping("/")
    public String home(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        return "menu";
    }
}