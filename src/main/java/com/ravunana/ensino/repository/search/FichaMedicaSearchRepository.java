package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.FichaMedica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FichaMedica} entity.
 */
public interface FichaMedicaSearchRepository extends ElasticsearchRepository<FichaMedica, Long> {
}
