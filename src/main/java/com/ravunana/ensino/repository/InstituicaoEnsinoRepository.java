package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.InstituicaoEnsino;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the InstituicaoEnsino entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstituicaoEnsinoRepository extends JpaRepository<InstituicaoEnsino, Long> {

    @Query("select instituicaoEnsino from InstituicaoEnsino instituicaoEnsino where instituicaoEnsino.utilizador.login = ?#{principal.username}")
    List<InstituicaoEnsino> findByUtilizadorIsCurrentUser();

}
