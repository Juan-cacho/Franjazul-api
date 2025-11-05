package com.franjazul.api.controller;

import com.franjazul.api.model.CitaServicio;
import com.franjazul.api.services.CitaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cita-servicio")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaServicioController {

    @Autowired
    private CitaServicioService citaServicioService;

    // GET /api/cita-servicio - Obtener todas las relaciones cita-servicio
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CitaServicio> citasServicios = citaServicioService.obtenerTodos();
            response.put("success", true);
            response.put("data", citasServicios);
            response.put("message", "Relaciones cita-servicio obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener relaciones cita-servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/cita-servicio/{citaId}/{servicioId} - Obtener una relación por ID compuesto
    @GetMapping("/{citaId}/{servicioId}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer citaId, @PathVariable Integer servicioId) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<CitaServicio> citaServicioOptional = citaServicioService.obtenerPorId(citaId, servicioId);

            if (citaServicioOptional.isPresent()) {
                response.put("success", true);
                response.put("data", citaServicioOptional.get());
                response.put("message", "Relación cita-servicio encontrada");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Relación cita-servicio no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener relación cita-servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/cita-servicio - Crear una nueva relación cita-servicio
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody CitaServicio citaServicio) {
        Map<String, Object> response = new HashMap<>();

        try {
            CitaServicio nuevaCitaServicio = citaServicioService.crear(citaServicio);
            response.put("success", true);
            response.put("data", nuevaCitaServicio);
            response.put("message", "Relación cita-servicio creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear relación cita-servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/cita-servicio/{citaId}/{servicioId} - Actualizar una relación cita-servicio
    @PatchMapping("/{citaId}/{servicioId}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer citaId,
                                                       @PathVariable Integer servicioId,
                                                       @RequestBody CitaServicio citaServicio) {
        Map<String, Object> response = new HashMap<>();

        try {
            CitaServicio citaServicioActualizado = citaServicioService.actualizar(citaId, servicioId, citaServicio);
            response.put("success", true);
            response.put("data", citaServicioActualizado);
            response.put("message", "Relación cita-servicio actualizada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar relación cita-servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/cita-servicio/{citaId}/{servicioId} - Borrado físico de una relación
    @DeleteMapping("/{citaId}/{servicioId}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer citaId, @PathVariable Integer servicioId) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = citaServicioService.borrar(citaId, servicioId);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Relación cita-servicio eliminada correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar la relación cita-servicio");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar relación cita-servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/cita-servicio/cita/{citaId} - Obtener servicios de una cita
    @GetMapping("/cita/{citaId}")
    public ResponseEntity<Map<String, Object>> obtenerServiciosDeCita(@PathVariable Integer citaId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CitaServicio> servicios = citaServicioService.obtenerServiciosDeCita(citaId);
            response.put("success", true);
            response.put("data", servicios);
            response.put("message", "Servicios de la cita obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener servicios de la cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/cita-servicio/servicio/{servicioId} - Obtener citas con un servicio
    @GetMapping("/servicio/{servicioId}")
    public ResponseEntity<Map<String, Object>> obtenerCitasConServicio(@PathVariable Integer servicioId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CitaServicio> citas = citaServicioService.obtenerCitasConServicio(servicioId);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas con el servicio obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas con el servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
