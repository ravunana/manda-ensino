package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.UnidadeCurricular;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UnidadeCurricular} entity.
 */
public interface UnidadeCurricularSearchRepository extends ElasticsearchRepository<UnidadeCurricular, Long> {
}
