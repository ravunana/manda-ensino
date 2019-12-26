package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.CategoriaValiacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaValiacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaValiacaoRepository extends JpaRepository<CategoriaValiacao, Long> {

}
