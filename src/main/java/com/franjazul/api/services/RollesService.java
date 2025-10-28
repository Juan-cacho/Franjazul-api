package com.franjazul.api.services;

import com.franjazul.api.model.Rolles;
import com.franjazul.api.repository.RollesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RollesService {

    @Autowired
    private RollesRepository rolRepository;

    // Obtener un rol por ID
    public Optional<Rolles> obtenerPorId(Integer id) {
        return rolRepository.findById(id);
    }

    // Obtener todos los roles
    public List<Rolles> obtenerTodos() {
        return rolRepository.findAll();
    }

    // Crear un nuevo rol
    public Rolles crear(Rolles rol) {
        // Validar que el nombre no exista
        Optional<Rolles> rolExistente = rolRepository.findByNombreRol(rol.getNombreRol());
        if (rolExistente.isPresent()) {
            throw new RuntimeException("Ya existe un rol con ese nombre");
        }

        return rolRepository.save(rol);
    }

    // Actualizar un rol existente
    public Rolles actualizar(Integer id, Rolles rolActualizado) {
        Rolles rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));

        // Actualiza solo si se envió
        if (rolActualizado.getNombreRol() != null) {
            rolExistente.setNombreRol(rolActualizado.getNombreRol());
        }
        if (rolActualizado.getDescripcionRol() != null) {
            rolExistente.setDescripcionRol(rolActualizado.getDescripcionRol());
        }

        return rolRepository.save(rolExistente);
    }

    //Borrar fisicamente
    public boolean borrar(Integer id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }

        rolRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un rol
    public boolean existe(Integer id) {
        return rolRepository.existsById(id);
    }
}
