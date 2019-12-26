package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Curso;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Curso} entity.
 */
public interface CursoSearchRepository extends ElasticsearchRepository<Curso, Long> {
}
