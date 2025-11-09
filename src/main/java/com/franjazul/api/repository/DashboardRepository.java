package com.franjazul.api.repository;

import com.franjazul.api.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener estadísticas del dashboard usando el paquete
    public DashboardStatsDTO obtenerEstadisticasDashboard() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            // Llamar a la función dentro del paquete
            callableStatement = connection.prepareCall("{ ? = call PKG_ESTADISTICAS.FN_ESTADISTICAS_DASHBOARD }");
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.execute();

            resultSet = (ResultSet) callableStatement.getObject(1);

            if (resultSet.next()) {
                stats.setTotalCitas(resultSet.getLong("TOTAL_CITAS"));
                stats.setCitasPendientes(resultSet.getLong("CITAS_PENDIENTES"));
                stats.setCitasCompletadas(resultSet.getLong("CITAS_COMPLETADAS"));
                stats.setCitasCanceladas(resultSet.getLong("CITAS_CANCELADAS"));
                stats.setTotalTecnicos(resultSet.getLong("TOTAL_TECNICOS"));
                stats.setTotalCertificados(resultSet.getLong("TOTAL_CERTIFICADOS"));
                stats.setCertificadosVencidos(resultSet.getLong("CERTIFICADOS_VENCIDOS"));
                stats.setCertificadosVigentes(resultSet.getLong("CERTIFICADOS_VIGENTES"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener estadísticas del dashboard: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(resultSet, callableStatement, connection);
        }

        return stats;
    }

    // Obtener citas por estado usando el paquete
    public List<CitasPorEstadoDTO> obtenerCitasPorEstado() {
        List<CitasPorEstadoDTO> lista = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            // Llamar a la función dentro del paquete
            callableStatement = connection.prepareCall("{ ? = call PKG_ESTADISTICAS.FN_CITAS_POR_ESTADO }");
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.execute();

            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                CitasPorEstadoDTO dto = new CitasPorEstadoDTO();
                dto.setEstado(resultSet.getString("ESTADO"));
                dto.setDescripcion(resultSet.getString("DESCRIPCION"));
                dto.setCantidad(resultSet.getLong("CANTIDAD"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener citas por estado: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(resultSet, callableStatement, connection);
        }

        return lista;
    }

    // Obtener rendimiento de técnicos usando el paquete
    public List<CitasPorTecnicoDTO> obtenerRendimientoTecnicos() {
        List<CitasPorTecnicoDTO> lista = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            // Llamar a la función dentro del paquete
            callableStatement = connection.prepareCall("{ ? = call PKG_ESTADISTICAS.FN_RENDIMIENTO_TECNICOS }");
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.execute();

            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                CitasPorTecnicoDTO dto = new CitasPorTecnicoDTO();
                dto.setIdTecnico(resultSet.getString("ID_TECNICO"));
                dto.setNombreCompleto(resultSet.getString("NOMBRE_COMPLETO"));
                dto.setTotalCitasAsignadas(resultSet.getLong("TOTAL_CITAS_ASIGNADAS"));
                dto.setCitasCompletadas(resultSet.getLong("CITAS_COMPLETADAS"));
                dto.setCitasPendientes(resultSet.getLong("CITAS_PENDIENTES"));
                dto.setCitasCanceladas(resultSet.getLong("CITAS_CANCELADAS"));
                dto.setPorcentajeEfectividad(resultSet.getDouble("PORCENTAJE_EFECTIVIDAD"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener rendimiento de técnicos: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(resultSet, callableStatement, connection);
        }

        return lista;
    }

    // Obtener citas de un técnico específico usando el paquete
    public List<CitaDetalleDTO> obtenerCitasPorTecnico(String idTecnico) {
        List<CitaDetalleDTO> lista = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            // Llamar a la función dentro del paquete con parámetro
            callableStatement = connection.prepareCall("{ ? = call PKG_ESTADISTICAS.FN_CITAS_POR_TECNICO(?) }");
            callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            callableStatement.setString(2, idTecnico);
            callableStatement.execute();

            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                CitaDetalleDTO dto = new CitaDetalleDTO();
                dto.setIdCita(resultSet.getInt("ID_CITA"));
                dto.setObservaciones(resultSet.getString("OBSERVACIONES"));
                dto.setEstado(resultSet.getString("ESTADO"));
                dto.setDescripcionEstado(resultSet.getString("DESCRIPCION_ESTADO"));
                dto.setLugar(resultSet.getString("LUGAR"));

                Timestamp fechaInicio = resultSet.getTimestamp("FECHA_INICIO");
                if (fechaInicio != null) {
                    dto.setFechaInicio(fechaInicio.toLocalDateTime());
                }

                Timestamp fechaFin = resultSet.getTimestamp("FECHA_FIN");
                if (fechaFin != null) {
                    dto.setFechaFin(fechaFin.toLocalDateTime());
                }

                dto.setUsuarioCreo(resultSet.getString("USUARIO_CREO"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener citas por técnico: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(resultSet, callableStatement, connection);
        }

        return lista;
    }

    // NUEVA FUNCIÓN: Obtener certificados próximos a vencer
    public List<CertificadoPorVencerDTO> obtenerCertificadosPorVencer(Integer diasAdelante) {
        List<CertificadoPorVencerDTO> lista = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            // Llamar a la función dentro del paquete con parámetro opcional
            if (diasAdelante != null) {
                callableStatement = connection.prepareCall("{ ? = call PKG_ESTADISTICAS.FN_CERTIFICADOS_POR_VENCER(?) }");
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.setInt(2, diasAdelante);
            } else {
                // Usar el valor por defecto (30 días)
                callableStatement = connection.prepareCall("{ ? = call PKG_ESTADISTICAS.FN_CERTIFICADOS_POR_VENCER }");
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
            }

            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                CertificadoPorVencerDTO dto = new CertificadoPorVencerDTO();
                dto.setCodigoCertificado(resultSet.getString("CODIGO_CERTIFICADO"));

                Timestamp fechaEmision = resultSet.getTimestamp("FECHA_EMISION");
                if (fechaEmision != null) {
                    dto.setFechaEmision(fechaEmision.toLocalDateTime());
                }

                Timestamp fechaVencimiento = resultSet.getTimestamp("FECHA_VENCIMIENTO");
                if (fechaVencimiento != null) {
                    dto.setFechaVencimiento(fechaVencimiento.toLocalDateTime());
                }

                dto.setDiasParaVencer(resultSet.getInt("DIAS_PARA_VENCER"));
                dto.setIdCita(resultSet.getInt("ID_CITA"));
                dto.setLugar(resultSet.getString("LUGAR"));
                dto.setTecnico(resultSet.getString("TECNICO"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener certificados por vencer: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(resultSet, callableStatement, connection);
        }

        return lista;
    }

    // Método auxiliar para cerrar recursos
    private void cerrarRecursos(ResultSet rs, CallableStatement cs, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
