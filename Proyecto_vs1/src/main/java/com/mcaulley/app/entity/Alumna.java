package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "tb_alumnas",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "dni", name = "uk_alumna_dni"),
        @UniqueConstraint(columnNames = "correo", name = "uk_alumna_correo")
    }
)
@Getter
@Setter
@ToString
public class Alumna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumna")
    private Integer idAlumna; // Nombre más descriptivo

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "dni", nullable = false, unique = true, length = 15)
    private String dni;

    @Column(name = "correo", length = 100)
    private String correo;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDate fechaRegistro;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "activo")
    private Boolean activo = true;

    // Constructor por defecto
    public Alumna() {
        this.fechaRegistro = LocalDate.now();
        this.activo = true;
    }

    // Constructor con parámetros básicos
    public Alumna(String nombre, String apellido, String dni) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    // Método helper para nombre completo
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    // Método helper para validar datos básicos
    public boolean esValida() {
        return this.nombre != null && !this.nombre.trim().isEmpty() &&
               this.apellido != null && !this.apellido.trim().isEmpty() &&
               this.dni != null && !this.dni.trim().isEmpty();
    }

    // Callback antes de persistir
    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
        this.fechaActualizacion = LocalDateTime.now();
        if (this.activo == null) {
            this.activo = true;
        }
    }

    // Callback antes de actualizar
    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}