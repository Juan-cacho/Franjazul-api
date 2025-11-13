package com.franjazul.api.services;

import com.franjazul.api.model.Lugares;
import com.franjazul.api.model.TipoLugar;
import com.franjazul.api.repository.LugaresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LugaresService {

    @Autowired
    private LugaresRepository lugaresRepository;

    @Autowired
    private TipoLugarService tipoLugarService;

    // Obtener un lugar por ID
    public Optional<Lugares> obtenerPorId(Integer id) {
        return lugaresRepository.findById(id);
    }

    // Obtener todos los lugares
    public List<Lugares> obtenerTodos() {
        return lugaresRepository.findAll();
    }

    // Crear un nuevo lugar
    public Lugares crear(Lugares lugar) {

        lugar.setIdLugar(null);
        // Validar que el nombre no exista
        Optional<Lugares> lugarExistente = lugaresRepository.findByNombreLugar(lugar.getNombreLugar());
        if (lugarExistente.isPresent()) {
            throw new RuntimeException("Ya existe un lugar con ese nombre");
        }

        // Validar que el tipo de lugar exista y esté asignado
        if (lugar.getTipoLugar() == null) {
            throw new RuntimeException("El tipo de lugar es obligatorio");
        }

        Optional<TipoLugar> tipoLugarOpt = tipoLugarService.obtenerPorId(lugar.getTipoLugar().getIdTl());
        if (!tipoLugarOpt.isPresent()) {
            throw new RuntimeException("El tipo de lugar con ID " + lugar.getTipoLugar().getIdTl() + " no existe");
        }

        lugar.setTipoLugar(tipoLugarOpt.get());

        // Validar que el lugar padre exista (si se especificó)
        if (lugar.getLugarPadre() != null && lugar.getLugarPadre().getIdLugar() != null) {
            Optional<Lugares> lugarPadreOpt = lugaresRepository.findById(lugar.getLugarPadre().getIdLugar());
            if (!lugarPadreOpt.isPresent()) {
                throw new RuntimeException("El lugar padre con ID " + lugar.getLugarPadre().getIdLugar() + " no existe");
            }
            lugar.setLugarPadre(lugarPadreOpt.get());
        } else {
            lugar.setLugarPadre(null);
        }

        return lugaresRepository.save(lugar);
    }

    // Actualizar un lugar existente
    public Lugares actualizar(Integer id, Lugares lugarActualizado) {
        Optional<Lugares> lugarOpt = lugaresRepository.findById(id);

        if (!lugarOpt.isPresent()) {
            throw new RuntimeException("Lugar no encontrado con ID: " + id);
        }

        Lugares lugarExistente = lugarOpt.get();

        // Actualiza solo si se envió
        if (lugarActualizado.getNombreLugar() != null) {
            lugarExistente.setNombreLugar(lugarActualizado.getNombreLugar());
        }

        if (lugarActualizado.getDireccionLugar() != null) {
            lugarExistente.setDireccionLugar(lugarActualizado.getDireccionLugar());
        }

        // Validar tipo de lugar antes de actualizar
        if (lugarActualizado.getTipoLugar() != null && lugarActualizado.getTipoLugar().getIdTl() != null) {
            Optional<TipoLugar> tipoLugarOpt = tipoLugarService.obtenerPorId(lugarActualizado.getTipoLugar().getIdTl());
            if (!tipoLugarOpt.isPresent()) {
                throw new RuntimeException("El tipo de lugar con ID " + lugarActualizado.getTipoLugar().getIdTl() + " no existe");
            }
            lugarExistente.setTipoLugar(tipoLugarOpt.get());
        }

        // Validar lugar padre antes de actualizar
        if (lugarActualizado.getLugarPadre() != null && lugarActualizado.getLugarPadre().getIdLugar() != null) {
            // Evitar autorreferencia
            if (lugarActualizado.getLugarPadre().getIdLugar().equals(id)) {
                throw new RuntimeException("Un lugar no puede estar dentro de sí mismo");
            }

            Optional<Lugares> lugarPadreOpt = lugaresRepository.findById(lugarActualizado.getLugarPadre().getIdLugar());
            if (!lugarPadreOpt.isPresent()) {
                throw new RuntimeException("El lugar padre con ID " + lugarActualizado.getLugarPadre().getIdLugar() + " no existe");
            }
            lugarExistente.setLugarPadre(lugarPadreOpt.get());
        }

        return lugaresRepository.save(lugarExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer id) {
        Optional<Lugares> lugarOpt = lugaresRepository.findById(id);

        if (!lugarOpt.isPresent()) {
            throw new RuntimeException("Lugar no encontrado con ID: " + id);
        }

        Lugares lugar = lugarOpt.get();

        // Verificar si hay lugares que dependen de este
        List<Lugares> lugaresHijos = lugaresRepository.findByLugarPadre(lugar);
        if (!lugaresHijos.isEmpty()) {
            throw new RuntimeException("No se puede eliminar el lugar porque existen " +
                    lugaresHijos.size() + " lugares dentro de él");
        }

        lugaresRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un lugar
    public boolean existe(Integer id) {
        return lugaresRepository.existsById(id);
    }

    // Obtener lugares por tipo
    public List<Lugares> obtenerPorTipo(Integer tipoLugarId) {
        Optional<TipoLugar> tipoLugarOpt = tipoLugarService.obtenerPorId(tipoLugarId);
        if (!tipoLugarOpt.isPresent()) {
            throw new RuntimeException("Tipo de lugar no encontrado con ID: " + tipoLugarId);
        }
        return lugaresRepository.findByTipoLugar(tipoLugarOpt.get());
    }

    // Obtener lugares dentro de otro lugar
    public List<Lugares> obtenerLugaresDentro(Integer lugarPadreId) {
        Optional<Lugares> lugarPadreOpt = lugaresRepository.findById(lugarPadreId);
        if (!lugarPadreOpt.isPresent()) {
            throw new RuntimeException("Lugar padre no encontrado con ID: " + lugarPadreId);
        }
        return lugaresRepository.findByLugarPadre(lugarPadreOpt.get());
    }
}
