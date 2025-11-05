package com.franjazul.api.services;

import com.franjazul.api.model.Permisos;
import com.franjazul.api.model.PermisoId;
import com.franjazul.api.model.Perfiles;
import com.franjazul.api.model.Formularios;
import com.franjazul.api.repository.PermisosRepository;
import com.franjazul.api.repository.PerfilesRepository;
import com.franjazul.api.repository.FormulariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisosService {

    @Autowired
    private PermisosRepository permisoRepository;

    @Autowired
    private PerfilesRepository perfilRepository;

    @Autowired
    private FormulariosRepository formularioRepository;

    // Obtener un permiso por ID compuesto
    public Optional<Permisos> obtenerPorId(Integer idPerfil, Integer idFormulario) {
        PermisoId id = new PermisoId(idPerfil, idFormulario);
        return permisoRepository.findById(id);
    }

    // Obtener todos los permisos
    public List<Permisos> obtenerTodos() {
        return permisoRepository.findAll();
    }

    // Obtener permisos por perfil
    public List<Permisos> obtenerPorPerfil(Integer idPerfil) {
        return permisoRepository.findByIdPerEnPerm(idPerfil);
    }

    // Obtener permisos por formulario
    public List<Permisos> obtenerPorFormulario(Integer idFormulario) {
        return permisoRepository.findByIdFormEnPerm(idFormulario);
    }

    // Crear un nuevo permiso
    public Permisos crear(Permisos permiso) {
        // Validar que el perfil exista
        Integer idPerfil = permiso.getIdPerEnPerm();
        if (idPerfil == null) {
            throw new RuntimeException("Debe especificar un perfil");
        }

        Optional<Perfiles> perfilExiste = perfilRepository.findById(idPerfil);
        if (!perfilExiste.isPresent()) {
            throw new RuntimeException("El perfil especificado no existe");
        }

        // Validar que el formulario exista
        Integer idFormulario = permiso.getIdFormEnPerm();
        if (idFormulario == null) {
            throw new RuntimeException("Debe especificar un formulario");
        }

        Optional<Formularios> formularioExiste = formularioRepository.findById(idFormulario);
        if (!formularioExiste.isPresent()) {
            throw new RuntimeException("El formulario especificado no existe");
        }

        // Validar que el permiso no exista ya
        Optional<Permisos> permisoExistente = permisoRepository.findByPerfilAndFormulario(idPerfil, idFormulario);
        if (permisoExistente.isPresent()) {
            throw new RuntimeException("Ya existe un permiso para este perfil y formulario");
        }

        // Establecer las relaciones
        permiso.setPerfil(perfilExiste.get());
        permiso.setFormulario(formularioExiste.get());

        return permisoRepository.save(permiso);
    }

    // Actualizar un permiso existente
    public Permisos actualizar(Integer idPerfil, Integer idFormulario, Permisos permisoActualizado) {
        PermisoId id = new PermisoId(idPerfil, idFormulario);
        Optional<Permisos> permisoOptional = permisoRepository.findById(id);

        if (!permisoOptional.isPresent()) {
            throw new RuntimeException("Permiso no encontrado");
        }

        Permisos permisoExistente = permisoOptional.get();

        // Actualizar solo los campos de permisos (no se pueden cambiar las llaves)
        if (permisoActualizado.getPuedeCrear() != null) {
            permisoExistente.setPuedeCrear(permisoActualizado.getPuedeCrear());
        }
        if (permisoActualizado.getPuedeBorrar() != null) {
            permisoExistente.setPuedeBorrar(permisoActualizado.getPuedeBorrar());
        }
        if (permisoActualizado.getPuedeEditar() != null) {
            permisoExistente.setPuedeEditar(permisoActualizado.getPuedeEditar());
        }
        if (permisoActualizado.getPuedeLeer() != null) {
            permisoExistente.setPuedeLeer(permisoActualizado.getPuedeLeer());
        }

        return permisoRepository.save(permisoExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer idPerfil, Integer idFormulario) {
        PermisoId id = new PermisoId(idPerfil, idFormulario);

        if (!permisoRepository.existsById(id)) {
            throw new RuntimeException("Permiso no encontrado");
        }

        permisoRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un permiso
    public boolean existe(Integer idPerfil, Integer idFormulario) {
        PermisoId id = new PermisoId(idPerfil, idFormulario);
        return permisoRepository.existsById(id);
    }
}