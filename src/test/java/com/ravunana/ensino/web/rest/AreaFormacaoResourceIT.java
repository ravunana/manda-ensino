package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.AreaFormacao;
import com.ravunana.ensino.repository.AreaFormacaoRepository;
import com.ravunana.ensino.repository.search.AreaFormacaoSearchRepository;
import com.ravunana.ensino.service.AreaFormacaoService;
import com.ravunana.ensino.service.dto.AreaFormacaoDTO;
import com.ravunana.ensino.service.mapper.AreaFormacaoMapper;
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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link AreaFormacaoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class AreaFormacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETENCIAS = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCIAS = "BBBBBBBBBB";

    @Autowired
    private AreaFormacaoRepository areaFormacaoRepository;

    @Autowired
    private AreaFormacaoMapper areaFormacaoMapper;

    @Autowired
    private AreaFormacaoService areaFormacaoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.AreaFormacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AreaFormacaoSearchRepository mockAreaFormacaoSearchRepository;

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

    private MockMvc restAreaFormacaoMockMvc;

    private AreaFormacao areaFormacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AreaFormacaoResource areaFormacaoResource = new AreaFormacaoResource(areaFormacaoService);
        this.restAreaFormacaoMockMvc = MockMvcBuilders.standaloneSetup(areaFormacaoResource)
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
    public static AreaFormacao createEntity(EntityManager em) {
        AreaFormacao areaFormacao = new AreaFormacao()
            .nome(DEFAULT_NOME)
            .competencias(DEFAULT_COMPETENCIAS);
        return areaFormacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaFormacao createUpdatedEntity(EntityManager em) {
        AreaFormacao areaFormacao = new AreaFormacao()
            .nome(UPDATED_NOME)
            .competencias(UPDATED_COMPETENCIAS);
        return areaFormacao;
    }

    @BeforeEach
    public void initTest() {
        areaFormacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createAreaFormacao() throws Exception {
        int databaseSizeBeforeCreate = areaFormacaoRepository.findAll().size();

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);
        restAreaFormacaoMockMvc.perform(post("/api/area-formacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeCreate + 1);
        AreaFormacao testAreaFormacao = areaFormacaoList.get(areaFormacaoList.size() - 1);
        assertThat(testAreaFormacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAreaFormacao.getCompetencias()).isEqualTo(DEFAULT_COMPETENCIAS);

        // Validate the AreaFormacao in Elasticsearch
        verify(mockAreaFormacaoSearchRepository, times(1)).save(testAreaFormacao);
    }

    @Test
    @Transactional
    public void createAreaFormacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaFormacaoRepository.findAll().size();

        // Create the AreaFormacao with an existing ID
        areaFormacao.setId(1L);
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaFormacaoMockMvc.perform(post("/api/area-formacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the AreaFormacao in Elasticsearch
        verify(mockAreaFormacaoSearchRepository, times(0)).save(areaFormacao);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaFormacaoRepository.findAll().size();
        // set the field null
        areaFormacao.setNome(null);

        // Create the AreaFormacao, which fails.
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        restAreaFormacaoMockMvc.perform(post("/api/area-formacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO)))
            .andExpect(status().isBadRequest());

        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAreaFormacaos() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        // Get all the areaFormacaoList
        restAreaFormacaoMockMvc.perform(get("/api/area-formacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaFormacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].competencias").value(hasItem(DEFAULT_COMPETENCIAS.toString())));
    }
    
    @Test
    @Transactional
    public void getAreaFormacao() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        // Get the areaFormacao
        restAreaFormacaoMockMvc.perform(get("/api/area-formacaos/{id}", areaFormacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(areaFormacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.competencias").value(DEFAULT_COMPETENCIAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAreaFormacao() throws Exception {
        // Get the areaFormacao
        restAreaFormacaoMockMvc.perform(get("/api/area-formacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAreaFormacao() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();

        // Update the areaFormacao
        AreaFormacao updatedAreaFormacao = areaFormacaoRepository.findById(areaFormacao.getId()).get();
        // Disconnect from session so that the updates on updatedAreaFormacao are not directly saved in db
        em.detach(updatedAreaFormacao);
        updatedAreaFormacao
            .nome(UPDATED_NOME)
            .competencias(UPDATED_COMPETENCIAS);
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(updatedAreaFormacao);

        restAreaFormacaoMockMvc.perform(put("/api/area-formacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO)))
            .andExpect(status().isOk());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);
        AreaFormacao testAreaFormacao = areaFormacaoList.get(areaFormacaoList.size() - 1);
        assertThat(testAreaFormacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAreaFormacao.getCompetencias()).isEqualTo(UPDATED_COMPETENCIAS);

        // Validate the AreaFormacao in Elasticsearch
        verify(mockAreaFormacaoSearchRepository, times(1)).save(testAreaFormacao);
    }

    @Test
    @Transactional
    public void updateNonExistingAreaFormacao() throws Exception {
        int databaseSizeBeforeUpdate = areaFormacaoRepository.findAll().size();

        // Create the AreaFormacao
        AreaFormacaoDTO areaFormacaoDTO = areaFormacaoMapper.toDto(areaFormacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaFormacaoMockMvc.perform(put("/api/area-formacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaFormacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaFormacao in the database
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AreaFormacao in Elasticsearch
        verify(mockAreaFormacaoSearchRepository, times(0)).save(areaFormacao);
    }

    @Test
    @Transactional
    public void deleteAreaFormacao() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);

        int databaseSizeBeforeDelete = areaFormacaoRepository.findAll().size();

        // Delete the areaFormacao
        restAreaFormacaoMockMvc.perform(delete("/api/area-formacaos/{id}", areaFormacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AreaFormacao> areaFormacaoList = areaFormacaoRepository.findAll();
        assertThat(areaFormacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AreaFormacao in Elasticsearch
        verify(mockAreaFormacaoSearchRepository, times(1)).deleteById(areaFormacao.getId());
    }

    @Test
    @Transactional
    public void searchAreaFormacao() throws Exception {
        // Initialize the database
        areaFormacaoRepository.saveAndFlush(areaFormacao);
        when(mockAreaFormacaoSearchRepository.search(queryStringQuery("id:" + areaFormacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(areaFormacao), PageRequest.of(0, 1), 1));
        // Search the areaFormacao
        restAreaFormacaoMockMvc.perform(get("/api/_search/area-formacaos?query=id:" + areaFormacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaFormacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].competencias").value(hasItem(DEFAULT_COMPETENCIAS.toString())));
    }
}
