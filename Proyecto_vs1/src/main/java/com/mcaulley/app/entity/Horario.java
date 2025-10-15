package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalTime;
import java.time.LocalDate;

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

    @Column(name = "dia_semana", nullable = false, length = 20)
    private String diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "cupos_disponibles")
    private Integer cuposDisponibles = 0;

    // ✅ AGREGAR ESTE CAMPO
    @Column(name = "activo")
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    @ToString.Exclude
    private Curso curso;

    public Horario() {
        this.cuposDisponibles = 0;
        this.activo = true;
    }
}