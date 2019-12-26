package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.CoordenadaBancaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CoordenadaBancaria entity.
 */
@Repository
public interface CoordenadaBancariaRepository extends JpaRepository<CoordenadaBancaria, Long> {

    @Query(value = "select distinct coordenadaBancaria from CoordenadaBancaria coordenadaBancaria left join fetch coordenadaBancaria.instituicaoEnsinos",
        countQuery = "select count(distinct coordenadaBancaria) from CoordenadaBancaria coordenadaBancaria")
    Page<CoordenadaBancaria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct coordenadaBancaria from CoordenadaBancaria coordenadaBancaria left join fetch coordenadaBancaria.instituicaoEnsinos")
    List<CoordenadaBancaria> findAllWithEagerRelationships();

    @Query("select coordenadaBancaria from CoordenadaBancaria coordenadaBancaria left join fetch coordenadaBancaria.instituicaoEnsinos where coordenadaBancaria.id =:id")
    Optional<CoordenadaBancaria> findOneWithEagerRelationships(@Param("id") Long id);

}
