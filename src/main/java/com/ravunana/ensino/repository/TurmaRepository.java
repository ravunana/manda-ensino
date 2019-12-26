package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Turma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Turma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("select turma from Turma turma where turma.utilizador.login = ?#{principal.username}")
    List<Turma> findByUtilizadorIsCurrentUser();

}
