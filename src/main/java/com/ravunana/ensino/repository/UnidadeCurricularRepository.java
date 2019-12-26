package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.UnidadeCurricular;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadeCurricular entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeCurricularRepository extends JpaRepository<UnidadeCurricular, Long> {

}
