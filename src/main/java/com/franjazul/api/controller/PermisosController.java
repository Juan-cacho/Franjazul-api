package com.franjazul.api.controller;

import com.franjazul.api.model.Permisos;
import com.franjazul.api.services.PermisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permisos")
@CrossOrigin(origins = "http://localhost:4200")
public class PermisosController {

    @Autowired
    private PermisosService permisoService;

    // GET /api/permisos - Obtener todos los permisos
    @GetMapping
    public ResponseEntity<RespuestaGenerica> doGet() {
        RespuestaGenerica response = new RespuestaGenerica();

        try {
            List<Permisos> permisos = permisoService.obtenerTodos();
            response.setSuccess(true);
            response.setData(permisos);
            response.setMessage("Permisos obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al obtener permisos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/permisos/{idPerfil}/{idFormulario} - Obtener un permiso específico
    @GetMapping("/{idPerfil}/{idFormulario}")
    public ResponseEntity<RespuestaGenerica> doGet(@PathVariable Integer idPerfil,
                                                   @PathVariable Integer idFormulario) {
        RespuestaGenerica response = new RespuestaGenerica();

        try {
            Optional<Permisos> permisoOptional = permisoService.obtenerPorId(idPerfil, idFormulario);

            if (permisoOptional.isPresent()) {
                response.setSuccess(true);
                response.setData(permisoOptional.get());
                response.setMessage("Permiso encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.setSuccess(false);
                response.setMessage("Permiso no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al obtener permiso: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/permisos/perfil/{idPerfil} - Obtener permisos por perfil
    @GetMapping("/perfil/{idPerfil}")
    public ResponseEntity<RespuestaGenerica> obtenerPorPerfil(@PathVariable Integer idPerfil) {
        RespuestaGenerica response = new RespuestaGenerica();

        try {
            List<Permisos> permisos = permisoService.obtenerPorPerfil(idPerfil);
            response.setSuccess(true);
            response.setData(permisos);
            response.setMessage("Permisos del perfil obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al obtener permisos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/permisos/formulario/{idFormulario} - Obtener permisos por formulario
    @GetMapping("/formulario/{idFormulario}")
    public ResponseEntity<RespuestaGenerica> obtenerPorFormulario(@PathVariable Integer idFormulario) {
        RespuestaGenerica response = new RespuestaGenerica();

        try {
            List<Permisos> permisos = permisoService.obtenerPorFormulario(idFormulario);
            response.setSuccess(true);
            response.setData(permisos);
            response.setMessage("Permisos del formulario obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al obtener permisos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/permisos - Crear un nuevo permiso
    @PostMapping
    public ResponseEntity<RespuestaGenerica> doPost(@RequestBody Permisos permiso) {
        RespuestaGenerica response = new RespuestaGenerica();

        try {
            Permisos nuevoPermiso = permisoService.crear(permiso);
            response.setSuccess(true);
            response.setData(nuevoPermiso);
            response.setMessage("Permiso creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al crear permiso: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/permisos/{idPerfil}/{idFormulario} - Actualizar un permiso
    @PatchMapping("/{idPerfil}/{idFormulario}")
    public ResponseEntity<RespuestaGenerica> doPatch(@PathVariable Integer idPerfil,
                                                     @PathVariable Integer idFormulario,
                                                     @RequestBody Permisos permiso) {
        RespuestaGenerica response = new RespuestaGenerica();

        try {
            Permisos permisoActualizado = permisoService.actualizar(idPerfil, idFormulario, permiso);
            response.setSuccess(true);
            response.setData(permisoActualizado);
            response.setMessage("Permiso actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al actualizar permiso: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/permisos/{idPerfil}/{idFormulario} - Borrar físicamente un permiso
    @DeleteMapping("/{idPerfil}/{idFormulario}")
    public ResponseEntity<RespuestaGenerica> doDelete(@PathVariable Integer idPerfil,
                                                      @PathVariable Integer idFormulario) {
        RespuestaGenerica response = new RespuestaGenerica();

        try {
            boolean eliminado = permisoService.borrar(idPerfil, idFormulario);

            if (eliminado) {
                response.setSuccess(true);
                response.setMessage("Permiso eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.setSuccess(false);
                response.setMessage("No se pudo eliminar el permiso");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al eliminar permiso: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Clase interna para la respuesta
    public static class RespuestaGenerica {
        private boolean success;
        private Object data;
        private String message;

        public RespuestaGenerica() {
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}