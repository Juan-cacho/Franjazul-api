package com.franjazul.api.controller;

import com.franjazul.api.model.TipoServicio;
import com.franjazul.api.services.TipoServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-servicio")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoServicioController {

    @Autowired
    private TipoServicioService tipoServicioService;

    // GET /api/tipos-servicio - Obtener todos los tipos de servicio
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<TipoServicio> tiposServicio = tipoServicioService.obtenerTodos();
            response.put("success", true);
            response.put("data", tiposServicio);
            response.put("message", "Tipos de servicio obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener tipos de servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/tipos-servicio/{nombreTps} - Obtener un tipo de servicio por nombre
    @GetMapping("/{nombreTps}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable String nombreTps) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<TipoServicio> tipoServicioOptional = tipoServicioService.obtenerPorId(nombreTps);

            if (tipoServicioOptional.isPresent()) {
                response.put("success", true);
                response.put("data", tipoServicioOptional.get());
                response.put("message", "Tipo de servicio encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Tipo de servicio no encontrado con nombre: " + nombreTps);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener tipo de servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/tipos-servicio - Crear un nuevo tipo de servicio
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody TipoServicio tipoServicio) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoServicio nuevoTipoServicio = tipoServicioService.crear(tipoServicio);
            response.put("success", true);
            response.put("data", nuevoTipoServicio);
            response.put("message", "Tipo de servicio creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear tipo de servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/tipos-servicio/{nombreTps} - Actualizar un tipo de servicio
    @PatchMapping("/{nombreTps}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable String nombreTps, @RequestBody TipoServicio tipoServicio) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoServicio tipoServicioActualizado = tipoServicioService.actualizar(nombreTps, tipoServicio);
            response.put("success", true);
            response.put("data", tipoServicioActualizado);
            response.put("message", "Tipo de servicio actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar tipo de servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/tipos-servicio/{nombreTps} - Borrado físico de un tipo de servicio
    @DeleteMapping("/{nombreTps}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable String nombreTps) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = tipoServicioService.borrar(nombreTps);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Tipo de servicio eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el tipo de servicio");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar tipo de servicio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
