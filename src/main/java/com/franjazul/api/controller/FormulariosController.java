package com.franjazul.api.controller;

import com.franjazul.api.model.Formularios;
import com.franjazul.api.services.FormulariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/formularios")
@CrossOrigin(origins = "http://localhost:4200")
public class FormulariosController {

    @Autowired
    private FormulariosService formularioService;

    private ResponseEntity<Map<String, Object>> response(HttpStatus status, boolean success, String message, Object data) {
        return ResponseEntity.status(status).body(Map.of(
                "success", success,
                "message", message,
                "data", data
        ));
    }

    private ResponseEntity<Map<String, Object>> response(HttpStatus status, boolean success, String message) {
        return response(status, success, message, null);
    }

    // GET /api/formularios - Obtener todos los formularios
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        try {
            var formularios = formularioService.obtenerTodos();
            return response(HttpStatus.OK, true, "Formularios obtenidos exitosamente", formularios);
        } catch (Exception e) {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error al obtener formularios: " + e.getMessage());
        }
    }

    // GET /api/formularios/{id} - Obtener un formulario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        try {
            return formularioService.obtenerPorId(id)
                    .map(f -> response(HttpStatus.OK, true, "Formulario encontrado", f))
                    .orElseGet(() -> response(HttpStatus.NOT_FOUND, false, "Formulario no encontrado con ID: " + id));
        } catch (Exception e) {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error al obtener formulario: " + e.getMessage());
        }
    }

    // POST /api/formularios - Crear un nuevo formulario
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Formularios formulario) {
        try {
            var nuevo = formularioService.crear(formulario);
            return response(HttpStatus.CREATED, true, "Formulario creado exitosamente", nuevo);
        } catch (RuntimeException e) {
            return response(HttpStatus.BAD_REQUEST, false, e.getMessage());
        } catch (Exception e) {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error al crear formulario: " + e.getMessage());
        }
    }

    // PATCH /api/formularios/{id} - Actualizar un formulario
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody Formularios formulario) {
        try {
            var actualizado = formularioService.actualizar(id, formulario);
            return response(HttpStatus.OK, true, "Formulario actualizado exitosamente", actualizado);
        } catch (RuntimeException e) {
            return response(HttpStatus.NOT_FOUND, false, e.getMessage());
        } catch (Exception e) {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error al actualizar formulario: " + e.getMessage());
        }
    }

    // DELETE /api/formularios/{id} - Borrar un formulario
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        try {
            boolean eliminado = formularioService.borrar(id);
            return eliminado
                    ? response(HttpStatus.OK, true, "Formulario eliminado correctamente")
                    : response(HttpStatus.INTERNAL_SERVER_ERROR, false, "No se pudo eliminar el formulario");
        } catch (RuntimeException e) {
            return response(HttpStatus.NOT_FOUND, false, e.getMessage());
        } catch (Exception e) {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error al eliminar formulario: " + e.getMessage());
        }
    }
}
