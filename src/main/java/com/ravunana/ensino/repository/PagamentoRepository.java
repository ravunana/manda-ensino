package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Pagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Pagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("select pagamento from Pagamento pagamento where pagamento.utilizador.login = ?#{principal.username}")
    List<Pagamento> findByUtilizadorIsCurrentUser();

}
