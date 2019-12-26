package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.CategoriaValiacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CategoriaValiacao} entity.
 */
public interface CategoriaValiacaoSearchRepository extends ElasticsearchRepository<CategoriaValiacao, Long> {
}
