package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Aluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Aluno} entity.
 */
public interface AlunoSearchRepository extends ElasticsearchRepository<Aluno, Long> {
}
