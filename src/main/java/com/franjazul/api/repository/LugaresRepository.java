package com.franjazul.api.repository;

import com.franjazul.api.model.Lugares;
import com.franjazul.api.model.TipoLugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LugaresRepository extends JpaRepository<Lugares, Integer> {

    // Buscar lugares por nombre
    Optional<Lugares> findByNombreLugar(String nombreLugar);

    // Buscar lugares por tipo de lugar
    List<Lugares> findByTipoLugar(TipoLugar tipoLugar);

    // Buscar lugares dentro de otro lugar
    List<Lugares> findByLugarPadre(Lugares lugarPadre);
}
