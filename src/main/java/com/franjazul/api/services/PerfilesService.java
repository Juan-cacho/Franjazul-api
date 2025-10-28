package com.franjazul.api.services;
import com.franjazul.api.model.Perfiles;
import com.franjazul.api.model.Rolles;
import com.franjazul.api.repository.PerfilesRepository;
import com.franjazul.api.repository.RollesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilesService {

    @Autowired
    private PerfilesRepository perfilRepository;

    @Autowired
    private RollesRepository rolRepository;

    // Obtener un perfil por ID
    public Optional<Perfiles> obtenerPorId(Integer id) {
        return perfilRepository.findById(id);
    }

    // Obtener todos los perfiles
    public List<Perfiles> obtenerTodos() {
        return perfilRepository.findAll();
    }

    // Obtener perfiles por rol
    public List<Perfiles> obtenerPorRol(Integer idRol) {
        return perfilRepository.findByRolId(idRol);
    }

    // Crear un nuevo perfil
    public Perfiles crear(Perfiles perfil) {
        // Validar que el nombre no exista
        Optional<Perfiles> perfilExistente = perfilRepository.findByNombrePer(perfil.getNombrePer());
        if (perfilExistente.isPresent()) {
            throw new RuntimeException("Ya existe un perfil con ese nombre");
        }

        // Validar que el rol exista
        if (perfil.getRol() == null) {
            throw new RuntimeException("Debe especificar un rol válido");
        }

        Optional<Rolles> rolOptional = rolRepository.findById(perfil.getRol().getIdRol());
        if (!rolOptional.isPresent()) {
            throw new RuntimeException("El rol especificado no existe");
        }

        perfil.setRol(rolOptional.get());
        return perfilRepository.save(perfil);
    }

    // Actualizar un perfil existente
    public Perfiles actualizar(Integer id, Perfiles perfilActualizado) {
        Perfiles perExiste = perfilRepository.findById(id).orElse(null);
        if (perExiste == null) {
            throw new RuntimeException("Perfil no encontrado con ID: " + id);
        }

        //Campos a actulizar
        if (perfilActualizado.getNombrePer() != null) {
            perExiste.setNombrePer(perfilActualizado.getNombrePer());
        }
        if (perfilActualizado.getDescripcionPer() != null) {
            perExiste.setDescripcionPer(perfilActualizado.getDescripcionPer());
        }

        // Validar rol
        if (perfilActualizado.getRol() != null) {
            Rolles nuevoRol = perfilActualizado.getRol();

            // Buscar el rol en la base de datos
            Rolles rolExiste = rolRepository.findById(nuevoRol.getIdRol()).orElse(null);
            if (rolExiste == null) {
                throw new RuntimeException("El rol con ID " + nuevoRol.getIdRol() + " no existe");
            }

            perExiste.setRol(rolExiste);
        }

        return perfilRepository.save(perExiste);
    }


    // Verificar si existe un perfil
    public boolean existe(Integer id) {
        return perfilRepository.existsById(id);
    }


    // Eliminar perfil físicamente
    public boolean borrar(Integer id) {
        if (!perfilRepository.existsById(id)) {
            throw new RuntimeException("Perfil no encontrado con ID: " + id);
        }

        perfilRepository.deleteById(id);
        return true;
    }

}
