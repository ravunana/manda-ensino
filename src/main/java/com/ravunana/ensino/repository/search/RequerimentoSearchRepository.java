package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Requerimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Requerimento} entity.
 */
public interface RequerimentoSearchRepository extends ElasticsearchRepository<Requerimento, Long> {
}
