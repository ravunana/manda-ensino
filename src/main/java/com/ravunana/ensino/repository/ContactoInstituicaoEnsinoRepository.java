package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.ContactoInstituicaoEnsino;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactoInstituicaoEnsino entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactoInstituicaoEnsinoRepository extends JpaRepository<ContactoInstituicaoEnsino, Long> {

}
