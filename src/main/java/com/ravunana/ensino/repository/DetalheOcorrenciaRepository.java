package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.DetalheOcorrencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetalheOcorrencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalheOcorrenciaRepository extends JpaRepository<DetalheOcorrencia, Long> {

}
