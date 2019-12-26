package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Arquivo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Arquivo} entity.
 */
public interface ArquivoSearchRepository extends ElasticsearchRepository<Arquivo, Long> {
}
