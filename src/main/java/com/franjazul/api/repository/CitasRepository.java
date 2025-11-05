package com.franjazul.api.repository;

import com.franjazul.api.model.Citas;
import com.franjazul.api.model.Usuarios;
import com.franjazul.api.model.EstadoCita;
import com.franjazul.api.model.FranjasHorarias;
import com.franjazul.api.model.Lugares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer> {

    // Buscar citas por usuario técnico
    List<Citas> findByUsuarioTecnico(Usuarios usuarioTecnico);

    // Buscar citas creadas por un usuario
    List<Citas> findByUsuarioCreo(Usuarios usuarioCreo);

    // Buscar citas por estado
    List<Citas> findByEstadoCita(EstadoCita estadoCita);

    // Buscar citas por franja horaria
    List<Citas> findByFranjaHoraria(FranjasHorarias franjaHoraria);

    // Buscar citas por lugar
    List<Citas> findByLugar(Lugares lugar);
}
