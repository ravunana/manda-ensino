package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Pessoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Pessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("select pessoa from Pessoa pessoa where pessoa.utilizador.login = ?#{principal.username}")
    List<Pessoa> findByUtilizadorIsCurrentUser();

}
