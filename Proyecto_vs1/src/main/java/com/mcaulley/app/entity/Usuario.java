package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", columnDefinition = "ENUM('admin', 'profesor', 'secretaria')")
    private RolUsuario rol = RolUsuario.secretaria;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Enum para los roles
    public enum RolUsuario {
        admin, profesor, secretaria
    }
    
    // Constructores
    public Usuario() {
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
        this.rol = RolUsuario.secretaria;
    }
    
    public Usuario(String username, String password, String email, RolUsuario rol) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }
    
    // Métodos helper
    public boolean esAdmin() {
        return this.rol == RolUsuario.admin;
    }
    
    public boolean esProfesor() {
        return this.rol == RolUsuario.profesor;
    }
    
    public boolean esSecretaria() {
        return this.rol == RolUsuario.secretaria;
    }
    
    public String getNombreRol() {
        return this.rol.name();
    }
    
    // Callbacks
    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.activo == null) {
            this.activo = true;
        }
        if (this.rol == null) {
            this.rol = RolUsuario.secretaria;
        }
    }
}