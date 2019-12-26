package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.PlanoActividade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlanoActividade} entity.
 */
public interface PlanoActividadeSearchRepository extends ElasticsearchRepository<PlanoActividade, Long> {
}
