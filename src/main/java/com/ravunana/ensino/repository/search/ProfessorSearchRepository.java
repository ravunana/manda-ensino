package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Professor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Professor} entity.
 */
public interface ProfessorSearchRepository extends ElasticsearchRepository<Professor, Long> {
}
