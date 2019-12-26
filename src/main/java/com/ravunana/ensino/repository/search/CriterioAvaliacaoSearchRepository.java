package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.CriterioAvaliacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CriterioAvaliacao} entity.
 */
public interface CriterioAvaliacaoSearchRepository extends ElasticsearchRepository<CriterioAvaliacao, Long> {
}
