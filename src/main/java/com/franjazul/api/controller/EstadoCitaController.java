package com.franjazul.api.controller;

import com.franjazul.api.model.EstadoCita;
import com.franjazul.api.services.EstadoCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estado-cita")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoCitaController {

    @Autowired
    private EstadoCitaService estadoCitaService;

    // GET /api/estados-cita - Obtener todos los estados de cita
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<EstadoCita> estadosCita = estadoCitaService.obtenerTodos();
            response.put("success", true);
            response.put("data", estadosCita);
            response.put("message", "Estados de cita obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener estados de cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/estados-cita/{nombreEc} - Obtener un estado de cita por nombre
    @GetMapping("/{nombreEc}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable String nombreEc) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<EstadoCita> estadoCitaOptional = estadoCitaService.obtenerPorId(nombreEc);

            if (estadoCitaOptional.isPresent()) {
                response.put("success", true);
                response.put("data", estadoCitaOptional.get());
                response.put("message", "Estado de cita encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Estado de cita no encontrado con nombre: " + nombreEc);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener estado de cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/estados-cita - Crear un nuevo estado de cita
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody EstadoCita estadoCita) {
        Map<String, Object> response = new HashMap<>();

        try {
            EstadoCita nuevoEstadoCita = estadoCitaService.crear(estadoCita);
            response.put("success", true);
            response.put("data", nuevoEstadoCita);
            response.put("message", "Estado de cita creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear estado de cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/estados-cita/{nombreEc} - Actualizar un estado de cita
    @PatchMapping("/{nombreEc}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable String nombreEc, @RequestBody EstadoCita estadoCita) {
        Map<String, Object> response = new HashMap<>();

        try {
            EstadoCita estadoCitaActualizado = estadoCitaService.actualizar(nombreEc, estadoCita);
            response.put("success", true);
            response.put("data", estadoCitaActualizado);
            response.put("message", "Estado de cita actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar estado de cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/estados-cita/{nombreEc} - Borrado físico de un estado de cita
    @DeleteMapping("/{nombreEc}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable String nombreEc) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = estadoCitaService.borrar(nombreEc);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Estado de cita eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el estado de cita");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar estado de cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
