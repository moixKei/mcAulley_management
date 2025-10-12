package com.mcaulley.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mcaulley.app.entity.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {
}
