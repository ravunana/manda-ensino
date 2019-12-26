package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.FormaLiquidacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormaLiquidacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaLiquidacaoRepository extends JpaRepository<FormaLiquidacao, Long> {

}
