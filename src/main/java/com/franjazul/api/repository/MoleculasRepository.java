package com.franjazul.api.repository;

import com.franjazul.api.model.Moleculas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoleculasRepository extends JpaRepository<Moleculas, String> {
}
