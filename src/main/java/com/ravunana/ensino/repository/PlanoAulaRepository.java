package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.PlanoAula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PlanoAula entity.
 */
@Repository
public interface PlanoAulaRepository extends JpaRepository<PlanoAula, Long> {

    @Query("select planoAula from PlanoAula planoAula where planoAula.utilizador.login = ?#{principal.username}")
    List<PlanoAula> findByUtilizadorIsCurrentUser();

    @Query(value = "select distinct planoAula from PlanoAula planoAula left join fetch planoAula.turmas",
        countQuery = "select count(distinct planoAula) from PlanoAula planoAula")
    Page<PlanoAula> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct planoAula from PlanoAula planoAula left join fetch planoAula.turmas")
    List<PlanoAula> findAllWithEagerRelationships();

    @Query("select planoAula from PlanoAula planoAula left join fetch planoAula.turmas where planoAula.id =:id")
    Optional<PlanoAula> findOneWithEagerRelationships(@Param("id") Long id);

}
