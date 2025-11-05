package com.franjazul.api.services;

import com.franjazul.api.model.Moleculas;
import com.franjazul.api.repository.MoleculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoleculasService {

    @Autowired
    private MoleculasRepository moleculasRepository;

    // Obtener una molécula por nombre
    public Optional<Moleculas> obtenerPorId(String nombreMol) {
        return moleculasRepository.findById(nombreMol);
    }

    // Obtener todas las moléculas
    public List<Moleculas> obtenerTodos() {
        return moleculasRepository.findAll();
    }

    // Crear una nueva molécula
    public Moleculas crear(Moleculas molecula) {
        // Validar que no exista
        if (moleculasRepository.existsById(molecula.getNombreMol())) {
            throw new RuntimeException("Ya existe una molécula con el nombre: " + molecula.getNombreMol());
        }

        return moleculasRepository.save(molecula);
    }

    // Actualizar una molécula existente
    public Moleculas actualizar(String nombreMol, Moleculas moleculaActualizada) {
        Optional<Moleculas> moleculaOpt = moleculasRepository.findById(nombreMol);

        if (!moleculaOpt.isPresent()) {
            throw new RuntimeException("Molécula no encontrada con nombre: " + nombreMol);
        }

        Moleculas moleculaExistente = moleculaOpt.get();

        // Actualiza solo si se envió
        if (moleculaActualizada.getDescripcionMol() != null) {
            moleculaExistente.setDescripcionMol(moleculaActualizada.getDescripcionMol());
        }

        return moleculasRepository.save(moleculaExistente);
    }

    // Borrar físicamente
    public boolean borrar(String nombreMol) {
        if (!moleculasRepository.existsById(nombreMol)) {
            throw new RuntimeException("Molécula no encontrada con nombre: " + nombreMol);
        }

        moleculasRepository.deleteById(nombreMol);
        return true;
    }

    // Verificar si existe una molécula
    public boolean existe(String nombreMol) {
        return moleculasRepository.existsById(nombreMol);
    }
}
