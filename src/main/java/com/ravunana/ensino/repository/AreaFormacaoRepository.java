package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.AreaFormacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AreaFormacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaFormacaoRepository extends JpaRepository<AreaFormacao, Long> {

}
