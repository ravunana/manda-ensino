package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.DetalhePagamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DetalhePagamento} entity.
 */
public interface DetalhePagamentoSearchRepository extends ElasticsearchRepository<DetalhePagamento, Long> {
}
