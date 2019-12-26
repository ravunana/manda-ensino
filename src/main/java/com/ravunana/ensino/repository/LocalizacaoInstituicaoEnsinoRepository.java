package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.LocalizacaoInstituicaoEnsino;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocalizacaoInstituicaoEnsino entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalizacaoInstituicaoEnsinoRepository extends JpaRepository<LocalizacaoInstituicaoEnsino, Long> {

}
