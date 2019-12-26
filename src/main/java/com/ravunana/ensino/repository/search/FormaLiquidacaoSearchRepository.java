package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.FormaLiquidacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FormaLiquidacao} entity.
 */
public interface FormaLiquidacaoSearchRepository extends ElasticsearchRepository<FormaLiquidacao, Long> {
}
