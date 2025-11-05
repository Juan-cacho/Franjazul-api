package com.franjazul.api.repository;

import com.franjazul.api.model.Permisos;
import com.franjazul.api.model.PermisoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisosRepository extends JpaRepository<Permisos, PermisoId> {


    // Buscar permisos por perfil
    @Query("SELECT p FROM Permisos p WHERE p.idPerEnPerm = :idPerfil")
    List<Permisos> findByIdPerEnPerm(@Param("idPerfil") Integer idPerfil);

    // Buscar permisos por formulario
    @Query("SELECT p FROM Permisos p WHERE p.idFormEnPerm = :idFormulario")
    List<Permisos> findByIdFormEnPerm(@Param("idFormulario") Integer idFormulario);

    // Buscar un permiso específico
    @Query("SELECT p FROM Permisos p WHERE p.idPerEnPerm = :idPerfil AND p.idFormEnPerm = :idFormulario")
    Optional<Permisos> findByPerfilAndFormulario(@Param("idPerfil") Integer idPerfil,
                                                @Param("idFormulario") Integer idFormulario);
}