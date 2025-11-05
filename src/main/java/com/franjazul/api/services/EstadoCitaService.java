package com.franjazul.api.services;

import com.franjazul.api.model.EstadoCita;
import com.franjazul.api.repository.EstadoCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoCitaService {

    @Autowired
    private EstadoCitaRepository estadoCitaRepository;

    // Obtener un estado de cita por nombre
    public Optional<EstadoCita> obtenerPorId(String nombreEc) {
        return estadoCitaRepository.findById(nombreEc);
    }

    // Obtener todos los estados de cita
    public List<EstadoCita> obtenerTodos() {
        return estadoCitaRepository.findAll();
    }

    // Crear un nuevo estado de cita
    public EstadoCita crear(EstadoCita estadoCita) {
        // Validar que no exista
        if (estadoCitaRepository.existsById(estadoCita.getNombreEc())) {
            throw new RuntimeException("Ya existe un estado de cita con el nombre: " + estadoCita.getNombreEc());
        }

        return estadoCitaRepository.save(estadoCita);
    }

    // Actualizar un estado de cita existente
    public EstadoCita actualizar(String nombreEc, EstadoCita estadoCitaActualizado) {
        Optional<EstadoCita> estadoCitaOpt = estadoCitaRepository.findById(nombreEc);

        if (!estadoCitaOpt.isPresent()) {
            throw new RuntimeException("Estado de cita no encontrado con nombre: " + nombreEc);
        }

        EstadoCita estadoCitaExistente = estadoCitaOpt.get();

        // Actualiza solo si se envió
        if (estadoCitaActualizado.getDescripcionEc() != null) {
            estadoCitaExistente.setDescripcionEc(estadoCitaActualizado.getDescripcionEc());
        }

        return estadoCitaRepository.save(estadoCitaExistente);
    }

    // Borrar físicamente
    public boolean borrar(String nombreEc) {
        if (!estadoCitaRepository.existsById(nombreEc)) {
            throw new RuntimeException("Estado de cita no encontrado con nombre: " + nombreEc);
        }

        estadoCitaRepository.deleteById(nombreEc);
        return true;
    }

    // Verificar si existe un estado de cita
    public boolean existe(String nombreEc) {
        return estadoCitaRepository.existsById(nombreEc);
    }
}
