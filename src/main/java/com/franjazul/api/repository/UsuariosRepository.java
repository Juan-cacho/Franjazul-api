package com.franjazul.api.repository;

import com.franjazul.api.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, String> {


    // Buscar por email
    Optional<Usuarios> findByEmailUs(String emailUs);

    // Buscar por email y password para login
    @Query("SELECT u FROM Usuarios u WHERE u.emailUs = :email AND u.passwordUs = :password")
    Optional<Usuarios> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    // Verificar si existe un email
    boolean existsByEmailUs(String emailUs);

    // Buscar usuarios por nombre de cargo
    List<Usuarios> findByCargoDeUsuario_NombreCargo(String nombreCargo);


}