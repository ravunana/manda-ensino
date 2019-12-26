package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Contrato;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Contrato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

}
