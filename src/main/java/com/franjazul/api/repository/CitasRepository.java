package com.franjazul.api.repository;

import com.franjazul.api.model.Citas;
import com.franjazul.api.model.Usuarios;
import com.franjazul.api.model.EstadoCita;
import com.franjazul.api.model.FranjasHorarias;
import com.franjazul.api.model.Lugares;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer> {

    // Buscar citas por usuario técnico
    List<Citas> findByUsuarioTecnico(Usuarios usuarioTecnico);

    // Buscar citas creadas por un usuario
    List<Citas> findByUsuarioCreo(Usuarios usuarioCreo);

    // Buscar citas por estado
    List<Citas> findByEstadoCita(EstadoCita estadoCita);

    // Buscar citas por franja horaria
    List<Citas> findByFranjaHoraria(FranjasHorarias franjaHoraria);

    // Buscar citas por lugar
    List<Citas> findByLugar(Lugares lugar);

    //Buscar la ultima cita para poderla asignar al ultimo tecnico
    Optional<Citas> findFirstByOrderByIdCitaDesc();

    @Query("SELECT c FROM Citas c WHERE c.usuarioTecnico.idUsuario = :idTecnico ORDER BY c.franjaHoraria.fechaInicio ASC")
    Page<Citas> findByUsuarioTecnicoOrderByFecha(@Param("idTecnico") String idTecnico, Pageable pageable);

    @Query("SELECT c FROM Citas c WHERE c.usuarioTecnico.idUsuario = :idTecnico " +
            "AND (LOWER(c.usuarioCreo.nombreUs) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(c.usuarioCreo.apellidoUs) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(c.usuarioCreo.idUsuario) LIKE LOWER(CONCAT('%', :busqueda, '%'))) " +
            "ORDER BY c.franjaHoraria.fechaInicio ASC")
    Page<Citas> findByUsuarioTecnicoAndClienteContaining(
            @Param("idTecnico") String idTecnico,
            @Param("busqueda") String busqueda,
            Pageable pageable
    );

    @Query("SELECT COUNT(c) FROM Citas c WHERE c.usuarioTecnico.idUsuario = :idTecnico " +
            "AND c.franjaHoraria.fechaInicio = :fechaInicio " +
            "AND c.franjaHoraria.fechaFin = :fechaFin " +
            "AND c.idCita <> :idCitaActual")
    long countByTecnicoAndFranjaExcluyendoCita(
            @Param("idTecnico") String idTecnico,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("idCitaActual") Integer idCitaActual
    );
}
