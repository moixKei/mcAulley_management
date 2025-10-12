package com.mcaulley.app.service;

import com.mcaulley.app.entity.Alumna;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.Map;

public interface AlumnaService {

    // ✅ Operaciones CRUD básicas
    List<Alumna> listarTodas();
    Optional<Alumna> buscarPorId(Integer id);
    Alumna guardar(Alumna alumna);
    Alumna actualizar(Integer id, Alumna alumna);
    void eliminar(Integer id);
    
    // ✅ Búsquedas específicas
    Optional<Alumna> buscarPorDni(String dni);
    Optional<Alumna> buscarPorCorreo(String correo);
    List<Alumna> buscarPorNombre(String nombre);
    List<Alumna> buscarPorApellido(String apellido);
    List<Alumna> buscarPorNombreOApellido(String nombre, String apellido);
    List<Alumna> buscarPorNombreYApellido(String nombre, String apellido);
    
    // ✅ Búsquedas avanzadas
    List<Alumna> buscarPorCelular(String celular);
    List<Alumna> buscarPorDireccion(String direccion);
    List<Alumna> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
    List<Alumna> buscarAlumnasActivas();
    List<Alumna> buscarAlumnasInactivas();
    
    // ✅ Operaciones de validación y verificación
    boolean existeAlumnaConDni(String dni);
    boolean existeAlumnaConCorreo(String correo);
    boolean existeOtraAlumnaConDni(String dni, Integer idExcluir);
    boolean validarAlumna(Alumna alumna);
    
    // ✅ Operaciones de estado
    void activarAlumna(Integer id);
    void desactivarAlumna(Integer id);
    boolean toggleEstadoAlumna(Integer id);
    
    // ✅ Operaciones batch
    List<Alumna> guardarTodas(List<Alumna> alumnas);
    void desactivarAlumnasAntiguas(LocalDate fechaLimite);
    
    // ✅ Reportes y estadísticas
    Long contarTotalAlumnas();
    Long contarAlumnasActivas();
    Long contarAlumnasRegistradasEsteMes();
    Map<String, Long> obtenerEstadisticas();
    List<Object[]> obtenerAlumnasPorMes(int año);
    
    // ✅ Búsquedas para UI
    List<Alumna> buscarParaAutocompletado(String texto);
    List<Alumna> buscarConFiltros(String nombre, String apellido, Boolean activa, LocalDate fechaDesde, LocalDate fechaHasta);
}