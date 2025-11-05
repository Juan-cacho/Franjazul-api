package com.franjazul.api.controller;

import com.franjazul.api.model.FranjasHorarias;
import com.franjazul.api.services.FranjasHorariasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/franjas-horarias")
@CrossOrigin(origins = "http://localhost:4200")
public class FranjasHorariasController {

    @Autowired
    private FranjasHorariasService franjasHorariasService;

    // GET /api/franjas-horarias - Obtener todas las franjas horarias
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<FranjasHorarias> franjasHorarias = franjasHorariasService.obtenerTodos();
            response.put("success", true);
            response.put("data", franjasHorarias);
            response.put("message", "Franjas horarias obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener franjas horarias: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/franjas-horarias/{id} - Obtener una franja horaria por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<FranjasHorarias> franjaHorariaOptional = franjasHorariasService.obtenerPorId(id);

            if (franjaHorariaOptional.isPresent()) {
                response.put("success", true);
                response.put("data", franjaHorariaOptional.get());
                response.put("message", "Franja horaria encontrada");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Franja horaria no encontrada con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener franja horaria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/franjas-horarias - Crear una nueva franja horaria
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody FranjasHorarias franjaHoraria) {
        Map<String, Object> response = new HashMap<>();

        try {
            FranjasHorarias nuevaFranjaHoraria = franjasHorariasService.crear(franjaHoraria);
            response.put("success", true);
            response.put("data", nuevaFranjaHoraria);
            response.put("message", "Franja horaria creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear franja horaria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/franjas-horarias/{id} - Actualizar una franja horaria
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer id, @RequestBody FranjasHorarias franjaHoraria) {
        Map<String, Object> response = new HashMap<>();

        try {
            FranjasHorarias franjaHorariaActualizada = franjasHorariasService.actualizar(id, franjaHoraria);
            response.put("success", true);
            response.put("data", franjaHorariaActualizada);
            response.put("message", "Franja horaria actualizada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar franja horaria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/franjas-horarias/{id} - Borrado físico de una franja horaria
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = franjasHorariasService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Franja horaria eliminada correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar la franja horaria");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar franja horaria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
