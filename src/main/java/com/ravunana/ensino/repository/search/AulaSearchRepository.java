package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Aula;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Aula} entity.
 */
public interface AulaSearchRepository extends ElasticsearchRepository<Aula, Long> {
}
