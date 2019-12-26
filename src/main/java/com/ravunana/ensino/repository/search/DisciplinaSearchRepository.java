package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Disciplina;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Disciplina} entity.
 */
public interface DisciplinaSearchRepository extends ElasticsearchRepository<Disciplina, Long> {
}
