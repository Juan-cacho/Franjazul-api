package com.franjazul.api.repository;
import com.franjazul.api.model.Perfiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilesRepository extends JpaRepository<Perfiles, Integer>{

    // Buscar perfil por nombre
    Optional<Perfiles> findByNombrePer(String nombrePer);

    @Query("SELECT p FROM Perfiles p WHERE p.rol.idRol = :idRol")
    List<Perfiles> findByRolId(@Param("idRol") Integer idRol);

}
