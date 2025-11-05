package com.franjazul.api.services;

import com.franjazul.api.model.TipoLugar;
import com.franjazul.api.repository.TipoLugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoLugarService {

    @Autowired
    private TipoLugarRepository tipoLugarRepository;

    // Obtener un tipo de lugar por ID
    public Optional<TipoLugar> obtenerPorId(Integer id) {
        return tipoLugarRepository.findById(id);
    }

    // Obtener todos los tipos de lugar
    public List<TipoLugar> obtenerTodos() {
        return tipoLugarRepository.findAll();
    }

    // Crear un nuevo tipo de lugar
    public TipoLugar crear(TipoLugar tipoLugar) {
        // Validar que el nombre no exista
        Optional<TipoLugar> tipoLugarExistente = tipoLugarRepository.findByNombreTl(tipoLugar.getNombreTl());
        if (tipoLugarExistente.isPresent()) {
            throw new RuntimeException("Ya existe un tipo de lugar con ese nombre");
        }

        return tipoLugarRepository.save(tipoLugar);
    }

    // Actualizar un tipo de lugar existente
    public TipoLugar actualizar(Integer id, TipoLugar tipoLugarActualizado) {
        Optional<TipoLugar> tipoLugarOpt = tipoLugarRepository.findById(id);

        if (!tipoLugarOpt.isPresent()) {
            throw new RuntimeException("Tipo de lugar no encontrado con ID: " + id);
        }

        TipoLugar tipoLugarExistente = tipoLugarOpt.get();

        // Actualiza solo si se envió
        if (tipoLugarActualizado.getNombreTl() != null) {
            tipoLugarExistente.setNombreTl(tipoLugarActualizado.getNombreTl());
        }

        return tipoLugarRepository.save(tipoLugarExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer id) {
        if (!tipoLugarRepository.existsById(id)) {
            throw new RuntimeException("Tipo de lugar no encontrado con ID: " + id);
        }

        tipoLugarRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un tipo de lugar
    public boolean existe(Integer id) {
        return tipoLugarRepository.existsById(id);
    }
}
