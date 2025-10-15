package com.mcaulley.app.repository;

import com.mcaulley.app.entity.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoPagoRepository extends JpaRepository<EstadoPago, Integer> {
    Optional<EstadoPago> findByNombreEstadoPago(String nombreEstadoPago);
}