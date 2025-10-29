package com.mcaulley.app.service;

import com.mcaulley.app.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    
    List<Usuario> listarTodos();
    Optional<Usuario> obtenerPorId(Integer id);
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuario(Integer id);
    boolean login(String username, String password);
    Optional<Usuario> buscarPorUsername(String username);
    boolean existeUsername(String username);
    boolean existeEmail(String email);
    List<Usuario> buscar(String busqueda);
    List<Usuario> obtenerPorRol(Usuario.RolUsuario rol);
}