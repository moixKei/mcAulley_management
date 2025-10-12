package com.mcaulley.app.service.impl;

import com.mcaulley.app.entity.Alumna;
import com.mcaulley.app.repository.AlumnaRepository;
import com.mcaulley.app.service.AlumnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Transactional
public class AlumnaServiceImp implements AlumnaService {

    @Autowired
    private AlumnaRepository alumnaRepository;

    // ✅ CRUD Básico
    @Override
    @Transactional(readOnly = true)
    public List<Alumna> listarTodas() {
        return alumnaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alumna> buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return alumnaRepository.findById(id);
    }

    @Override
    public Alumna guardar(Alumna alumna) {
        if (!validarAlumna(alumna)) {
            throw new IllegalArgumentException("Los datos de la alumna no son válidos");
        }
        
        if (existeAlumnaConDni(alumna.getDni())) {
            throw new IllegalArgumentException("Ya existe una alumna con el DNI: " + alumna.getDni());
        }
        
        if (alumna.getCorreo() != null && !alumna.getCorreo().trim().isEmpty() && 
            existeAlumnaConCorreo(alumna.getCorreo())) {
            throw new IllegalArgumentException("Ya existe una alumna con el correo: " + alumna.getCorreo());
        }
        
        return alumnaRepository.save(alumna);
    }

    @Override
    public Alumna actualizar(Integer id, Alumna alumnaActualizada) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de alumna inválido");
        }
        
        if (!validarAlumna(alumnaActualizada)) {
            throw new IllegalArgumentException("Los datos de la alumna no son válidos");
        }
        
        return alumnaRepository.findById(id)
                .map(alumnaExistente -> {
                    // Verificar si el DNI ya existe en otra alumna
                    if (existeOtraAlumnaConDni(alumnaActualizada.getDni(), id)) {
                        throw new IllegalArgumentException("Ya existe otra alumna con el DNI: " + alumnaActualizada.getDni());
                    }
                    
                    // Verificar si el correo ya existe en otra alumna
                    if (alumnaActualizada.getCorreo() != null && !alumnaActualizada.getCorreo().trim().isEmpty() &&
                        existeOtraAlumnaConCorreo(alumnaActualizada.getCorreo(), id)) {
                        throw new IllegalArgumentException("Ya existe otra alumna con el correo: " + alumnaActualizada.getCorreo());
                    }
                    
                    // Actualizar campos permitidos
                    alumnaExistente.setNombre(alumnaActualizada.getNombre());
                    alumnaExistente.setApellido(alumnaActualizada.getApellido());
                    alumnaExistente.setDni(alumnaActualizada.getDni());
                    alumnaExistente.setCorreo(alumnaActualizada.getCorreo());
                    alumnaExistente.setCelular(alumnaActualizada.getCelular());
                    alumnaExistente.setDireccion(alumnaActualizada.getDireccion());
                    
                    return alumnaRepository.save(alumnaExistente);
                })
                .orElseThrow(() -> new RuntimeException("Alumna no encontrada con ID: " + id));
    }

    @Override
    public void eliminar(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de alumna inválido");
        }
        
        Alumna alumna = alumnaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumna no encontrada con ID: " + id));
        
        alumnaRepository.delete(alumna);
    }

    // ✅ Búsquedas específicas
    @Override
    @Transactional(readOnly = true)
    public Optional<Alumna> buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return Optional.empty();
        }
        return alumnaRepository.findByDni(dni.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alumna> buscarPorCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return Optional.empty();
        }
        return alumnaRepository.findByCorreo(correo.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return alumnaRepository.findByNombre(nombre.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarPorApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return alumnaRepository.findByApellido(apellido.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarPorNombreOApellido(String nombre, String apellido) {
        return alumnaRepository.buscarPorNombreOApellido(
            nombre != null ? nombre.trim() : "",
            apellido != null ? apellido.trim() : ""
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarPorNombreYApellido(String nombre, String apellido) {
        if ((nombre == null || nombre.trim().isEmpty()) || 
            (apellido == null || apellido.trim().isEmpty())) {
            return new ArrayList<>();
        }
        return alumnaRepository.findByNombreAndApellido(nombre.trim(), apellido.trim());
    }

    // ✅ Búsquedas avanzadas
    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarPorCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return alumnaRepository.buscarPorCelularConteniendo(celular.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarPorDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return alumnaRepository.buscarPorDireccionConteniendo(direccion.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return new ArrayList<>();
        }
        return alumnaRepository.findByFechaRegistroBetween(fechaInicio, fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarAlumnasActivas() {
        return alumnaRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarAlumnasInactivas() {
        return alumnaRepository.findByActivoFalse();
    }

    // ✅ Validaciones y verificaciones
    @Override
    @Transactional(readOnly = true)
    public boolean existeAlumnaConDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        return alumnaRepository.existsByDni(dni.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeAlumnaConCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return alumnaRepository.existsByCorreo(correo.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeOtraAlumnaConDni(String dni, Integer idExcluir) {
        if (dni == null || dni.trim().isEmpty() || idExcluir == null) {
            return false;
        }
        return alumnaRepository.existsByDniAndIdAlumnaNot(dni.trim(), idExcluir);
    }

    private boolean existeOtraAlumnaConCorreo(String correo, Integer idExcluir) {
        return alumnaRepository.findByCorreo(correo.trim())
                .map(alumna -> !alumna.getIdAlumna().equals(idExcluir))
                .orElse(false);
    }

    @Override
    public boolean validarAlumna(Alumna alumna) {
        return alumna != null && 
               alumna.getNombre() != null && !alumna.getNombre().trim().isEmpty() &&
               alumna.getApellido() != null && !alumna.getApellido().trim().isEmpty() &&
               alumna.getDni() != null && !alumna.getDni().trim().isEmpty();
    }

    // ✅ Operaciones de estado
    @Override
    public void activarAlumna(Integer id) {
        cambiarEstadoAlumna(id, true);
    }

    @Override
    public void desactivarAlumna(Integer id) {
        cambiarEstadoAlumna(id, false);
    }

    @Override
    public boolean toggleEstadoAlumna(Integer id) {
        Alumna alumna = alumnaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumna no encontrada con ID: " + id));
        
        boolean nuevoEstado = !alumna.getActivo();
        alumna.setActivo(nuevoEstado);
        alumnaRepository.save(alumna);
        
        return nuevoEstado;
    }

    private void cambiarEstadoAlumna(Integer id, boolean estado) {
        Alumna alumna = alumnaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumna no encontrada con ID: " + id));
        
        alumna.setActivo(estado);
        alumnaRepository.save(alumna);
    }

    // ✅ Operaciones batch
    @Override
    public List<Alumna> guardarTodas(List<Alumna> alumnas) {
        if (alumnas == null || alumnas.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Validar todas las alumnas antes de guardar
        for (Alumna alumna : alumnas) {
            if (!validarAlumna(alumna)) {
                throw new IllegalArgumentException("Una o más alumnas tienen datos inválidos");
            }
            if (existeAlumnaConDni(alumna.getDni())) {
                throw new IllegalArgumentException("Ya existe una alumna con el DNI: " + alumna.getDni());
            }
        }
        
        return alumnaRepository.saveAll(alumnas);
    }

    // ✅ SOLO UN método desactivarAlumnasAntiguas (eliminé el duplicado)
    @Override
    public void desactivarAlumnasAntiguas(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            throw new IllegalArgumentException("Fecha límite no puede ser nula");
        }
        alumnaRepository.desactivarAlumnasAntiguas(fechaLimite);
    }

    // ✅ Reportes y estadísticas
    @Override
    @Transactional(readOnly = true)
    public Long contarTotalAlumnas() {
        return alumnaRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarAlumnasActivas() {
        return alumnaRepository.contarAlumnasActivas();
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarAlumnasRegistradasEsteMes() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return (long) alumnaRepository.findByFechaRegistroBetween(inicioMes, finMes).size();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obtenerEstadisticas() {
        Map<String, Object> resultados = alumnaRepository.obtenerEstadisticasRegistro();
        Map<String, Long> estadisticas = new HashMap<>();
        
        // Convertir Object a Long
        estadisticas.put("total", ((Number) resultados.get("total")).longValue());
        estadisticas.put("activas", ((Number) resultados.get("activas")).longValue());
        estadisticas.put("esteMes", ((Number) resultados.get("este_mes")).longValue());
        estadisticas.put("inactivas", estadisticas.get("total") - estadisticas.get("activas"));
        
        return estadisticas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenerAlumnasPorMes(int año) {
        return alumnaRepository.contarAlumnasPorMes(año);
    }

    // ✅ Búsquedas para UI
    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarParaAutocompletado(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Object[]> resultados = alumnaRepository.buscarParaAutocompletado(texto.trim());
        List<Alumna> alumnas = new ArrayList<>();
        
        for (Object[] resultado : resultados) {
            Alumna alumna = new Alumna();
            alumna.setIdAlumna((Integer) resultado[0]);
            alumna.setNombre((String) resultado[1]);
            alumna.setApellido((String) resultado[2]);
            alumnas.add(alumna);
        }
        
        return alumnas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumna> buscarConFiltros(String nombre, String apellido, Boolean activa, LocalDate fechaDesde, LocalDate fechaHasta) {
        // Para simplificar, usamos el método existente y filtramos por activo después
        List<Alumna> resultados = alumnaRepository.buscarConFiltrosAvanzados(nombre, apellido, fechaDesde, fechaHasta);
        
        if (activa != null) {
            resultados.removeIf(alumna -> !alumna.getActivo().equals(activa));
        }
        
        return resultados;
    }
}