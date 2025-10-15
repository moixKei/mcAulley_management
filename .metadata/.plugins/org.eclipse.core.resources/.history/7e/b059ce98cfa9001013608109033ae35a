package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Entity
@Table(name = "tb_cursos")
@Getter
@Setter
@ToString
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer idCurso;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "duracion_horas")
    private Integer duracionHoras;
    
    @Column(name = "precio")
    private Double precio = 0.0;
    
    @Column(name = "cupo_maximo")
    private Integer cupoMaximo = 20;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    @ToString.Exclude
    private EstadoCurso estado;
    
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDate.now();
        }
        if (this.activo == null) {
            this.activo = true;
        }
    }
}