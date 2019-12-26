package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Deposito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Deposito} entity.
 */
public interface DepositoSearchRepository extends ElasticsearchRepository<Deposito, Long> {
}
