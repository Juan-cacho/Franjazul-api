package com.franjazul.api.controller;

import com.franjazul.api.dto.SolicitudCertificadoDTO;
import com.franjazul.api.model.Certificados;
import com.franjazul.api.services.CertificadosService;
import com.franjazul.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificados")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class CertificadosController {

    @Autowired
    private CertificadosService certificadosService;

    @Autowired
    private JwtUtil jwtUtil;

    // GET /api/certificados - Obtener todos los certificados
    @GetMapping
    public ResponseEntity<Map<String, Object>> doGet() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Certificados> certificados = certificadosService.obtenerTodos();
            response.put("success", true);
            response.put("data", certificados);
            response.put("message", "Certificados obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener certificados: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/certificados/{codigoCer} - Obtener un certificado por código
    @GetMapping("/{codigoCer}")
    public ResponseEntity<Map<String, Object>> doGet(@PathVariable String codigoCer) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Certificados> certificadoOptional = certificadosService.obtenerPorId(codigoCer);

            if (certificadoOptional.isPresent()) {
                response.put("success", true);
                response.put("data", certificadoOptional.get());
                response.put("message", "Certificado encontrado");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Certificado no encontrado con código: " + codigoCer);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener certificado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/certificados - Crear un nuevo certificado
    @PostMapping
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody Certificados certificado) {
        Map<String, Object> response = new HashMap<>();

        try {
            Certificados nuevoCertificado = certificadosService.crear(certificado);
            response.put("success", true);
            response.put("data", nuevoCertificado);
            response.put("message", "Certificado creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear certificado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PATCH /api/certificados/{codigoCer} - Actualizar un certificado
    @PatchMapping("/{codigoCer}")
    public ResponseEntity<Map<String, Object>> doPatch(@PathVariable String codigoCer, @RequestBody Certificados certificado) {
        Map<String, Object> response = new HashMap<>();

        try {
            Certificados certificadoActualizado = certificadosService.actualizar(codigoCer, certificado);
            response.put("success", true);
            response.put("data", certificadoActualizado);
            response.put("message", "Certificado actualizado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar certificado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/certificados/{codigoCer} - Borrado físico de un certificado
    @DeleteMapping("/{codigoCer}")
    public ResponseEntity<Map<String, Object>> doDelete(@PathVariable String codigoCer) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean eliminado = certificadosService.borrar(codigoCer);

            if (eliminado) {
                response.put("success", true);
                response.put("message", "Certificado eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo eliminar el certificado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar certificado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/certificados/cita/{citaId} - Obtener certificados por cita
    @GetMapping("/cita/{citaId}")
    public ResponseEntity<Map<String, Object>> obtenerPorCita(@PathVariable Integer citaId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Certificados> certificados = certificadosService.obtenerPorCita(citaId);
            response.put("success", true);
            response.put("data", certificados);
            response.put("message", "Certificados obtenidos por cita exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener certificados por cita: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/certificados/vencidos - Obtener certificados vencidos
    @GetMapping("/vencidos")
    public ResponseEntity<Map<String, Object>> obtenerVencidos() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Certificados> certificados = certificadosService.obtenerVencidos();
            response.put("success", true);
            response.put("data", certificados);
            response.put("message", "Certificados vencidos obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener certificados vencidos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/certificados/vigentes - Obtener certificados vigentes
    @GetMapping("/vigentes")
    public ResponseEntity<Map<String, Object>> obtenerVigentes() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Certificados> certificados = certificadosService.obtenerVigentes();
            response.put("success", true);
            response.put("data", certificados);
            response.put("message", "Certificados vigentes obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener certificados vigentes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }






    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //>>>>>>>>>>>>>>Endpoint Solicitar Certificado<<<<<<<<<<<<<<<
    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





    @PostMapping("/generar")
    public ResponseEntity<?> generarCertificado(@RequestBody SolicitudCertificadoDTO solicitud,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Extraer el token del header (formato: "Bearer TOKEN")
            String token = authHeader.replace("Bearer ", "");

            // Validar el token
            if (!jwtUtil.validateToken(token)) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Token inválido o expirado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            // Generar el PDF
            byte[] pdfBytes = certificadosService.generarCertificadoPDF(solicitud, token);

            // Configurar headers para descarga
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "certificado_franjazul.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


}
