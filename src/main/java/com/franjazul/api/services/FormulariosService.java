package com.franjazul.api.services;

import com.franjazul.api.model.Formularios;
import com.franjazul.api.repository.FormulariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormulariosService {

    @Autowired
    private FormulariosRepository formularioRepository;

    // Obtener un formulario por ID
    public Optional<Formularios> obtenerPorId(Integer id) {
        return formularioRepository.findById(id);
    }

    // Obtener todos los formularios
    public List<Formularios> obtenerTodos() {
        return formularioRepository.findAll();
    }

    // Crear un nuevo formulario
    public Formularios crear(Formularios formulario) {
        formulario.setIdForm(null); // Se hace el id null para que lo maneje el autoincremento de la base de datos

        // Validar que el título no exista
        Optional<Formularios> formularioExistente = formularioRepository.findByTituloForm(formulario.getTituloForm());
        if (formularioExistente.isPresent()) {
            throw new RuntimeException("Ya existe un formulario con ese título");
        }

        // Validar formulario recursivo si existe
        if (formulario.getFormRecursivo() != null) {
            Integer idFormRecursivo = formulario.getFormRecursivo().getIdForm();
            if (idFormRecursivo != null) {
                Optional<Formularios> formRecursivoExiste = formularioRepository.findById(idFormRecursivo);
                if (!formRecursivoExiste.isPresent()) {
                    throw new RuntimeException("El formulario padre especificado no existe");
                }
                formulario.setFormRecursivo(formRecursivoExiste.get());
            }
        }

        return formularioRepository.save(formulario);
    }


    // Actualizar un formulario existente
    public Formularios actualizar(Integer id, Formularios formularioActualizado) {
        Optional<Formularios> formularioOptional = formularioRepository.findById(id);

        if (!formularioOptional.isPresent()) {
            throw new RuntimeException("Formulario no encontrado con ID: " + id);
        }

        Formularios formularioExistente = formularioOptional.get();

        // Actualizar campos si se enviaron
        if (formularioActualizado.getTituloForm() != null) {
            formularioExistente.setTituloForm(formularioActualizado.getTituloForm());
        }
        if (formularioActualizado.getUrlForm() != null) {
            formularioExistente.setUrlForm(formularioActualizado.getUrlForm());
        }
        if (formularioActualizado.getEsPadre() != null) {
            formularioExistente.setEsPadre(formularioActualizado.getEsPadre());
        }
        if (formularioActualizado.getOrden() != null) {
            formularioExistente.setOrden(formularioActualizado.getOrden());
        }

        // Actualizar formulario recursivo si se proporciona
        if (formularioActualizado.getFormRecursivo() != null) {
            Integer idFormRecursivo = formularioActualizado.getFormRecursivo().getIdForm();
            if (idFormRecursivo != null) {
                Optional<Formularios> formRecursivoExiste = formularioRepository.findById(idFormRecursivo);
                if (!formRecursivoExiste.isPresent()) {
                    throw new RuntimeException("El formulario padre especificado no existe");
                }
                formularioExistente.setFormRecursivo(formRecursivoExiste.get());
            }
        }

        return formularioRepository.save(formularioExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer id) {
        if (!formularioRepository.existsById(id)) {
            throw new RuntimeException("Formulario no encontrado con ID: " + id);
        }

        formularioRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un formulario
    public boolean existe(Integer id) {
        return formularioRepository.existsById(id);
    }
}