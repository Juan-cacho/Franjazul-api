package com.franjazul.api.controller;

import com.franjazul.api.model.Moleculas;
import com.franjazul.api.services.MoleculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/moleculas")
@CrossOrigin(origins = "http://localhost:4200")
public class MoleculasController {

    @Autowired
    private MoleculasService moleculasService;

    // GET /api/moleculas - Obtener todas las moléculas
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Moleculas> moleculas = moleculasService.obtenerTodos();
            response.put("success", true);
            response.put("data", moleculas);
            response.put("message", "Moléculas obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener moléculas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/moleculas/{nombreMol} - Obtener una molécula por nombre
    @GetMapping("/{nombreMol}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable String nombreMol) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Moleculas> moleculaOptional = moleculasService.obtenerPorId(nombreMol);

            if (moleculaOptional.isPresent()) {
                response.put("success", true);
                response.put("data", moleculaOptional.get());
                response.put("message", "Molécula encontrada");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Molécula no encontrada con nombre: " + nombreMol);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener molécula: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/moleculas - Crear una nueva molécula
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Moleculas molecula) {
        Map<String, Object> response = new HashMap<>();

        try {
            Moleculas nuevaMolecula = moleculasService.crear(molecula);
            response.put("success", true);
            response.put("data", nuevaMolecula);
            response.put("message", "Molécula creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear molécula: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/moleculas/{nombreMol} - Actualizar una molécula
    @PatchMapping("/{nombreMol}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable String nombreMol, @RequestBody Moleculas molecula) {
        Map<String, Object> response = new HashMap<>();

        try {
            Moleculas moleculaActualizada = moleculasService.actualizar(nombreMol, molecula);
            response.put("success", true);
            response.put("data", moleculaActualizada);
            response.put("message", "Molécula actualizada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar molécula: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/moleculas/{nombreMol} - Borrado físico de una molécula
    @DeleteMapping("/{nombreMol}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable String nombreMol) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = moleculasService.borrar(nombreMol);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Molécula eliminada correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar la molécula");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar molécula: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
