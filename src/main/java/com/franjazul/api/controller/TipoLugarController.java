package com.franjazul.api.controller;

import com.franjazul.api.model.TipoLugar;
import com.franjazul.api.services.TipoLugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-lugar")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoLugarController {

    @Autowired
    private TipoLugarService tipoLugarService;

    // GET /api/tipos-lugar - Obtener todos los tipos de lugar
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<TipoLugar> tiposLugar = tipoLugarService.obtenerTodos();
            response.put("success", true);
            response.put("data", tiposLugar);
            response.put("message", "Tipos de lugar obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener tipos de lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/tipos-lugar/{id} - Obtener un tipo de lugar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<TipoLugar> tipoLugarOptional = tipoLugarService.obtenerPorId(id);

            if (tipoLugarOptional.isPresent()) {
                response.put("success", true);
                response.put("data", tipoLugarOptional.get());
                response.put("message", "Tipo de lugar encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Tipo de lugar no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener tipo de lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/tipos-lugar - Crear un nuevo tipo de lugar
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody TipoLugar tipoLugar) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoLugar nuevoTipoLugar = tipoLugarService.crear(tipoLugar);
            response.put("success", true);
            response.put("data", nuevoTipoLugar);
            response.put("message", "Tipo de lugar creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear tipo de lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/tipos-lugar/{id} - Actualizar un tipo de lugar
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer id, @RequestBody TipoLugar tipoLugar) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoLugar tipoLugarActualizado = tipoLugarService.actualizar(id, tipoLugar);
            response.put("success", true);
            response.put("data", tipoLugarActualizado);
            response.put("message", "Tipo de lugar actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar tipo de lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/tipos-lugar/{id} - Borrado físico de un tipo de lugar
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = tipoLugarService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Tipo de lugar eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el tipo de lugar");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar tipo de lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
