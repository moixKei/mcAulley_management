package com.mcaulley.app.service.impl;

import com.mcaulley.app.entity.Usuario;
import com.mcaulley.app.repository.UsuarioRepository;
import com.mcaulley.app.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findByActivoTrue();
    }

    @Override
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Si es un usuario nuevo, verificar que el username no exista
        if (usuario.getIdUsuario() == null && usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        // Verificar email único si se proporciona
        if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
            if (usuarioExistente.isPresent() && 
                !usuarioExistente.get().getIdUsuario().equals(usuario.getIdUsuario())) {
                throw new RuntimeException("El email ya está registrado");
            }
        }
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Integer id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        });
    }

    @Override
    public boolean login(String username, String password) {
        // Buscar usuario por username y password (sin encriptación por ahora)
        Optional<Usuario> usuario = usuarioRepository.findByUsernameAndPassword(username, password);
        return usuario.isPresent() && usuario.get().getActivo();
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public List<Usuario> buscar(String busqueda) {
        return usuarioRepository.buscarPorUsernameOEmail(busqueda);
    }

    @Override
    public List<Usuario> obtenerPorRol(Usuario.RolUsuario rol) {
        return usuarioRepository.findByRol(rol);
    }
}