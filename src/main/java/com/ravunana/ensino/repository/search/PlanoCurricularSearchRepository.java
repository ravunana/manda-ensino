package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.PlanoCurricular;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlanoCurricular} entity.
 */
public interface PlanoCurricularSearchRepository extends ElasticsearchRepository<PlanoCurricular, Long> {
}
