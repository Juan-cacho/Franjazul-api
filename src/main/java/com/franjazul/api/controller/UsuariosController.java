package com.franjazul.api.controller;

import com.franjazul.api.dto.CambioPasswordRequest;
import com.franjazul.api.dto.LoginRequest;
import com.franjazul.api.dto.LoginResponse;
import com.franjazul.api.dto.RegistroRequest;
import com.franjazul.api.model.Usuarios;
import com.franjazul.api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /api/usuarios - Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Usuarios> usuarios = usuarioService.obtenerTodos();
            response.put("success", true);
            response.put("data", usuarios);
            response.put("message", "Usuarios obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener usuarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/usuarios/{id} - Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Usuarios> usuarioOptional = usuarioService.obtenerPorId(id);

            if (usuarioOptional.isPresent()) {
                response.put("success", true);
                response.put("data", usuarioOptional.get());
                response.put("message", "Usuario encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Usuario no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/usuarios/por-cargo/{nombreCargo} - Obtener usuarios por cargo
    @GetMapping("/por-cargo/{nombreCargo}")
    public ResponseEntity<Map<String, Object>> obtenerPorCargo(@PathVariable String nombreCargo) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Usuarios> usuarios = usuarioService.obtenerPorCargo(nombreCargo);
            response.put("success", true);
            response.put("data", usuarios);
            response.put("message", "Usuarios obtenidos por cargo exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener usuarios por cargo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/usuarios - Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Usuarios usuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            Usuarios nuevoUsuario = usuarioService.crear(usuario);
            response.put("success", true);
            response.put("data", nuevoUsuario);
            response.put("message", "Usuario creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/usuarios/{id} - Actualizar un usuario
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable String id, @RequestBody Usuarios usuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            Usuarios usuarioActualizado = usuarioService.actualizar(id, usuario);
            response.put("success", true);
            response.put("data", usuarioActualizado);
            response.put("message", "Usuario actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/usuarios/{id} - Borrar físicamente un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = usuarioService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Usuario eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el usuario");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/usuarios/login - Validar credenciales de login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            LoginResponse loginResponse = usuarioService.login(loginRequest);
            response.put("success", true);
            response.put("data", loginResponse);
            response.put("message", "Login exitoso");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al procesar login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //post /api/usuarios/registrar
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrar(@RequestBody RegistroRequest registroRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            LoginResponse loginResponse = usuarioService.registrar(registroRequest);
            response.put("success", true);
            response.put("data", loginResponse);
            response.put("message", "Usuario registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<Map<String, Object>> cambiarPassword(@RequestBody CambioPasswordRequest cambioRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean cambiado = usuarioService.cambiarPassword(cambioRequest);
            if (cambiado) {
                response.put("success", true);
                response.put("message", "Contraseña cambiada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo cambiar la contraseña");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al cambiar contraseña: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}