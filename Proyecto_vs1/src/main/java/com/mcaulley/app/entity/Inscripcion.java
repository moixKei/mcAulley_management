package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "tb_inscripciones",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_alumna", "id_horario"}, name = "uk_inscripcion_alumna_horario")
    }
)
@Getter
@Setter
@ToString
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Integer idInscripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alumna", nullable = false)
    @ToString.Exclude
    private Alumna alumna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario", nullable = false)
    @ToString.Exclude
    private Horario horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_pago")
    @ToString.Exclude
    private EstadoPago estadoPago;

    @Column(name = "fecha_inscripcion", updatable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "qr_codigo", length = 200)
    private String qrCodigo;

    @Column(name = "codigo_inscripcion", unique = true, length = 50)
    private String codigoInscripcion;

    @Column(name = "activo")
    private Boolean activo = true;

    // Constructor
    public Inscripcion() {
        this.fechaInscripcion = LocalDate.now();
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
        generarCodigoInscripcion();
    }

    // Constructor con parámetros principales
    public Inscripcion(Alumna alumna, Horario horario) {
        this();
        this.alumna = alumna;
        this.horario = horario;
        generarCodigoInscripcion();
    }

    // Método para generar código único de inscripción
    private void generarCodigoInscripcion() {
        if (this.codigoInscripcion == null) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String random = String.valueOf((int) (Math.random() * 1000));
            this.codigoInscripcion = "INS-" + timestamp + "-" + random;
        }
    }

    // Método helper para validar inscripción
    public boolean esInscripcionValida() {
        return alumna != null && 
               horario != null && 
               horario.tieneCuposDisponibles() &&
               fechaInscripcion != null;
    }

    // Método para obtener información resumida
    public String getInfoResumen() {
        if (alumna != null && horario != null && horario.getCurso() != null) {
            return String.format("Inscripción: %s - %s en %s", 
                alumna.getNombreCompleto(),
                horario.getCurso().getNombre(),
                horario.getDiaSemana());
        }
        return "Inscripción sin información completa";
    }

    // Callback antes de persistir
    @PrePersist
    protected void onCreate() {
        if (this.fechaInscripcion == null) {
            this.fechaInscripcion = LocalDate.now();
        }
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        generarCodigoInscripcion();
    }
}