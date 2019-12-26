package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Aluno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Aluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("select aluno from Aluno aluno where aluno.utilizador.login = ?#{principal.username}")
    List<Aluno> findByUtilizadorIsCurrentUser();

}
