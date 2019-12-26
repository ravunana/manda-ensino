package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Emolumento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Emolumento} entity.
 */
public interface EmolumentoSearchRepository extends ElasticsearchRepository<Emolumento, Long> {
}
