package com.franjazul.api.controller;

import com.franjazul.api.model.Cargos;
import com.franjazul.api.services.CargosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cargos")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class CargosController {


    @Autowired
    private CargosService cargosService;

    // GET /api/cargos - Obtener todos los cargos
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Cargos> cargos = cargosService.obtenerTodos();
            response.put("success", true);
            response.put("data", cargos);
            response.put("message", "cargos obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener Cargos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //Obtener un cargo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Cargos> cargoOptional = cargosService.obtenerPorId(id);

            if (cargoOptional.isPresent()) {
                response.put("success", true);
                response.put("data", cargoOptional.get());
                response.put("message", "Cargo encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Cargo no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener control: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/cargos - Crear un nuevo cargo
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Cargos cargo) {
        Map<String, Object> response = new HashMap<>();

        try {
            Cargos nuevoCargo = cargosService.crear(cargo);
            response.put("success", true);
            response.put("data", nuevoCargo);
            response.put("message", "Cargo creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear cargo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/cargos/{id} - Actualizar un cargo
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable String id, @RequestBody Cargos cargo) {
        Map<String, Object> response = new HashMap<>();

        try {
            Cargos cargoActualizado = cargosService.actualizar(id, cargo);
            response.put("success", true);
            response.put("data", cargoActualizado);
            response.put("message", "Cargo actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el cargo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/roles/{id} - Borrado lógico de un cargo
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = cargosService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "El cargo fue eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el cargo");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar el cargo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
