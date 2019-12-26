package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Pessoa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pessoa} entity.
 */
public interface PessoaSearchRepository extends ElasticsearchRepository<Pessoa, Long> {
}
