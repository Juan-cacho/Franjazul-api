package com.franjazul.api.controller;

import com.franjazul.api.dto.*;
import com.franjazul.api.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // GET /api/dashboard/estadisticas - Obtener estadísticas generales
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> response = new HashMap<>();

        try {
            DashboardStatsDTO stats = dashboardService.obtenerEstadisticasDashboard();
            response.put("success", true);
            response.put("data", stats);
            response.put("message", "Estadísticas obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener estadísticas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/dashboard/citas-por-estado - Obtener citas agrupadas por estado
    @GetMapping("/citas-por-estado")
    public ResponseEntity<Map<String, Object>> obtenerCitasPorEstado() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CitasPorEstadoDTO> citas = dashboardService.obtenerCitasPorEstado();
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas por estado obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas por estado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/dashboard/rendimiento-tecnicos - Obtener rendimiento de técnicos
    @GetMapping("/rendimiento-tecnicos")
    public ResponseEntity<Map<String, Object>> obtenerRendimientoTecnicos() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CitasPorTecnicoDTO> tecnicos = dashboardService.obtenerRendimientoTecnicos();
            response.put("success", true);
            response.put("data", tecnicos);
            response.put("message", "Rendimiento de técnicos obtenido exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener rendimiento de técnicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/dashboard/citas-tecnico/{idTecnico} - Obtener citas de un técnico
    @GetMapping("/citas-tecnico/{idTecnico}")
    public ResponseEntity<Map<String, Object>> obtenerCitasPorTecnico(@PathVariable String idTecnico) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CitaDetalleDTO> citas = dashboardService.obtenerCitasPorTecnico(idTecnico);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Citas del técnico obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener citas del técnico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/dashboard/certificados-por-vencer - Obtener certificados próximos a vencer
    @GetMapping("/certificados-por-vencer")
    public ResponseEntity<Map<String, Object>> obtenerCertificadosPorVencer(
            @RequestParam(required = false) Integer dias) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CertificadoPorVencerDTO> certificados = dashboardService.obtenerCertificadosPorVencer(dias);
            response.put("success", true);
            response.put("data", certificados);
            response.put("message", "Certificados por vencer obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener certificados por vencer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // NUEVO ENDPOINT: GET /api/dashboard/indicadores-cumplimiento
    @GetMapping("/indicadores-cumplimiento")
    public ResponseEntity<Map<String, Object>> obtenerIndicadoresCumplimiento() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<IndicadorCumplimientoDTO> indicadores = dashboardService.obtenerIndicadoresCumplimiento();
            response.put("success", true);
            response.put("data", indicadores);
            response.put("message", "Indicadores de cumplimiento obtenidos exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener indicadores: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // NUEVO ENDPOINT: GET /api/dashboard/proximas-citas
    @GetMapping("/proximas-citas")
    public ResponseEntity<Map<String, Object>> obtenerProximasCitas(
            @RequestParam(required = false) Integer cantidad) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<ProximaCitaDTO> citas = dashboardService.obtenerProximasCitas(cantidad);
            response.put("success", true);
            response.put("data", citas);
            response.put("message", "Próximas citas obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener próximas citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
