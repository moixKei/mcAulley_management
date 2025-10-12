package com.mcaulley.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_estado_pago")
@Getter
@Setter
public class EstadoPago {
    
    @Id
    @Column(name = "id_estado_pago")
    private Integer idEstadoPago;
    
    @Column(name = "nombre_estado_pago", length = 20, unique = true)
    private String nombreEstadoPago;
    
    // Métodos helper
    public boolean esPendiente() {
        return "pendiente".equalsIgnoreCase(nombreEstadoPago);
    }
    
    public boolean esCompletado() {
        return "completado".equalsIgnoreCase(nombreEstadoPago);
    }
    
    public boolean esFallido() {
        return "fallido".equalsIgnoreCase(nombreEstadoPago);
    }
}