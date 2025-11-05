package com.franjazul.api.repository;

import com.franjazul.api.model.FranjasHorarias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FranjasHorariasRepository extends JpaRepository<FranjasHorarias, Integer> {

    // Buscar franjas horarias que contengan una fecha específica
    List<FranjasHorarias> findByFechaInicioBefore(LocalDateTime fecha);

    // Buscar franjas horarias por rango de fechas
    List<FranjasHorarias> findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
