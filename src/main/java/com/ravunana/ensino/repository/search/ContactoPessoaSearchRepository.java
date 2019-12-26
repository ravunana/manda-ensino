package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.ContactoPessoa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ContactoPessoa} entity.
 */
public interface ContactoPessoaSearchRepository extends ElasticsearchRepository<ContactoPessoa, Long> {
}
