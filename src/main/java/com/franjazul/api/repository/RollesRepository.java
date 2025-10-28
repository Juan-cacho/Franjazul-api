package com.franjazul.api.repository;

import com.franjazul.api.model.Rolles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RollesRepository extends JpaRepository<Rolles, Integer> {

    // Buscar rol por nombre
    Optional<Rolles> findByNombreRol(String nombreRol);


}