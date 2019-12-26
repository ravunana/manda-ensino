package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.InstituicaoEnsino;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InstituicaoEnsino} entity.
 */
public interface InstituicaoEnsinoSearchRepository extends ElasticsearchRepository<InstituicaoEnsino, Long> {
}
