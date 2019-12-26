package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Horario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Horario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    @Query("select horario from Horario horario where horario.utilizador.login = ?#{principal.username}")
    List<Horario> findByUtilizadorIsCurrentUser();

}
