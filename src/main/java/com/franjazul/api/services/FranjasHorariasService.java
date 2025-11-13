package com.franjazul.api.services;

import com.franjazul.api.model.FranjasHorarias;
import com.franjazul.api.repository.FranjasHorariasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FranjasHorariasService {

    @Autowired
    private FranjasHorariasRepository franjasHorariasRepository;

    public Optional<FranjasHorarias> obtenerPorId(Integer id) {
        return franjasHorariasRepository.findById(id);
    }

    public List<FranjasHorarias> obtenerTodos() {
        return franjasHorariasRepository.findAll();
    }

    public FranjasHorarias crear(FranjasHorarias franjaHoraria) {
        // el ID se hace null para que la secuencia lo genere
        franjaHoraria.setIdFranja(null);

        // Validar que las fechas no sean nulas
        if (franjaHoraria.getFechaInicio() == null || franjaHoraria.getFechaFin() == null) {
            throw new RuntimeException("Las fechas de inicio y fin son obligatorias");
        }

        // Validar que la fecha de inicio sea anterior a la fecha de fin
        if (franjaHoraria.getFechaInicio().isAfter(franjaHoraria.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        return franjasHorariasRepository.save(franjaHoraria);
    }

    public FranjasHorarias actualizar(Integer id, FranjasHorarias franjaHorariaActualizada) {
        Optional<FranjasHorarias> franjaHorariaOpt = franjasHorariasRepository.findById(id);

        if (!franjaHorariaOpt.isPresent()) {
            throw new RuntimeException("Franja horaria no encontrada con ID: " + id);
        }

        FranjasHorarias franjaHorariaExistente = franjaHorariaOpt.get();

        // Actualiza solo si se envió
        if (franjaHorariaActualizada.getFechaInicio() != null) {
            franjaHorariaExistente.setFechaInicio(franjaHorariaActualizada.getFechaInicio());
        }

        if (franjaHorariaActualizada.getFechaFin() != null) {
            franjaHorariaExistente.setFechaFin(franjaHorariaActualizada.getFechaFin());
        }

        // Validar que la fecha de inicio sea anterior a la fecha de fin
        if (franjaHorariaExistente.getFechaInicio().isAfter(franjaHorariaExistente.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        return franjasHorariasRepository.save(franjaHorariaExistente);
    }

    public boolean borrar(Integer id) {
        if (!franjasHorariasRepository.existsById(id)) {
            throw new RuntimeException("Franja horaria no encontrada con ID: " + id);
        }

        franjasHorariasRepository.deleteById(id);
        return true;
    }

    public boolean existe(Integer id) {
        return franjasHorariasRepository.existsById(id);
    }
}
