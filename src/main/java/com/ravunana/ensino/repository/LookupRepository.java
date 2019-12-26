package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.Lookup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lookup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookupRepository extends JpaRepository<Lookup, Long> {

}
