package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.AreaFormacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AreaFormacao} entity.
 */
public interface AreaFormacaoSearchRepository extends ElasticsearchRepository<AreaFormacao, Long> {
}
