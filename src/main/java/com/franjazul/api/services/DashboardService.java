package com.franjazul.api.services;

import com.franjazul.api.dto.*;
import com.franjazul.api.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    // Obtener estadísticas del dashboard
    public DashboardStatsDTO obtenerEstadisticasDashboard() {
        return dashboardRepository.obtenerEstadisticasDashboard();
    }

    // Obtener citas agrupadas por estado
    public List<CitasPorEstadoDTO> obtenerCitasPorEstado() {
        return dashboardRepository.obtenerCitasPorEstado();
    }

    // Obtener rendimiento de técnicos
    public List<CitasPorTecnicoDTO> obtenerRendimientoTecnicos() {
        return dashboardRepository.obtenerRendimientoTecnicos();
    }

    // Obtener citas de un técnico específico
    public List<CitaDetalleDTO> obtenerCitasPorTecnico(String idTecnico) {
        if (idTecnico == null || idTecnico.trim().isEmpty()) {
            throw new RuntimeException("El ID del técnico es obligatorio");
        }
        return dashboardRepository.obtenerCitasPorTecnico(idTecnico);
    }

    // Obtener certificados próximos a vencer
    public List<CertificadoPorVencerDTO> obtenerCertificadosPorVencer(Integer diasAdelante) {
        if (diasAdelante != null && diasAdelante < 1) {
            throw new RuntimeException("Los días adelante deben ser mayor a 0");
        }
        return dashboardRepository.obtenerCertificadosPorVencer(diasAdelante);
    }


    public List<IndicadorCumplimientoDTO> obtenerIndicadoresCumplimiento() {
        return dashboardRepository.obtenerIndicadoresCumplimiento();
    }


    public List<ProximaCitaDTO> obtenerProximasCitas(Integer cantidad) {
        if (cantidad != null && cantidad < 1) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        return dashboardRepository.obtenerProximasCitas(cantidad);
    }
}
