package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.EncarregadoEducacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EncarregadoEducacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncarregadoEducacaoRepository extends JpaRepository<EncarregadoEducacao, Long> {

}
