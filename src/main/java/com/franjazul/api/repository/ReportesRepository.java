package com.franjazul.api.repository;

import com.franjazul.api.model.Citas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportesRepository extends JpaRepository<Citas, Integer> {

    // Reporte de citas por período
    @Query(value = "SELECT * FROM fn_reporte_citas_por_periodo(:fechaInicio, :fechaFin, :estado, :idTecnico)",
            nativeQuery = true)
    List<Object[]> obtenerReporteCitasPorPeriodo(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("estado") String estado,
            @Param("idTecnico") String idTecnico
    );

    // Reporte de productividad de técnicos
    @Query(value = "SELECT * FROM fn_reporte_productividad_tecnicos(:fechaInicio, :fechaFin, :idTecnico)",
            nativeQuery = true)
    List<Object[]> obtenerReporteProductividadTecnicos(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("idTecnico") String idTecnico
    );

    // Reporte de servicios más solicitados
    @Query(value = "SELECT * FROM fn_reporte_servicios_solicitados(:fechaInicio, :fechaFin, :tipoServicio)",
            nativeQuery = true)
    List<Object[]> obtenerReporteServiciosSolicitados(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("tipoServicio") String tipoServicio
    );
}
