package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalTime;

@Entity
@Table(name = "tb_horarios")
@Getter
@Setter
@ToString
public class Horario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer idHorario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    @ToString.Exclude
    private Curso curso;
    
    @Column(name = "dia_semana", length = 10)
    private String diaSemana;
    
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    
    @Column(name = "cupos_disponibles")
    private Integer cuposDisponibles;
    
    @Column(name = "aula", length = 20)
    private String aula;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @PrePersist
    protected void onCreate() {
        if (this.activo == null) {
            this.activo = true;
        }
        if (this.cuposDisponibles == null && this.curso != null) {
            this.cuposDisponibles = this.curso.getCupoMaximo();
        }
    }
}