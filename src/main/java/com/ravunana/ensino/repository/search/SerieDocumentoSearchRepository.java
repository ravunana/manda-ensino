package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.SerieDocumento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SerieDocumento} entity.
 */
public interface SerieDocumentoSearchRepository extends ElasticsearchRepository<SerieDocumento, Long> {
}
