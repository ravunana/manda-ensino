package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.LicencaSoftware;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LicencaSoftware} entity.
 */
public interface LicencaSoftwareSearchRepository extends ElasticsearchRepository<LicencaSoftware, Long> {
}
