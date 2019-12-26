package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.MoradaPessoa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MoradaPessoa} entity.
 */
public interface MoradaPessoaSearchRepository extends ElasticsearchRepository<MoradaPessoa, Long> {
}
