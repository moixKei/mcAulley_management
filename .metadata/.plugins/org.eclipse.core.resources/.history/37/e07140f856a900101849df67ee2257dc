package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false, length = 20)
    private DayOfWeek diaSemana;

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

    @Column(name = "activo")
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    @ToString.Exclude
    private Curso curso;

    // Constructor
    public Horario() {
        this.cuposDisponibles = 0;
        this.activo = true;
    }

    // Constructor con parámetros principales
    public Horario(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFin, Curso curso) {
        this();
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.curso = curso;
    }

    // Método helper para validar horario
    public boolean esHorarioValido() {
        return horaInicio != null && 
               horaFin != null && 
               horaInicio.isBefore(horaFin) &&
               diaSemana != null;
    }

    // Método helper para obtener duración en horas
    public double getDuracionHoras() {
        if (horaInicio != null && horaFin != null) {
            long minutos = java.time.Duration.between(horaInicio, horaFin).toMinutes();
            return minutos / 60.0;
        }
        return 0;
    }

    // Método para verificar si hay cupos disponibles
    public boolean tieneCuposDisponibles() {
        return cuposDisponibles != null && cuposDisponibles > 0;
    }

    // Método para reducir cupos
    public void reducirCupo() {
        if (tieneCuposDisponibles()) {
            this.cuposDisponibles--;
        }
    }

    // Método para aumentar cupos
    public void aumentarCupo() {
        if (this.cuposDisponibles == null) {
            this.cuposDisponibles = 1;
        } else {
            this.cuposDisponibles++;
        }
    }
}