package com.mcaulley.app.service.impl;

import com.mcaulley.app.entity.EstadoPago;
import com.mcaulley.app.repository.EstadoPagoRepository;
import com.mcaulley.app.service.EstadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstadoPagoServiceImp implements EstadoPagoService {

    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstadoPago> listarTodos() {
        return estadoPagoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoPago> buscarPorId(Integer id) {
        return estadoPagoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoPago> buscarPorNombre(String nombre) {
        return estadoPagoRepository.findByNombreEstadoPago(nombre);
    }
}