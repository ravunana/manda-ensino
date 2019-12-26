package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.LookupItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LookupItem} entity.
 */
public interface LookupItemSearchRepository extends ElasticsearchRepository<LookupItem, Long> {
}
