package com.franjazul.api.repository;

import com.franjazul.api.model.FranjasHorarias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FranjasHorariasRepository extends JpaRepository<FranjasHorarias, Integer> {

    // Buscar franjas horarias que contengan una fecha específica
    List<FranjasHorarias> findByFechaInicioBefore(LocalDateTime fecha);

    // Buscar franjas horarias por rango de fechas
    List<FranjasHorarias> findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("SELECT f FROM FranjasHorarias f WHERE f.fechaInicio = :fechaInicio AND f.fechaFin = :fechaFin")
    Optional<FranjasHorarias> findByFechaInicioAndFechaFin(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}
