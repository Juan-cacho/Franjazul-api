package com.franjazul.api.repository;

import com.franjazul.api.dto.*;
import com.franjazul.api.model.Citas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Citas, Integer> {

    // Cambiar de Oracle procedure call a PostgreSQL function call
    @Query(value = "SELECT * FROM fn_estadisticas_dashboard()", nativeQuery = true)
    List<Object[]> obtenerEstadisticasDashboard();

    @Query(value = "SELECT * FROM fn_citas_por_estado()", nativeQuery = true)
    List<Object[]> obtenerCitasPorEstado();

    @Query(value = "SELECT * FROM fn_rendimiento_tecnicos()", nativeQuery = true)
    List<Object[]> obtenerRendimientoTecnicos();

    @Query(value = "SELECT * FROM fn_citas_por_tecnico(:idTecnico)", nativeQuery = true)
    List<Object[]> obtenerCitasPorTecnico(@Param("idTecnico") String idTecnico);

    @Query(value = "SELECT * FROM fn_certificados_por_vencer(:dias)", nativeQuery = true)
    List<Object[]> obtenerCertificadosPorVencer(@Param("dias") Integer dias);

    @Query(value = "SELECT * FROM fn_indicadores_cumplimiento()", nativeQuery = true)
    List<Object[]> obtenerIndicadoresCumplimiento();

    @Query(value = "SELECT * FROM fn_proximas_citas(:cantidad)", nativeQuery = true)
    List<Object[]> obtenerProximasCitas(@Param("cantidad") Integer cantidad);

    @Query(value = "SELECT * FROM fn_estadisticas_comparativas()", nativeQuery = true)
    List<Object[]> obtenerEstadisticasComparativas();

    @Query(value = "SELECT * FROM fn_servicios_mas_solicitados(:limite)", nativeQuery = true)
    List<Object[]> obtenerServiciosMasSolicitados(@Param("limite") Integer limite);

    @Query(value = "SELECT * FROM fn_lugares_mas_servicios(:limite)", nativeQuery = true)
    List<Object[]> obtenerLugaresMasServicios(@Param("limite") Integer limite);
}
