package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.DetalheOcorrencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DetalheOcorrencia} entity.
 */
public interface DetalheOcorrenciaSearchRepository extends ElasticsearchRepository<DetalheOcorrencia, Long> {
}
