package com.franjazul.api.services;

import com.franjazul.api.dto.ReporteCitaPorPeriodoDTO;
import com.franjazul.api.dto.ReporteProductividadTecnicoDTO;
import com.franjazul.api.dto.ReporteServiciosSolicitadosDTO;
import com.franjazul.api.repository.ReportesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportesService {

    @Autowired
    private ReportesRepository reportesRepository;

    // Reporte de citas por período
    public List<ReporteCitaPorPeriodoDTO> obtenerReporteCitasPorPeriodo(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            String estado,
            String idTecnico) {

        try {
            List<Object[]> resultados = reportesRepository.obtenerReporteCitasPorPeriodo(
                    fechaInicio, fechaFin, estado, idTecnico);

            if (resultados == null || resultados.isEmpty()) {
                return new ArrayList<>();
            }

            return resultados.stream().map(row -> {
                ReporteCitaPorPeriodoDTO dto = new ReporteCitaPorPeriodoDTO();
                dto.setIdCita(row[0] != null ? ((Number) row[0]).intValue() : null);
                dto.setFechaInicio(convertToLocalDateTime(row[1]));
                dto.setFechaFin(convertToLocalDateTime(row[2]));
                dto.setCliente(row[3] != null ? (String) row[3] : "");
                dto.setEmailCliente(row[4] != null ? (String) row[4] : "");
                dto.setTecnico(row[5] != null ? (String) row[5] : "");
                dto.setLugar(row[6] != null ? (String) row[6] : "");
                dto.setDireccion(row[7] != null ? (String) row[7] : "");
                dto.setServicios(row[8] != null ? (String) row[8] : "Sin servicios");
                dto.setEstado(row[9] != null ? (String) row[9] : "");
                dto.setObservaciones(row[10] != null ? (String) row[10] : "");
                return dto;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo reporte de citas: " + e.getMessage(), e);
        }
    }

    // Reporte de productividad de técnicos
    public List<ReporteProductividadTecnicoDTO> obtenerReporteProductividadTecnicos(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            String idTecnico) {

        try {
            List<Object[]> resultados = reportesRepository.obtenerReporteProductividadTecnicos(
                    fechaInicio, fechaFin, idTecnico);

            if (resultados == null || resultados.isEmpty()) {
                return new ArrayList<>();
            }

            return resultados.stream().map(row -> {
                ReporteProductividadTecnicoDTO dto = new ReporteProductividadTecnicoDTO();
                dto.setIdTecnico(row[0] != null ? (String) row[0] : "");
                dto.setNombreCompleto(row[1] != null ? (String) row[1] : "");
                dto.setEmail(row[2] != null ? (String) row[2] : "");
                dto.setTelefono(row[3] != null ? (String) row[3] : "");
                dto.setTotalCitasAsignadas(row[4] != null ? ((Number) row[4]).longValue() : 0L);
                dto.setCitasCompletadas(row[5] != null ? ((Number) row[5]).longValue() : 0L);
                dto.setCitasPendientes(row[6] != null ? ((Number) row[6]).longValue() : 0L);
                dto.setCitasCanceladas(row[7] != null ? ((Number) row[7]).longValue() : 0L);
                dto.setCitasReagendadas(row[8] != null ? ((Number) row[8]).longValue() : 0L);
                dto.setPorcentajeEfectividad(row[9] != null ? ((Number) row[9]).doubleValue() : 0.0);
                dto.setPorcentajeCancelacion(row[10] != null ? ((Number) row[10]).doubleValue() : 0.0);
                return dto;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo reporte de productividad: " + e.getMessage(), e);
        }
    }

    // Reporte de servicios más solicitados
    public List<ReporteServiciosSolicitadosDTO> obtenerReporteServiciosSolicitados(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            String tipoServicio) {

        try {
            List<Object[]> resultados = reportesRepository.obtenerReporteServiciosSolicitados(
                    fechaInicio, fechaFin, tipoServicio);

            if (resultados == null || resultados.isEmpty()) {
                return new ArrayList<>();
            }

            return resultados.stream().map(row -> {
                ReporteServiciosSolicitadosDTO dto = new ReporteServiciosSolicitadosDTO();
                dto.setIdServicio(row[0] != null ? ((Number) row[0]).intValue() : null);
                dto.setNombreServicio(row[1] != null ? (String) row[1] : "");
                dto.setDescripcion(row[2] != null ? (String) row[2] : "");
                dto.setTipoServicio(row[3] != null ? (String) row[3] : "");
                dto.setMolecula(row[4] != null ? (String) row[4] : "N/A");
                dto.setVecesSolicitado(row[5] != null ? ((Number) row[5]).longValue() : 0L);
                dto.setCantidadTotal(row[6] != null ? ((Number) row[6]).longValue() : 0L);
                dto.setCitasCompletadas(row[7] != null ? ((Number) row[7]).longValue() : 0L);
                dto.setCitasPendientes(row[8] != null ? ((Number) row[8]).longValue() : 0L);
                dto.setPorcentajeCompletado(row[9] != null ? ((Number) row[9]).doubleValue() : 0.0);
                return dto;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo reporte de servicios: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para convertir a LocalDateTime
    private LocalDateTime convertToLocalDateTime(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toLocalDateTime();
        }
        if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        }
        return null;
    }
}
