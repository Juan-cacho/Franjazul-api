package com.franjazul.api.services;

import com.franjazul.api.model.TipoServicio;
import com.franjazul.api.repository.TipoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    // Obtener un tipo de servicio por nombre
    public Optional<TipoServicio> obtenerPorId(String nombreTps) {
        return tipoServicioRepository.findById(nombreTps);
    }

    // Obtener todos los tipos de servicio
    public List<TipoServicio> obtenerTodos() {
        return tipoServicioRepository.findAll();
    }

    // Crear un nuevo tipo de servicio
    public TipoServicio crear(TipoServicio tipoServicio) {
        // Validar que no exista
        if (tipoServicioRepository.existsById(tipoServicio.getNombreTps())) {
            throw new RuntimeException("Ya existe un tipo de servicio con el nombre: " + tipoServicio.getNombreTps());
        }

        return tipoServicioRepository.save(tipoServicio);
    }

    // Actualizar un tipo de servicio existente
    public TipoServicio actualizar(String nombreTps, TipoServicio tipoServicioActualizado) {
        Optional<TipoServicio> tipoServicioOpt = tipoServicioRepository.findById(nombreTps);

        if (!tipoServicioOpt.isPresent()) {
            throw new RuntimeException("Tipo de servicio no encontrado con nombre: " + nombreTps);
        }

        TipoServicio tipoServicioExistente = tipoServicioOpt.get();

        // Actualiza solo si se envió
        if (tipoServicioActualizado.getDescripcionTsp() != null) {
            tipoServicioExistente.setDescripcionTsp(tipoServicioActualizado.getDescripcionTsp());
        }

        return tipoServicioRepository.save(tipoServicioExistente);
    }

    // Borrar físicamente
    public boolean borrar(String nombreTps) {
        if (!tipoServicioRepository.existsById(nombreTps)) {
            throw new RuntimeException("Tipo de servicio no encontrado con nombre: " + nombreTps);
        }

        tipoServicioRepository.deleteById(nombreTps);
        return true;
    }

    // Verificar si existe un tipo de servicio
    public boolean existe(String nombreTps) {
        return tipoServicioRepository.existsById(nombreTps);
    }
}
