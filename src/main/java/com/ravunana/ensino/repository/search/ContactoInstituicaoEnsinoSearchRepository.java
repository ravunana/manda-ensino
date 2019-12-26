package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.ContactoInstituicaoEnsino;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ContactoInstituicaoEnsino} entity.
 */
public interface ContactoInstituicaoEnsinoSearchRepository extends ElasticsearchRepository<ContactoInstituicaoEnsino, Long> {
}
