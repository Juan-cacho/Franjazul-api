package com.franjazul.api.controller;

import com.franjazul.api.model.Rolles;
import com.franjazul.api.services.RollesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RollesController {

    @Autowired
    private RollesService rolService;

    // GET /api/roles - Obtener todos los roles
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Rolles> roles = rolService.obtenerTodos();
            response.put("success", true);
            response.put("data", roles);
            response.put("message", "Roles obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener roles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //Obtener un rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Rolles> rolOptional = rolService.obtenerPorId(id);

            if (rolOptional.isPresent()) {
                response.put("success", true);
                response.put("data", rolOptional.get());
                response.put("message", "Rol encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Rol no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/roles - Crear un nuevo rol
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Rolles rol) {
        Map<String, Object> response = new HashMap<>();

        try {
            Rolles nuevoRol = rolService.crear(rol);
            response.put("success", true);
            response.put("data", nuevoRol);
            response.put("message", "Rol creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/roles/{id} - Actualizar un rol
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer id, @RequestBody Rolles rol) {
        Map<String, Object> response = new HashMap<>();

        try {
            Rolles rolActualizado = rolService.actualizar(id, rol);
            response.put("success", true);
            response.put("data", rolActualizado);
            response.put("message", "Rol actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/roles/{id} - Borrado lógico de un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = rolService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Rol eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el rol");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}