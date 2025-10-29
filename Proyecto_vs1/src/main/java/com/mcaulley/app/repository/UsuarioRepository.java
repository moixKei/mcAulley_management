package com.mcaulley.app.repository;

import com.mcaulley.app.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Métodos de consulta derivados
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByActivoTrue();
    List<Usuario> findByRol(Usuario.RolUsuario rol);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // Consulta personalizada para búsqueda
    @Query("SELECT u FROM Usuario u WHERE u.activo = true AND " +
           "(LOWER(u.username) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :busqueda, '%')))")
    List<Usuario> buscarPorUsernameOEmail(@Param("busqueda") String busqueda);
    
    // Método para login (sin encriptación por ahora)
    @Query("SELECT u FROM Usuario u WHERE u.username = :username AND u.password = :password AND u.activo = true")
    Optional<Usuario> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}