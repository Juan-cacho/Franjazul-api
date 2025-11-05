package com.franjazul.api.controller;

import com.franjazul.api.model.Servicios;
import com.franjazul.api.services.ServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiciosController {

    @Autowired
    private ServiciosService serviciosService;

    // GET /api/servicios - Obtener todos los servicios
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Servicios> servicios = serviciosService.obtenerTodos();
            response.put("success", true);
            response.put("data", servicios);
            response.put("message", "Servicios obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener servicios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/servicios/{id} - Obtener un servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Servicios> servicioOptional = serviciosService.obtenerPorId(id);

            if (servicioOptional.isPresent()) {
                response.put("success", true);
                response.put("data", servicioOptional.get());
                response.put("message", "Servicio encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Servicio no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/servicios - Crear un nuevo servicio
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Servicios servicio) {
        Map<String, Object> response = new HashMap<>();

        try {
            Servicios nuevoServicio = serviciosService.crear(servicio);
            response.put("success", true);
            response.put("data", nuevoServicio);
            response.put("message", "Servicio creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/servicios/{id} - Actualizar un servicio
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer id, @RequestBody Servicios servicio) {
        Map<String, Object> response = new HashMap<>();

        try {
            Servicios servicioActualizado = serviciosService.actualizar(id, servicio);
            response.put("success", true);
            response.put("data", servicioActualizado);
            response.put("message", "Servicio actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/servicios/{id} - Borrado físico de un servicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = serviciosService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Servicio eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el servicio");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/servicios/tipo/{nombreTps} - Obtener servicios por tipo
    @GetMapping("/tipo/{nombreTps}")
    public ResponseEntity<Map<String, Object>> obtenerPorTipo(@PathVariable String nombreTps) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Servicios> servicios = serviciosService.obtenerPorTipo(nombreTps);
            response.put("success", true);
            response.put("data", servicios);
            response.put("message", "Servicios obtenidos por tipo exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener servicios por tipo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/servicios/molecula/{nombreMol} - Obtener servicios por molécula
    @GetMapping("/molecula/{nombreMol}")
    public ResponseEntity<Map<String, Object>> obtenerPorMolecula(@PathVariable String nombreMol) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Servicios> servicios = serviciosService.obtenerPorMolecula(nombreMol);
            response.put("success", true);
            response.put("data", servicios);
            response.put("message", "Servicios obtenidos por molécula exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener servicios por molécula: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
