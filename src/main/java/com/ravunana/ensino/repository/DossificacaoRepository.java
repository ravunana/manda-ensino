package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Dossificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Dossificacao entity.
 */
@Repository
public interface DossificacaoRepository extends JpaRepository<Dossificacao, Long> {

    @Query(value = "select distinct dossificacao from Dossificacao dossificacao left join fetch dossificacao.cursos left join fetch dossificacao.classes",
        countQuery = "select count(distinct dossificacao) from Dossificacao dossificacao")
    Page<Dossificacao> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dossificacao from Dossificacao dossificacao left join fetch dossificacao.cursos left join fetch dossificacao.classes")
    List<Dossificacao> findAllWithEagerRelationships();

    @Query("select dossificacao from Dossificacao dossificacao left join fetch dossificacao.cursos left join fetch dossificacao.classes where dossificacao.id =:id")
    Optional<Dossificacao> findOneWithEagerRelationships(@Param("id") Long id);

}
