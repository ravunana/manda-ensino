package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.DocumentoMatricula;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentoMatricula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoMatriculaRepository extends JpaRepository<DocumentoMatricula, Long> {

}
