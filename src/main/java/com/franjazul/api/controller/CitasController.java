package com.franjazul.api.controller;

import com.franjazul.api.dto.SolicitudCitaRequest;
import com.franjazul.api.model.Citas;
import com.franjazul.api.services.CitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:4200")
public class CitasController {

    @Autowired
    private CitasService citasService;

    // GET /api/citas - Obtener todas las citas
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Citas> citas = citasService.obtenerTodos();
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/citas/{id} - Obtener una cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Citas> citaOptional = citasService.obtenerPorId(id);

            if (citaOptional.isPresent()) {
                response.put("success", true);
                response.put("data", citaOptional.get());
                response.put("message", "Cita encontrada");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Cita no encontrada con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/citas - Crear una nueva cita
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Citas cita) {
        Map<String, Object> response = new HashMap<>();

        try {
            Citas nuevaCita = citasService.crear(cita);
            response.put("success", true);
            response.put("data", nuevaCita);
            response.put("message", "Cita creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Map<String, Object>> solicitarCita(@RequestBody SolicitudCitaRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Citas citaCreada = citasService.solicitarCita(request);
            response.put("success", true);
            response.put("data", citaCreada);
            response.put("message", "Cita solicitada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al solicitar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // PATCH /api/citas/{id} - Actualizar una cita
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable Integer id, @RequestBody Citas cita) {
        Map<String, Object> response = new HashMap<>();

        try {
            Citas citaActualizada = citasService.actualizar(id, cita);
            response.put("success", true);
            response.put("data", citaActualizada);
            response.put("message", "Cita actualizada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/citas/{id} - Borrado físico de una cita
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = citasService.borrar(id);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Cita eliminada correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar la cita");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/citas/usuario-tecnico/{idUsuario} - Obtener citas por usuario técnico
    @GetMapping("/usuario-tecnico/{idUsuario}")
    public ResponseEntity<Map<String, Object>> obtenerPorUsuarioTecnico(@PathVariable String idUsuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Citas> citas = citasService.obtenerPorUsuarioTecnico(idUsuario);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas obtenidas por usuario técnico exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas por usuario técnico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/citas/usuario-creador/{idUsuario} - Obtener citas creadas por un usuario
    @GetMapping("/usuario-creador/{idUsuario}")
    public ResponseEntity<Map<String, Object>> obtenerPorUsuarioCreador(@PathVariable String idUsuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Citas> citas = citasService.obtenerPorUsuarioCreador(idUsuario);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas obtenidas por usuario creador exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas por usuario creador: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/citas/estado/{nombreEc} - Obtener citas por estado
    @GetMapping("/estado/{nombreEc}")
    public ResponseEntity<Map<String, Object>> obtenerPorEstado(@PathVariable String nombreEc) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Citas> citas = citasService.obtenerPorEstado(nombreEc);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas obtenidas por estado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas por estado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/citas/lugar/{idLugar} - Obtener citas por lugar
    @GetMapping("/lugar/{idLugar}")
    public ResponseEntity<Map<String, Object>> obtenerPorLugar(@PathVariable Integer idLugar) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Citas> citas = citasService.obtenerPorLugar(idLugar);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas obtenidas por lugar exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas por lugar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/citas/franja-horaria/{idFranja} - Obtener citas por franja horaria
    @GetMapping("/franja-horaria/{idFranja}")
    public ResponseEntity<Map<String, Object>> obtenerPorFranjaHoraria(@PathVariable Integer idFranja) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Citas> citas = citasService.obtenerPorFranjaHoraria(idFranja);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas obtenidas por franja horaria exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas por franja horaria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
