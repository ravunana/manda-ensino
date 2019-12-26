package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Aula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Aula entity.
 */
@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    @Query("select aula from Aula aula where aula.utilizador.login = ?#{principal.username}")
    List<Aula> findByUtilizadorIsCurrentUser();

    @Query(value = "select distinct aula from Aula aula left join fetch aula.planoAulas",
        countQuery = "select count(distinct aula) from Aula aula")
    Page<Aula> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct aula from Aula aula left join fetch aula.planoAulas")
    List<Aula> findAllWithEagerRelationships();

    @Query("select aula from Aula aula left join fetch aula.planoAulas where aula.id =:id")
    Optional<Aula> findOneWithEagerRelationships(@Param("id") Long id);

}
