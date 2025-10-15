package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_inscripciones")
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

    // ✅ AGREGAR ESTE CAMPO
    @Column(name = "activo")
    private Boolean activo = true;

    public Inscripcion() {
        this.fechaInscripcion = LocalDate.now();
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
        generarCodigoInscripcion();
    }

    private void generarCodigoInscripcion() {
        if (this.codigoInscripcion == null) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String random = String.valueOf((int) (Math.random() * 1000));
            this.codigoInscripcion = "INS-" + timestamp + "-" + random;
        }
    }
}