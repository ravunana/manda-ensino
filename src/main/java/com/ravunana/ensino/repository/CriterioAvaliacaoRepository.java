package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.CriterioAvaliacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CriterioAvaliacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long> {

}
