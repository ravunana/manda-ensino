package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Dossificacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Dossificacao} entity.
 */
public interface DossificacaoSearchRepository extends ElasticsearchRepository<Dossificacao, Long> {
}
