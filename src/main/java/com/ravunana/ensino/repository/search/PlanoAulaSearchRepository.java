package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.PlanoAula;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlanoAula} entity.
 */
public interface PlanoAulaSearchRepository extends ElasticsearchRepository<PlanoAula, Long> {
}
