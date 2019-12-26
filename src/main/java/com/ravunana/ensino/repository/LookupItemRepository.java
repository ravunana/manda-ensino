package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.LookupItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LookupItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookupItemRepository extends JpaRepository<LookupItem, Long> {

}
