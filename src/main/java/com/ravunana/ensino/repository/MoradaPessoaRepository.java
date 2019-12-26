package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.MoradaPessoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MoradaPessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoradaPessoaRepository extends JpaRepository<MoradaPessoa, Long> {

}
