package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Sala;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Sala} entity.
 */
public interface SalaSearchRepository extends ElasticsearchRepository<Sala, Long> {
}
