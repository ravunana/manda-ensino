package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Nota;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Nota entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

    @Query("select nota from Nota nota where nota.utilizador.login = ?#{principal.username}")
    List<Nota> findByUtilizadorIsCurrentUser();

}
