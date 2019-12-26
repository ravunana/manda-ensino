package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.SerieDocumento;
import com.ravunana.ensino.repository.SerieDocumentoRepository;
import com.ravunana.ensino.repository.search.SerieDocumentoSearchRepository;
import com.ravunana.ensino.service.SerieDocumentoService;
import com.ravunana.ensino.service.dto.SerieDocumentoDTO;
import com.ravunana.ensino.service.mapper.SerieDocumentoMapper;
import com.ravunana.ensino.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.ravunana.ensino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SerieDocumentoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class SerieDocumentoResourceIT {

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCIA = 1;
    private static final Integer UPDATED_SEQUENCIA = 2;

    private static final String DEFAULT_ENTIDADE = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDADE = "BBBBBBBBBB";

    @Autowired
    private SerieDocumentoRepository serieDocumentoRepository;

    @Autowired
    private SerieDocumentoMapper serieDocumentoMapper;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.SerieDocumentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private SerieDocumentoSearchRepository mockSerieDocumentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSerieDocumentoMockMvc;

    private SerieDocumento serieDocumento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SerieDocumentoResource serieDocumentoResource = new SerieDocumentoResource(serieDocumentoService);
        this.restSerieDocumentoMockMvc = MockMvcBuilders.standaloneSetup(serieDocumentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SerieDocumento createEntity(EntityManager em) {
        SerieDocumento serieDocumento = new SerieDocumento()
            .serie(DEFAULT_SERIE)
            .sequencia(DEFAULT_SEQUENCIA)
            .entidade(DEFAULT_ENTIDADE);
        return serieDocumento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SerieDocumento createUpdatedEntity(EntityManager em) {
        SerieDocumento serieDocumento = new SerieDocumento()
            .serie(UPDATED_SERIE)
            .sequencia(UPDATED_SEQUENCIA)
            .entidade(UPDATED_ENTIDADE);
        return serieDocumento;
    }

    @BeforeEach
    public void initTest() {
        serieDocumento = createEntity(em);
    }

    @Test
    @Transactional
    public void createSerieDocumento() throws Exception {
        int databaseSizeBeforeCreate = serieDocumentoRepository.findAll().size();

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);
        restSerieDocumentoMockMvc.perform(post("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isCreated());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testSerieDocumento.getSequencia()).isEqualTo(DEFAULT_SEQUENCIA);
        assertThat(testSerieDocumento.getEntidade()).isEqualTo(DEFAULT_ENTIDADE);

        // Validate the SerieDocumento in Elasticsearch
        verify(mockSerieDocumentoSearchRepository, times(1)).save(testSerieDocumento);
    }

    @Test
    @Transactional
    public void createSerieDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serieDocumentoRepository.findAll().size();

        // Create the SerieDocumento with an existing ID
        serieDocumento.setId(1L);
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSerieDocumentoMockMvc.perform(post("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the SerieDocumento in Elasticsearch
        verify(mockSerieDocumentoSearchRepository, times(0)).save(serieDocumento);
    }


    @Test
    @Transactional
    public void getAllSerieDocumentos() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList
        restSerieDocumentoMockMvc.perform(get("/api/serie-documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serieDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].sequencia").value(hasItem(DEFAULT_SEQUENCIA)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE)));
    }
    
    @Test
    @Transactional
    public void getSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get the serieDocumento
        restSerieDocumentoMockMvc.perform(get("/api/serie-documentos/{id}", serieDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serieDocumento.getId().intValue()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
            .andExpect(jsonPath("$.sequencia").value(DEFAULT_SEQUENCIA))
            .andExpect(jsonPath("$.entidade").value(DEFAULT_ENTIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingSerieDocumento() throws Exception {
        // Get the serieDocumento
        restSerieDocumentoMockMvc.perform(get("/api/serie-documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();

        // Update the serieDocumento
        SerieDocumento updatedSerieDocumento = serieDocumentoRepository.findById(serieDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedSerieDocumento are not directly saved in db
        em.detach(updatedSerieDocumento);
        updatedSerieDocumento
            .serie(UPDATED_SERIE)
            .sequencia(UPDATED_SEQUENCIA)
            .entidade(UPDATED_ENTIDADE);
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(updatedSerieDocumento);

        restSerieDocumentoMockMvc.perform(put("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isOk());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testSerieDocumento.getSequencia()).isEqualTo(UPDATED_SEQUENCIA);
        assertThat(testSerieDocumento.getEntidade()).isEqualTo(UPDATED_ENTIDADE);

        // Validate the SerieDocumento in Elasticsearch
        verify(mockSerieDocumentoSearchRepository, times(1)).save(testSerieDocumento);
    }

    @Test
    @Transactional
    public void updateNonExistingSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc.perform(put("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SerieDocumento in Elasticsearch
        verify(mockSerieDocumentoSearchRepository, times(0)).save(serieDocumento);
    }

    @Test
    @Transactional
    public void deleteSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeDelete = serieDocumentoRepository.findAll().size();

        // Delete the serieDocumento
        restSerieDocumentoMockMvc.perform(delete("/api/serie-documentos/{id}", serieDocumento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SerieDocumento in Elasticsearch
        verify(mockSerieDocumentoSearchRepository, times(1)).deleteById(serieDocumento.getId());
    }

    @Test
    @Transactional
    public void searchSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);
        when(mockSerieDocumentoSearchRepository.search(queryStringQuery("id:" + serieDocumento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serieDocumento), PageRequest.of(0, 1), 1));
        // Search the serieDocumento
        restSerieDocumentoMockMvc.perform(get("/api/_search/serie-documentos?query=id:" + serieDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serieDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].sequencia").value(hasItem(DEFAULT_SEQUENCIA)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE)));
    }
}
