package com.franjazul.api.repository;

import com.franjazul.api.model.TipoLugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoLugarRepository extends JpaRepository<TipoLugar, Integer> {

    // Buscar tipo de lugar por nombre
    Optional<TipoLugar> findByNombreTl(String nombreTl);
}
