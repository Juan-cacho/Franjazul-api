package com.franjazul.api.services;

import com.franjazul.api.model.Certificados;
import com.franjazul.api.model.Citas;
import com.franjazul.api.repository.CertificadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CertificadosService {

    @Autowired
    private CertificadosRepository certificadosRepository;

    @Autowired
    private CitasService citasService;

    // Obtener un certificado por código
    public Optional<Certificados> obtenerPorId(String codigoCer) {
        return certificadosRepository.findById(codigoCer);
    }

    // Obtener todos los certificados
    public List<Certificados> obtenerTodos() {
        return certificadosRepository.findAll();
    }

    // Crear un nuevo certificado
    public Certificados crear(Certificados certificado) {
        // Validar que el código no exista
        if (certificadosRepository.existsById(certificado.getCodigoCer())) {
            throw new RuntimeException("Ya existe un certificado con el código: " + certificado.getCodigoCer());
        }

        // Validar que la cita exista (obligatorio)
        if (certificado.getCita() == null || certificado.getCita().getIdCita() == null) {
            throw new RuntimeException("La cita es obligatoria");
        }

        Optional<Citas> citaOpt = citasService.obtenerPorId(certificado.getCita().getIdCita());
        if (!citaOpt.isPresent()) {
            throw new RuntimeException("La cita con ID " + certificado.getCita().getIdCita() + " no existe");
        }
        certificado.setCita(citaOpt.get());

        // Validar que la fecha de emisión sea anterior a la fecha de vencimiento
        if (certificado.getFechaEmision() != null && certificado.getFechaVence() != null) {
            if (certificado.getFechaEmision().isAfter(certificado.getFechaVence())) {
                throw new RuntimeException("La fecha de emisión debe ser anterior a la fecha de vencimiento");
            }
        }

        return certificadosRepository.save(certificado);
    }

    // Actualizar un certificado existente
    public Certificados actualizar(String codigoCer, Certificados certificadoActualizado) {
        Optional<Certificados> certificadoOpt = certificadosRepository.findById(codigoCer);

        if (!certificadoOpt.isPresent()) {
            throw new RuntimeException("Certificado no encontrado con código: " + codigoCer);
        }

        Certificados certificadoExistente = certificadoOpt.get();

        // Actualiza solo si se envió
        if (certificadoActualizado.getFechaEmision() != null) {
            certificadoExistente.setFechaEmision(certificadoActualizado.getFechaEmision());
        }

        if (certificadoActualizado.getFechaVence() != null) {
            certificadoExistente.setFechaVence(certificadoActualizado.getFechaVence());
        }

        // Validar cita antes de actualizar
        if (certificadoActualizado.getCita() != null && certificadoActualizado.getCita().getIdCita() != null) {
            Optional<Citas> citaOpt = citasService.obtenerPorId(certificadoActualizado.getCita().getIdCita());
            if (!citaOpt.isPresent()) {
                throw new RuntimeException("La cita con ID " + certificadoActualizado.getCita().getIdCita() + " no existe");
            }
            certificadoExistente.setCita(citaOpt.get());
        }

        // Validar fechas
        if (certificadoExistente.getFechaEmision().isAfter(certificadoExistente.getFechaVence())) {
            throw new RuntimeException("La fecha de emisión debe ser anterior a la fecha de vencimiento");
        }

        return certificadosRepository.save(certificadoExistente);
    }

    // Borrar físicamente
    public boolean borrar(String codigoCer) {
        if (!certificadosRepository.existsById(codigoCer)) {
            throw new RuntimeException("Certificado no encontrado con código: " + codigoCer);
        }

        certificadosRepository.deleteById(codigoCer);
        return true;
    }

    // Verificar si existe un certificado
    public boolean existe(String codigoCer) {
        return certificadosRepository.existsById(codigoCer);
    }

    // Obtener certificados por cita
    public List<Certificados> obtenerPorCita(Integer citaId) {
        Optional<Citas> citaOpt = citasService.obtenerPorId(citaId);
        if (!citaOpt.isPresent()) {
            throw new RuntimeException("Cita no encontrada con ID: " + citaId);
        }
        return certificadosRepository.findByCita(citaOpt.get());
    }

    // Obtener certificados vencidos
    public List<Certificados> obtenerVencidos() {
        LocalDateTime ahora = LocalDateTime.now();
        return certificadosRepository.findByFechaVenceBefore(ahora);
    }

    // Obtener certificados vigentes
    public List<Certificados> obtenerVigentes() {
        LocalDateTime ahora = LocalDateTime.now();
        return certificadosRepository.findByFechaVenceAfter(ahora);
    }

    // Obtener certificados por rango de fechas
    public List<Certificados> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        return certificadosRepository.findByFechaEmisionBetween(fechaInicio, fechaFin);
    }
}
