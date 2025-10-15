package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "tb_cursos")
@Getter
@Setter
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
    
    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    private EstadoCurso estado;
    
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
    
    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDate.now();
        }
    }
}