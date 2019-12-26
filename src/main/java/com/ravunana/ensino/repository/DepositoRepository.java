package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Deposito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Deposito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {

    @Query("select deposito from Deposito deposito where deposito.utilizador.login = ?#{principal.username}")
    List<Deposito> findByUtilizadorIsCurrentUser();

}
