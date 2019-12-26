package com.ravunana.ensino.repository;

import com.ravunana.ensino.domain.AssinaturaDigital;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AssinaturaDigital entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssinaturaDigitalRepository extends JpaRepository<AssinaturaDigital, Long> {

}
