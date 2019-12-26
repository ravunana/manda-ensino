package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.DocumentacaoPessoa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentacaoPessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentacaoPessoaRepository extends JpaRepository<DocumentacaoPessoa, Long> {

}
