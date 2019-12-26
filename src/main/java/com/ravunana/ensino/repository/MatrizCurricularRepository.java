package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.MatrizCurricular;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MatrizCurricular entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatrizCurricularRepository extends JpaRepository<MatrizCurricular, Long> {

}
