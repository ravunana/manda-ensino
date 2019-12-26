package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.MatrizCurricular;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MatrizCurricular} entity.
 */
public interface MatrizCurricularSearchRepository extends ElasticsearchRepository<MatrizCurricular, Long> {
}
