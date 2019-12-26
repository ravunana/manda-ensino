package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.CategoriaAluno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaAluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaAlunoRepository extends JpaRepository<CategoriaAluno, Long> {

}
