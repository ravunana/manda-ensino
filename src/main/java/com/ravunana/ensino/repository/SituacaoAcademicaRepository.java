package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.SituacaoAcademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SituacaoAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituacaoAcademicaRepository extends JpaRepository<SituacaoAcademica, Long> {

}
