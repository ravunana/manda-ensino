package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.CategoriaRequerimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaRequerimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaRequerimentoRepository extends JpaRepository<CategoriaRequerimento, Long> {

}
