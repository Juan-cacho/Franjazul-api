package com.franjazul.api.services;

import com.franjazul.api.model.CitaServicio;
import com.franjazul.api.model.CitaServicioId;
import com.franjazul.api.repository.CitaServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServicioService {

    @Autowired
    private CitaServicioRepository citaServicioRepository;

    @Autowired
    private CitasService citasService;

    @Autowired
    private ServiciosService serviciosService;

    // Obtener una relación cita-servicio por ID compuesto
    public Optional<CitaServicio> obtenerPorId(Integer citaId, Integer servicioId) {
        CitaServicioId id = new CitaServicioId(citaId, servicioId);
        return citaServicioRepository.findById(id);
    }

    // Obtener todas las relaciones cita-servicio
    public List<CitaServicio> obtenerTodos() {
        return citaServicioRepository.findAll();
    }

    // Crear una nueva relación cita-servicio
    public CitaServicio crear(CitaServicio citaServicio) {
        // Validar que la cita exista (obligatorio)
        if (citaServicio.getCitaEnIntermedio() == null) {
            throw new RuntimeException("El ID de la cita es obligatorio");
        }

        if (!citasService.existe(citaServicio.getCitaEnIntermedio())) {
            throw new RuntimeException("La cita con ID " + citaServicio.getCitaEnIntermedio() + " no existe");
        }

        // Validar que el servicio exista (obligatorio)
        if (citaServicio.getServicioEnIntermedio() == null) {
            throw new RuntimeException("El ID del servicio es obligatorio");
        }

        if (!serviciosService.existe(citaServicio.getServicioEnIntermedio())) {
            throw new RuntimeException("El servicio con ID " + citaServicio.getServicioEnIntermedio() + " no existe");
        }

        // Validar que la relación no exista ya
        CitaServicioId id = new CitaServicioId(citaServicio.getCitaEnIntermedio(), citaServicio.getServicioEnIntermedio());
        if (citaServicioRepository.existsById(id)) {
            throw new RuntimeException("Ya existe una relación entre la cita " + citaServicio.getCitaEnIntermedio() +
                    " y el servicio " + citaServicio.getServicioEnIntermedio());
        }

        return citaServicioRepository.save(citaServicio);
    }

    // Actualizar una relación cita-servicio existente
    public CitaServicio actualizar(Integer citaId, Integer servicioId, CitaServicio citaServicioActualizado) {
        CitaServicioId id = new CitaServicioId(citaId, servicioId);
        Optional<CitaServicio> citaServicioOpt = citaServicioRepository.findById(id);

        if (!citaServicioOpt.isPresent()) {
            throw new RuntimeException("Relación cita-servicio no encontrada con IDs: citaId=" + citaId + ", servicioId=" + servicioId);
        }

        CitaServicio citaServicioExistente = citaServicioOpt.get();

        // Actualiza solo la cantidad si se envió
        if (citaServicioActualizado.getCantidadSer() != null) {
            citaServicioExistente.setCantidadSer(citaServicioActualizado.getCantidadSer());
        }

        return citaServicioRepository.save(citaServicioExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer citaId, Integer servicioId) {
        CitaServicioId id = new CitaServicioId(citaId, servicioId);

        if (!citaServicioRepository.existsById(id)) {
            throw new RuntimeException("Relación cita-servicio no encontrada con IDs: citaId=" + citaId + ", servicioId=" + servicioId);
        }

        citaServicioRepository.deleteById(id);
        return true;
    }

    // Obtener servicios de una cita
    public List<CitaServicio> obtenerServiciosDeCita(Integer citaId) {
        if (!citasService.existe(citaId)) {
            throw new RuntimeException("La cita con ID " + citaId + " no existe");
        }
        return citaServicioRepository.findByCitaEnIntermedio(citaId);
    }

    // Obtener citas que tienen un servicio
    public List<CitaServicio> obtenerCitasConServicio(Integer servicioId) {
        if (!serviciosService.existe(servicioId)) {
            throw new RuntimeException("El servicio con ID " + servicioId + " no existe");
        }
        return citaServicioRepository.findByServicioEnIntermedio(servicioId);
    }
}
