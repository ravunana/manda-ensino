package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.SerieDocumento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SerieDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SerieDocumentoRepository extends JpaRepository<SerieDocumento, Long> {

}
