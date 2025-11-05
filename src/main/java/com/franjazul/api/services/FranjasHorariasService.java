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

    // Obtener una franja horaria por ID
    public Optional<FranjasHorarias> obtenerPorId(Integer id) {
        return franjasHorariasRepository.findById(id);
    }

    // Obtener todas las franjas horarias
    public List<FranjasHorarias> obtenerTodos() {
        return franjasHorariasRepository.findAll();
    }

    // Crear una nueva franja horaria
    public FranjasHorarias crear(FranjasHorarias franjaHoraria) {
        // Validar que el ID no exista
        if (franjasHorariasRepository.existsById(franjaHoraria.getIdFranja())) {
            throw new RuntimeException("Ya existe una franja horaria con el ID: " + franjaHoraria.getIdFranja());
        }

        // Validar que la fecha de inicio sea anterior a la fecha de fin
//        if (franjaHoraria.getFechaInicio().after(franjaHoraria.getFechaFin())) {
//            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin");
//        }

        return franjasHorariasRepository.save(franjaHoraria);
    }

    // Actualizar una franja horaria existente
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
//        if (franjaHorariaExistente.getFechaInicio().after(franjaHorariaExistente.getFechaFin())) {
//            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin");
//        }

        return franjasHorariasRepository.save(franjaHorariaExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer id) {
        if (!franjasHorariasRepository.existsById(id)) {
            throw new RuntimeException("Franja horaria no encontrada con ID: " + id);
        }

        franjasHorariasRepository.deleteById(id);
        return true;
    }

    // Verificar si existe una franja horaria
    public boolean existe(Integer id) {
        return franjasHorariasRepository.existsById(id);
    }
}

