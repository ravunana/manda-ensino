package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Nota;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Nota} entity.
 */
public interface NotaSearchRepository extends ElasticsearchRepository<Nota, Long> {
}
