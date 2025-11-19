package com.franjazul.api.services;

import com.franjazul.api.dto.*;
import com.franjazul.api.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    // Obtener estadísticas del dashboard
    public DashboardStatsDTO obtenerEstadisticasDashboard() {
        List<Object[]> resultados = dashboardRepository.obtenerEstadisticasDashboard();

        if (resultados.isEmpty()) {
            return new DashboardStatsDTO();
        }

        Object[] row = resultados.get(0);
        DashboardStatsDTO dto = new DashboardStatsDTO();

        dto.setCitasCompletadas(((Number) row[0]).longValue());
        dto.setTotalCitas(((Number) row[1]).longValue());
        dto.setPorcentajeCompletadas(row[2] != null ? ((Number) row[2]).doubleValue() : 0.0);
        dto.setCitasPendientes(((Number) row[3]).longValue());
        dto.setServiciosActivos(((Number) row[4]).longValue());
        dto.setTotalTecnicos(((Number) row[5]).longValue());
        dto.setTotalCitas(((Number) row[6]).longValue());
        dto.setCertificadosVigentes(((Number) row[7]).longValue());
        dto.setCertificadosVencidos(((Number) row[8]).longValue());
        dto.setVariacionMesAnterior(row[9] != null ? ((Number) row[9]).doubleValue() : 0.0);

        return dto;
    }

    // Obtener citas agrupadas por estado
    public List<CitasPorEstadoDTO> obtenerCitasPorEstado() {
        List<Object[]> resultados = dashboardRepository.obtenerCitasPorEstado();

        return resultados.stream().map(row -> {
            CitasPorEstadoDTO dto = new CitasPorEstadoDTO();
            dto.setEstado((String) row[0]);
            dto.setDescripcion((String) row[1]);
            dto.setCantidad(((Number) row[2]).longValue());
            return dto;
        }).collect(Collectors.toList());
    }

    // Obtener rendimiento de técnicos
    public List<CitasPorTecnicoDTO> obtenerRendimientoTecnicos() {
        List<Object[]> resultados = dashboardRepository.obtenerRendimientoTecnicos();

        return resultados.stream().map(row -> {
            CitasPorTecnicoDTO dto = new CitasPorTecnicoDTO();
            dto.setIdTecnico((String) row[0]);
            dto.setNombreCompleto((String) row[1]);
            dto.setTotalCitasAsignadas(((Number) row[2]).longValue());
            dto.setCitasCompletadas(((Number) row[3]).longValue());
            dto.setCitasPendientes(((Number) row[4]).longValue());
            dto.setCitasCanceladas(((Number) row[5]).longValue());
            dto.setPorcentajeEfectividad(row[6] != null ? ((Number) row[6]).doubleValue() : 0.0);
            return dto;
        }).collect(Collectors.toList());
    }

    // Obtener citas de un técnico específico
    public List<CitaDetalleDTO> obtenerCitasPorTecnico(String idTecnico) {
        if (idTecnico == null || idTecnico.trim().isEmpty()) {
            throw new RuntimeException("El ID del técnico es obligatorio");
        }

        List<Object[]> resultados = dashboardRepository.obtenerCitasPorTecnico(idTecnico);

        return resultados.stream().map(row -> {
            CitaDetalleDTO dto = new CitaDetalleDTO();
            dto.setIdCita(((Number) row[0]).intValue());
            dto.setObservaciones((String) row[1]);
            dto.setEstado((String) row[2]);
            dto.setDescripcionEstado((String) row[3]);
            dto.setLugar((String) row[4]);
            dto.setFechaInicio(convertToLocalDateTime(row[5]));
            dto.setFechaFin(convertToLocalDateTime(row[6]));
            dto.setUsuarioCreo((String) row[7]);
            return dto;
        }).collect(Collectors.toList());
    }

    // Obtener certificados próximos a vencer
    public List<CertificadoPorVencerDTO> obtenerCertificadosPorVencer(Integer diasAdelante) {
        if (diasAdelante == null) {
            diasAdelante = 7; // Valor por defecto
        }
        if (diasAdelante < 1) {
            throw new RuntimeException("Los días adelante deben ser mayor a 0");
        }

        List<Object[]> resultados = dashboardRepository.obtenerCertificadosPorVencer(diasAdelante);

        return resultados.stream().map(row -> {
            CertificadoPorVencerDTO dto = new CertificadoPorVencerDTO();
            dto.setCodigoCertificado((String) row[0]);
            dto.setFechaEmision(convertToLocalDateTime(row[1]));
            dto.setFechaVencimiento(convertToLocalDateTime(row[2]));
            dto.setDiasParaVencer(((Number) row[3]).intValue());
            dto.setIdCita(((Number) row[4]).intValue());
            dto.setLugar((String) row[5]);
            dto.setTecnico((String) row[6]);
            return dto;
        }).collect(Collectors.toList());
    }

    // Obtener indicadores de cumplimiento
    public List<IndicadorCumplimientoDTO> obtenerIndicadoresCumplimiento() {
        List<Object[]> resultados = dashboardRepository.obtenerIndicadoresCumplimiento();

        return resultados.stream().map(row -> {
            IndicadorCumplimientoDTO dto = new IndicadorCumplimientoDTO();
            dto.setNombreServicio((String) row[0]);
            dto.setTipoServicio((String) row[1]);
            dto.setTotalVecesSolicitado(((Number) row[2]).longValue());
            dto.setCompletadas(((Number) row[3]).longValue());
            dto.setPorcentajeCumplimiento(row[4] != null ? ((Number) row[4]).doubleValue() : 0.0);
            return dto;
        }).collect(Collectors.toList());
    }

    // Obtener próximas citas
    public List<ProximaCitaDTO> obtenerProximasCitas(Integer cantidad) {
        if (cantidad == null) {
            cantidad = 5;
        }
        if (cantidad < 1) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        List<Object[]> resultados = dashboardRepository.obtenerProximasCitas(cantidad);

        return resultados.stream().map(row -> {
            ProximaCitaDTO dto = new ProximaCitaDTO();
            dto.setIdCita(((Number) row[0]).intValue());
            dto.setLugar((String) row[1]);
            dto.setDireccion((String) row[2]);
            dto.setFechaInicio(convertToLocalDateTime(row[3]));
            dto.setFechaFin(convertToLocalDateTime(row[4]));
            dto.setFechaFormato((String) row[5]);
            dto.setHoraInicio((String) row[6]);
            dto.setHoraFin((String) row[7]);
            dto.setTecnicoResponsable((String) row[8]);
            dto.setUsuarioCreo((String) row[9]);
            dto.setEstado((String) row[10]);
            dto.setDescripcionEstado((String) row[11]);
            dto.setObservaciones((String) row[12]);
            dto.setServicios((String) row[13]);
            return dto;
        }).collect(Collectors.toList());
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
