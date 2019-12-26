package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Contrato;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Contrato} entity.
 */
public interface ContratoSearchRepository extends ElasticsearchRepository<Contrato, Long> {
}
