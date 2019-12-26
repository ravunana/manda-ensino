package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.MeioLiquidacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MeioLiquidacao} entity.
 */
public interface MeioLiquidacaoSearchRepository extends ElasticsearchRepository<MeioLiquidacao, Long> {
}
