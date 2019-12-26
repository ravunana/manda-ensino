package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.DetalhePagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DetalhePagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalhePagamentoRepository extends JpaRepository<DetalhePagamento, Long> {

    @Query("select detalhePagamento from DetalhePagamento detalhePagamento where detalhePagamento.utilizador.login = ?#{principal.username}")
    List<DetalhePagamento> findByUtilizadorIsCurrentUser();

}
