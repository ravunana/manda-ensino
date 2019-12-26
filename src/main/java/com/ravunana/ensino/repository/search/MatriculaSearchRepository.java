package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Matricula;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Matricula} entity.
 */
public interface MatriculaSearchRepository extends ElasticsearchRepository<Matricula, Long> {
}
