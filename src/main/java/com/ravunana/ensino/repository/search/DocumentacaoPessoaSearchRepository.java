package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.DocumentacaoPessoa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DocumentacaoPessoa} entity.
 */
public interface DocumentacaoPessoaSearchRepository extends ElasticsearchRepository<DocumentacaoPessoa, Long> {
}
