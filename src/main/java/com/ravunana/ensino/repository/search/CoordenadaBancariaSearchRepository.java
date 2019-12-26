package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.CoordenadaBancaria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CoordenadaBancaria} entity.
 */
public interface CoordenadaBancariaSearchRepository extends ElasticsearchRepository<CoordenadaBancaria, Long> {
}
