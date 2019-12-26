package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Pagamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pagamento} entity.
 */
public interface PagamentoSearchRepository extends ElasticsearchRepository<Pagamento, Long> {
}
