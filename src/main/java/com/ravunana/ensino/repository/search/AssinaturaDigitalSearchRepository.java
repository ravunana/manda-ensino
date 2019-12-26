package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.AssinaturaDigital;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AssinaturaDigital} entity.
 */
public interface AssinaturaDigitalSearchRepository extends ElasticsearchRepository<AssinaturaDigital, Long> {
}
