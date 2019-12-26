package com.ravunana.ensino.repository.search;

import com.ravunana.ensino.domain.LocalizacaoInstituicaoEnsino;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LocalizacaoInstituicaoEnsino} entity.
 */
public interface LocalizacaoInstituicaoEnsinoSearchRepository extends ElasticsearchRepository<LocalizacaoInstituicaoEnsino, Long> {
}
