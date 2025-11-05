package com.franjazul.api.repository;

import com.franjazul.api.model.CitaServicio;
import com.franjazul.api.model.CitaServicioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaServicioRepository extends JpaRepository<CitaServicio, CitaServicioId> {

    // Buscar todos los servicios de una cita
    List<CitaServicio> findByCitaEnIntermedio(Integer citaId);

    // Buscar todas las citas que tienen un servicio
    List<CitaServicio> findByServicioEnIntermedio(Integer servicioId);
}
