package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Professor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Professor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("select professor from Professor professor where professor.utilizador.login = ?#{principal.username}")
    List<Professor> findByUtilizadorIsCurrentUser();

}
