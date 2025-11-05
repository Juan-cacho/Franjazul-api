package com.franjazul.api.repository;

import com.franjazul.api.model.Servicios;
import com.franjazul.api.model.TipoServicio;
import com.franjazul.api.model.Moleculas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, Integer> {

    // Buscar servicio por nombre
    Optional<Servicios> findByNombreSer(String nombreSer);

    // Buscar servicios por tipo de servicio
    List<Servicios> findByTipoServicio(TipoServicio tipoServicio);

    // Buscar servicios por molécula
    List<Servicios> findByMolecula(Moleculas molecula);
}
