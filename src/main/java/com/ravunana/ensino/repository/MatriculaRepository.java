package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Matricula;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Matricula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    @Query("select matricula from Matricula matricula where matricula.utilizador.login = ?#{principal.username}")
    List<Matricula> findByUtilizadorIsCurrentUser();

}
