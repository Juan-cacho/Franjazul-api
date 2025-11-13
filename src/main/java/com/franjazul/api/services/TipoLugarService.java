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

    public Optional<TipoLugar> obtenerPorId(Integer id) {
        return tipoLugarRepository.findById(id);
    }

    public List<TipoLugar> obtenerTodos() {
        return tipoLugarRepository.findAll();
    }

    public TipoLugar crear(TipoLugar tipoLugar) {
        // el ID se pone null para que la secuencia lo genere
        tipoLugar.setIdTl(null);

        // Validar que el nombre no exista
        Optional<TipoLugar> tipoLugarExistente = tipoLugarRepository.findByNombreTl(tipoLugar.getNombreTl());
        if (tipoLugarExistente.isPresent()) {
            throw new RuntimeException("Ya existe un tipo de lugar con ese nombre");
        }

        return tipoLugarRepository.save(tipoLugar);
    }

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

    public boolean borrar(Integer id) {
        if (!tipoLugarRepository.existsById(id)) {
            throw new RuntimeException("Tipo de lugar no encontrado con ID: " + id);
        }

        tipoLugarRepository.deleteById(id);
        return true;
    }

    public boolean existe(Integer id) {
        return tipoLugarRepository.existsById(id);
    }
}
