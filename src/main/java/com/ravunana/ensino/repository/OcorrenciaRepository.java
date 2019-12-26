package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Ocorrencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Ocorrencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    @Query("select ocorrencia from Ocorrencia ocorrencia where ocorrencia.utilizador.login = ?#{principal.username}")
    List<Ocorrencia> findByUtilizadorIsCurrentUser();

}
