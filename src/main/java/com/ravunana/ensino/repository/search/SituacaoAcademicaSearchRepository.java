package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.SituacaoAcademica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SituacaoAcademica} entity.
 */
public interface SituacaoAcademicaSearchRepository extends ElasticsearchRepository<SituacaoAcademica, Long> {
}
