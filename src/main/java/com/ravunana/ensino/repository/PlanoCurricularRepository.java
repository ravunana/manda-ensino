package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.PlanoCurricular;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanoCurricular entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoCurricularRepository extends JpaRepository<PlanoCurricular, Long> {

}
