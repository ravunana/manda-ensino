package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.RelacionamentoPessoa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RelacionamentoPessoa} entity.
 */
public interface RelacionamentoPessoaSearchRepository extends ElasticsearchRepository<RelacionamentoPessoa, Long> {
}
