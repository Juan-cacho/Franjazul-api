package com.franjazul.api.repository;

import com.franjazul.api.model.Formularios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormulariosRepository extends JpaRepository<Formularios, Integer> {

    // Buscar por título
    Optional<Formularios> findByTituloForm(String tituloForm);
}