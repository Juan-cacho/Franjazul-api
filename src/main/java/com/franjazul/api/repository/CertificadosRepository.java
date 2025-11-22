package com.franjazul.api.repository;

import com.franjazul.api.model.Certificados;
import com.franjazul.api.model.Citas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificadosRepository extends JpaRepository<Certificados, String> {

    // Buscar certificados por cita
    List<Certificados> findByCita(Citas cita);

    // Buscar certificados vencidos
    List<Certificados> findByFechaVenceBefore(LocalDateTime fecha);

    // Buscar certificados vigentes
    List<Certificados> findByFechaVenceAfter(LocalDateTime fecha);

    // Buscar certificados por rango de fechas de emisión
    List<Certificados> findByFechaEmisionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);


    //Query para solicitar certificado

    @Query("SELECT c FROM Certificados c WHERE c.cita.usuarioCreo.idUsuario = :userId ORDER BY c.fechaEmision DESC")
    Optional<Certificados> findUltimoCertificadoByUsuario(@Param("userId") String userId);

}
