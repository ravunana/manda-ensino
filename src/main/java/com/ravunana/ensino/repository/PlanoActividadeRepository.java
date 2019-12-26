package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.PlanoActividade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PlanoActividade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoActividadeRepository extends JpaRepository<PlanoActividade, Long> {

    @Query("select planoActividade from PlanoActividade planoActividade where planoActividade.utilizador.login = ?#{principal.username}")
    List<PlanoActividade> findByUtilizadorIsCurrentUser();

}
