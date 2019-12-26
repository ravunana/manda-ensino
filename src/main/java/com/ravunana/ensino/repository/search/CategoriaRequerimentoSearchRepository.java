package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.CategoriaRequerimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CategoriaRequerimento} entity.
 */
public interface CategoriaRequerimentoSearchRepository extends ElasticsearchRepository<CategoriaRequerimento, Long> {
}
