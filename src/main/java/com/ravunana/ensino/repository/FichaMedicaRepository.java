package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.FichaMedica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FichaMedica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichaMedicaRepository extends JpaRepository<FichaMedica, Long> {

    @Query("select fichaMedica from FichaMedica fichaMedica where fichaMedica.utilizador.login = ?#{principal.username}")
    List<FichaMedica> findByUtilizadorIsCurrentUser();

}
