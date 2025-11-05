package com.franjazul.api.services;
import com.franjazul.api.model.Cargos;
import com.franjazul.api.model.Rolles;
import com.franjazul.api.repository.CargosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargosService {

    @Autowired
    private CargosRepository cargosRepository;

    public Optional<Cargos> obtenerPorId(String id) {
        return cargosRepository.findById(id);
    }

    // Obtener todos los Cargos
    public List<Cargos> obtenerTodos() {
        return cargosRepository.findAll();
    }


    // Crear un nuevo Cargo
    public Cargos crear(Cargos cargo) {
        // Validar que el nombre no exista
        Optional<Cargos> cargoExistente = cargosRepository.findById(cargo.getNombreCargo());
        if (cargoExistente.isPresent()) {
            throw new RuntimeException("Ya existe un cargo con ese nombre");
        }

        return cargosRepository.save(cargo);
    }

    // Actualizar un Cargo existente
    public Cargos actualizar(String id, Cargos cargoActualizado) {
        Cargos cargoExistente = cargosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado con ID: " + id));

        // Actualiza solo si se envió
        if (cargoActualizado.getNombreCargo() != null) {
            cargoExistente.setNombreCargo(cargoActualizado.getNombreCargo());
        }
        if (cargoActualizado.getDescripcionCargo() != null) {
            cargoExistente.setDescripcionCargo(cargoActualizado.getDescripcionCargo());
        }

        return cargosRepository.save(cargoActualizado);
    }

    //Borrar fisicamente
    public boolean borrar(String id) {
        if (!cargosRepository.existsById(id)) {
            throw new RuntimeException("Cargo no encontrado con ID: " + id);
        }

        cargosRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un rol
    public boolean existe(String id) {
        return cargosRepository.existsById(id);
    }
}
