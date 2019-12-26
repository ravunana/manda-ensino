package com.ravunana.ensino.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FichaMedicaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FichaMedicaSearchRepositoryMockConfiguration {

    @MockBean
    private FichaMedicaSearchRepository mockFichaMedicaSearchRepository;

}
