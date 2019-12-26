package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Requerimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Requerimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequerimentoRepository extends JpaRepository<Requerimento, Long> {

    @Query("select requerimento from Requerimento requerimento where requerimento.utilizador.login = ?#{principal.username}")
    List<Requerimento> findByUtilizadorIsCurrentUser();

}
