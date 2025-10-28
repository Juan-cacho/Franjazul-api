package com.franjazul.api.controller;


import com.franjazul.api.model.Perfiles;
import com.franjazul.api.services.PerfilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfiles")
@CrossOrigin(origins = "http://localhost:4200")
public class PerfilesController {

    @Autowired
    private PerfilesService perfilService;

    // GET /api/perfiles - Obtener todos los perfiles
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Perfiles> perfiles = perfilService.obtenerTodos();
            response.put("success", true);
            response.put("data", perfiles);
            response.put("message", "Perfiles obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener perfiles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/perfiles/{id} - Obtener un perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Perfiles> perfilOptional = perfilService.obtenerPorId(id);

            if (perfilOptional.isPresent()) {
                response.put("success", true);
                response.put("data", perfilOptional.get());
                response.put("message", "Perfil encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Perfil no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener perfil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/perfiles - Crear un nuevo perfil
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Perfiles perfil) {
        Map<String, Object> response = new HashMap<>();

        try {
            Perfiles nuevoPerfil = perfilService.crear(perfil);
            response.put("success", true);
            response.put("data", nuevoPerfil);
            response.put("message", "Perfil creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear perfil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/perfiles/{id} - Actualizar un perfil
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer id, @RequestBody Perfiles perfil) {
        Map<String, Object> response = new HashMap<>();

        try {
            Perfiles perfilActualizado = perfilService.actualizar(id, perfil);
            response.put("success", true);
            response.put("data", perfilActualizado);
            response.put("message", "Perfil actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar perfil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/perfiles/{id} - Borrado lógico de un perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = perfilService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Perfil eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el perfil");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar perfil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // GET /api/perfiles/rol/{idRol} - Obtener perfiles por rol
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<Map<String, Object>> obtenerPorRol(@PathVariable Integer idRol) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Perfiles> perfiles = perfilService.obtenerPorRol(idRol);
            response.put("success", true);
            response.put("data", perfiles);
            response.put("message", "Perfiles obtenidos por rol exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener perfiles por rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}