package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.DocumentoMatricula;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DocumentoMatricula} entity.
 */
public interface DocumentoMatriculaSearchRepository extends ElasticsearchRepository<DocumentoMatricula, Long> {
}
