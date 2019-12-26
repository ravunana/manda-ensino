package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.EncarregadoEducacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EncarregadoEducacao} entity.
 */
public interface EncarregadoEducacaoSearchRepository extends ElasticsearchRepository<EncarregadoEducacao, Long> {
}
