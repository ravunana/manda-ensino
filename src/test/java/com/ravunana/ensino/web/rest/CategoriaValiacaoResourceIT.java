package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.CategoriaValiacao;
import com.ravunana.ensino.domain.AreaFormacao;
import com.ravunana.ensino.repository.CategoriaValiacaoRepository;
import com.ravunana.ensino.repository.search.CategoriaValiacaoSearchRepository;
import com.ravunana.ensino.service.CategoriaValiacaoService;
import com.ravunana.ensino.service.dto.CategoriaValiacaoDTO;
import com.ravunana.ensino.service.mapper.CategoriaValiacaoMapper;
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
 * Integration tests for the {@link CategoriaValiacaoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class CategoriaValiacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_INTERNA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_INTERNA = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA_PAUTA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA_PAUTA = "BBBBBBBBBB";

    @Autowired
    private CategoriaValiacaoRepository categoriaValiacaoRepository;

    @Autowired
    private CategoriaValiacaoMapper categoriaValiacaoMapper;

    @Autowired
    private CategoriaValiacaoService categoriaValiacaoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.CategoriaValiacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoriaValiacaoSearchRepository mockCategoriaValiacaoSearchRepository;

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

    private MockMvc restCategoriaValiacaoMockMvc;

    private CategoriaValiacao categoriaValiacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaValiacaoResource categoriaValiacaoResource = new CategoriaValiacaoResource(categoriaValiacaoService);
        this.restCategoriaValiacaoMockMvc = MockMvcBuilders.standaloneSetup(categoriaValiacaoResource)
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
    public static CategoriaValiacao createEntity(EntityManager em) {
        CategoriaValiacao categoriaValiacao = new CategoriaValiacao()
            .nome(DEFAULT_NOME)
            .siglaInterna(DEFAULT_SIGLA_INTERNA)
            .siglaPauta(DEFAULT_SIGLA_PAUTA);
        // Add required entity
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            areaFormacao = AreaFormacaoResourceIT.createEntity(em);
            em.persist(areaFormacao);
            em.flush();
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        categoriaValiacao.setAreaFormacao(areaFormacao);
        return categoriaValiacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaValiacao createUpdatedEntity(EntityManager em) {
        CategoriaValiacao categoriaValiacao = new CategoriaValiacao()
            .nome(UPDATED_NOME)
            .siglaInterna(UPDATED_SIGLA_INTERNA)
            .siglaPauta(UPDATED_SIGLA_PAUTA);
        // Add required entity
        AreaFormacao areaFormacao;
        if (TestUtil.findAll(em, AreaFormacao.class).isEmpty()) {
            areaFormacao = AreaFormacaoResourceIT.createUpdatedEntity(em);
            em.persist(areaFormacao);
            em.flush();
        } else {
            areaFormacao = TestUtil.findAll(em, AreaFormacao.class).get(0);
        }
        categoriaValiacao.setAreaFormacao(areaFormacao);
        return categoriaValiacao;
    }

    @BeforeEach
    public void initTest() {
        categoriaValiacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaValiacao() throws Exception {
        int databaseSizeBeforeCreate = categoriaValiacaoRepository.findAll().size();

        // Create the CategoriaValiacao
        CategoriaValiacaoDTO categoriaValiacaoDTO = categoriaValiacaoMapper.toDto(categoriaValiacao);
        restCategoriaValiacaoMockMvc.perform(post("/api/categoria-valiacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaValiacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoriaValiacao in the database
        List<CategoriaValiacao> categoriaValiacaoList = categoriaValiacaoRepository.findAll();
        assertThat(categoriaValiacaoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaValiacao testCategoriaValiacao = categoriaValiacaoList.get(categoriaValiacaoList.size() - 1);
        assertThat(testCategoriaValiacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCategoriaValiacao.getSiglaInterna()).isEqualTo(DEFAULT_SIGLA_INTERNA);
        assertThat(testCategoriaValiacao.getSiglaPauta()).isEqualTo(DEFAULT_SIGLA_PAUTA);

        // Validate the CategoriaValiacao in Elasticsearch
        verify(mockCategoriaValiacaoSearchRepository, times(1)).save(testCategoriaValiacao);
    }

    @Test
    @Transactional
    public void createCategoriaValiacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaValiacaoRepository.findAll().size();

        // Create the CategoriaValiacao with an existing ID
        categoriaValiacao.setId(1L);
        CategoriaValiacaoDTO categoriaValiacaoDTO = categoriaValiacaoMapper.toDto(categoriaValiacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaValiacaoMockMvc.perform(post("/api/categoria-valiacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaValiacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaValiacao in the database
        List<CategoriaValiacao> categoriaValiacaoList = categoriaValiacaoRepository.findAll();
        assertThat(categoriaValiacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CategoriaValiacao in Elasticsearch
        verify(mockCategoriaValiacaoSearchRepository, times(0)).save(categoriaValiacao);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaValiacaoRepository.findAll().size();
        // set the field null
        categoriaValiacao.setNome(null);

        // Create the CategoriaValiacao, which fails.
        CategoriaValiacaoDTO categoriaValiacaoDTO = categoriaValiacaoMapper.toDto(categoriaValiacao);

        restCategoriaValiacaoMockMvc.perform(post("/api/categoria-valiacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaValiacaoDTO)))
            .andExpect(status().isBadRequest());

        List<CategoriaValiacao> categoriaValiacaoList = categoriaValiacaoRepository.findAll();
        assertThat(categoriaValiacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaPautaIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaValiacaoRepository.findAll().size();
        // set the field null
        categoriaValiacao.setSiglaPauta(null);

        // Create the CategoriaValiacao, which fails.
        CategoriaValiacaoDTO categoriaValiacaoDTO = categoriaValiacaoMapper.toDto(categoriaValiacao);

        restCategoriaValiacaoMockMvc.perform(post("/api/categoria-valiacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaValiacaoDTO)))
            .andExpect(status().isBadRequest());

        List<CategoriaValiacao> categoriaValiacaoList = categoriaValiacaoRepository.findAll();
        assertThat(categoriaValiacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriaValiacaos() throws Exception {
        // Initialize the database
        categoriaValiacaoRepository.saveAndFlush(categoriaValiacao);

        // Get all the categoriaValiacaoList
        restCategoriaValiacaoMockMvc.perform(get("/api/categoria-valiacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaValiacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].siglaInterna").value(hasItem(DEFAULT_SIGLA_INTERNA)))
            .andExpect(jsonPath("$.[*].siglaPauta").value(hasItem(DEFAULT_SIGLA_PAUTA)));
    }
    
    @Test
    @Transactional
    public void getCategoriaValiacao() throws Exception {
        // Initialize the database
        categoriaValiacaoRepository.saveAndFlush(categoriaValiacao);

        // Get the categoriaValiacao
        restCategoriaValiacaoMockMvc.perform(get("/api/categoria-valiacaos/{id}", categoriaValiacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaValiacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.siglaInterna").value(DEFAULT_SIGLA_INTERNA))
            .andExpect(jsonPath("$.siglaPauta").value(DEFAULT_SIGLA_PAUTA));
    }

    @Test
    @Transactional
    public void getNonExistingCategoriaValiacao() throws Exception {
        // Get the categoriaValiacao
        restCategoriaValiacaoMockMvc.perform(get("/api/categoria-valiacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaValiacao() throws Exception {
        // Initialize the database
        categoriaValiacaoRepository.saveAndFlush(categoriaValiacao);

        int databaseSizeBeforeUpdate = categoriaValiacaoRepository.findAll().size();

        // Update the categoriaValiacao
        CategoriaValiacao updatedCategoriaValiacao = categoriaValiacaoRepository.findById(categoriaValiacao.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaValiacao are not directly saved in db
        em.detach(updatedCategoriaValiacao);
        updatedCategoriaValiacao
            .nome(UPDATED_NOME)
            .siglaInterna(UPDATED_SIGLA_INTERNA)
            .siglaPauta(UPDATED_SIGLA_PAUTA);
        CategoriaValiacaoDTO categoriaValiacaoDTO = categoriaValiacaoMapper.toDto(updatedCategoriaValiacao);

        restCategoriaValiacaoMockMvc.perform(put("/api/categoria-valiacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaValiacaoDTO)))
            .andExpect(status().isOk());

        // Validate the CategoriaValiacao in the database
        List<CategoriaValiacao> categoriaValiacaoList = categoriaValiacaoRepository.findAll();
        assertThat(categoriaValiacaoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaValiacao testCategoriaValiacao = categoriaValiacaoList.get(categoriaValiacaoList.size() - 1);
        assertThat(testCategoriaValiacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaValiacao.getSiglaInterna()).isEqualTo(UPDATED_SIGLA_INTERNA);
        assertThat(testCategoriaValiacao.getSiglaPauta()).isEqualTo(UPDATED_SIGLA_PAUTA);

        // Validate the CategoriaValiacao in Elasticsearch
        verify(mockCategoriaValiacaoSearchRepository, times(1)).save(testCategoriaValiacao);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaValiacao() throws Exception {
        int databaseSizeBeforeUpdate = categoriaValiacaoRepository.findAll().size();

        // Create the CategoriaValiacao
        CategoriaValiacaoDTO categoriaValiacaoDTO = categoriaValiacaoMapper.toDto(categoriaValiacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaValiacaoMockMvc.perform(put("/api/categoria-valiacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaValiacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaValiacao in the database
        List<CategoriaValiacao> categoriaValiacaoList = categoriaValiacaoRepository.findAll();
        assertThat(categoriaValiacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CategoriaValiacao in Elasticsearch
        verify(mockCategoriaValiacaoSearchRepository, times(0)).save(categoriaValiacao);
    }

    @Test
    @Transactional
    public void deleteCategoriaValiacao() throws Exception {
        // Initialize the database
        categoriaValiacaoRepository.saveAndFlush(categoriaValiacao);

        int databaseSizeBeforeDelete = categoriaValiacaoRepository.findAll().size();

        // Delete the categoriaValiacao
        restCategoriaValiacaoMockMvc.perform(delete("/api/categoria-valiacaos/{id}", categoriaValiacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaValiacao> categoriaValiacaoList = categoriaValiacaoRepository.findAll();
        assertThat(categoriaValiacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CategoriaValiacao in Elasticsearch
        verify(mockCategoriaValiacaoSearchRepository, times(1)).deleteById(categoriaValiacao.getId());
    }

    @Test
    @Transactional
    public void searchCategoriaValiacao() throws Exception {
        // Initialize the database
        categoriaValiacaoRepository.saveAndFlush(categoriaValiacao);
        when(mockCategoriaValiacaoSearchRepository.search(queryStringQuery("id:" + categoriaValiacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(categoriaValiacao), PageRequest.of(0, 1), 1));
        // Search the categoriaValiacao
        restCategoriaValiacaoMockMvc.perform(get("/api/_search/categoria-valiacaos?query=id:" + categoriaValiacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaValiacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].siglaInterna").value(hasItem(DEFAULT_SIGLA_INTERNA)))
            .andExpect(jsonPath("$.[*].siglaPauta").value(hasItem(DEFAULT_SIGLA_PAUTA)));
    }
}
