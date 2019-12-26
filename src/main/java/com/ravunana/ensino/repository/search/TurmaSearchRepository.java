package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Turma;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Turma} entity.
 */
public interface TurmaSearchRepository extends ElasticsearchRepository<Turma, Long> {
}
