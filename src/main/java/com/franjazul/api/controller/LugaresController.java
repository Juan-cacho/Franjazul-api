package com.franjazul.api.controller;

import com.franjazul.api.model.Lugares;
import com.franjazul.api.services.LugaresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/lugares")
@CrossOrigin(origins = "http://localhost:4200")
public class LugaresController {

    @Autowired
    private LugaresService lugaresService;

    // GET /api/lugares - Obtener todos los lugares
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Lugares> lugares = lugaresService.obtenerTodos();
            response.put("success", true);
            response.put("data", lugares);
            response.put("message", "Lugares obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener lugares: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/lugares/{id} - Obtener un lugar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Lugares> lugarOptional = lugaresService.obtenerPorId(id);

            if (lugarOptional.isPresent()) {
                response.put("success", true);
                response.put("data", lugarOptional.get());
                response.put("message", "Lugar encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Lugar no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/lugares - Crear un nuevo lugar
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Lugares lugar) {
        Map<String, Object> response = new HashMap<>();

        try {
            Lugares nuevoLugar = lugaresService.crear(lugar);
            response.put("success", true);
            response.put("data", nuevoLugar);
            response.put("message", "Lugar creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/lugares/{id} - Actualizar un lugar
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer id, @RequestBody Lugares lugar) {
        Map<String, Object> response = new HashMap<>();

        try {
            Lugares lugarActualizado = lugaresService.actualizar(id, lugar);
            response.put("success", true);
            response.put("data", lugarActualizado);
            response.put("message", "Lugar actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/lugares/{id} - Borrado físico de un lugar
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = lugaresService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Lugar eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el lugar");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/lugares/tipo/{tipoId} - Obtener lugares por tipo
    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<Map<String, Object>> obtenerPorTipo(@PathVariable Integer tipoId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Lugares> lugares = lugaresService.obtenerPorTipo(tipoId);
            response.put("success", true);
            response.put("data", lugares);
            response.put("message", "Lugares obtenidos por tipo exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener lugares por tipo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/lugares/dentro/{lugarPadreId} - Obtener lugares dentro de otro lugar
    @GetMapping("/dentro/{lugarPadreId}")
    public ResponseEntity<Map<String, Object>> obtenerLugaresDentro(@PathVariable Integer lugarPadreId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Lugares> lugares = lugaresService.obtenerLugaresDentro(lugarPadreId);
            response.put("success", true);
            response.put("data", lugares);
            response.put("message", "Lugares obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener lugares: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
