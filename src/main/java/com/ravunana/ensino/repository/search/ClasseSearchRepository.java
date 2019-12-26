package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.Classe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Classe} entity.
 */
public interface ClasseSearchRepository extends ElasticsearchRepository<Classe, Long> {
}
