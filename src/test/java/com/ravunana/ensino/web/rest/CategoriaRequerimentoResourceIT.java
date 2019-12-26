package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.CategoriaRequerimento;
import com.ravunana.ensino.repository.CategoriaRequerimentoRepository;
import com.ravunana.ensino.repository.search.CategoriaRequerimentoSearchRepository;
import com.ravunana.ensino.service.CategoriaRequerimentoService;
import com.ravunana.ensino.service.dto.CategoriaRequerimentoDTO;
import com.ravunana.ensino.service.mapper.CategoriaRequerimentoMapper;
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
 * Integration tests for the {@link CategoriaRequerimentoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class CategoriaRequerimentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEMPO_RESPOSTA = 1;
    private static final Integer UPDATED_TEMPO_RESPOSTA = 2;

    private static final Boolean DEFAULT_PAGASE = false;
    private static final Boolean UPDATED_PAGASE = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CategoriaRequerimentoRepository categoriaRequerimentoRepository;

    @Autowired
    private CategoriaRequerimentoMapper categoriaRequerimentoMapper;

    @Autowired
    private CategoriaRequerimentoService categoriaRequerimentoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.CategoriaRequerimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoriaRequerimentoSearchRepository mockCategoriaRequerimentoSearchRepository;

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

    private MockMvc restCategoriaRequerimentoMockMvc;

    private CategoriaRequerimento categoriaRequerimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaRequerimentoResource categoriaRequerimentoResource = new CategoriaRequerimentoResource(categoriaRequerimentoService);
        this.restCategoriaRequerimentoMockMvc = MockMvcBuilders.standaloneSetup(categoriaRequerimentoResource)
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
    public static CategoriaRequerimento createEntity(EntityManager em) {
        CategoriaRequerimento categoriaRequerimento = new CategoriaRequerimento()
            .nome(DEFAULT_NOME)
            .tempoResposta(DEFAULT_TEMPO_RESPOSTA)
            .pagase(DEFAULT_PAGASE)
            .descricao(DEFAULT_DESCRICAO);
        return categoriaRequerimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaRequerimento createUpdatedEntity(EntityManager em) {
        CategoriaRequerimento categoriaRequerimento = new CategoriaRequerimento()
            .nome(UPDATED_NOME)
            .tempoResposta(UPDATED_TEMPO_RESPOSTA)
            .pagase(UPDATED_PAGASE)
            .descricao(UPDATED_DESCRICAO);
        return categoriaRequerimento;
    }

    @BeforeEach
    public void initTest() {
        categoriaRequerimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaRequerimento() throws Exception {
        int databaseSizeBeforeCreate = categoriaRequerimentoRepository.findAll().size();

        // Create the CategoriaRequerimento
        CategoriaRequerimentoDTO categoriaRequerimentoDTO = categoriaRequerimentoMapper.toDto(categoriaRequerimento);
        restCategoriaRequerimentoMockMvc.perform(post("/api/categoria-requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRequerimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoriaRequerimento in the database
        List<CategoriaRequerimento> categoriaRequerimentoList = categoriaRequerimentoRepository.findAll();
        assertThat(categoriaRequerimentoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaRequerimento testCategoriaRequerimento = categoriaRequerimentoList.get(categoriaRequerimentoList.size() - 1);
        assertThat(testCategoriaRequerimento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCategoriaRequerimento.getTempoResposta()).isEqualTo(DEFAULT_TEMPO_RESPOSTA);
        assertThat(testCategoriaRequerimento.isPagase()).isEqualTo(DEFAULT_PAGASE);
        assertThat(testCategoriaRequerimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the CategoriaRequerimento in Elasticsearch
        verify(mockCategoriaRequerimentoSearchRepository, times(1)).save(testCategoriaRequerimento);
    }

    @Test
    @Transactional
    public void createCategoriaRequerimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaRequerimentoRepository.findAll().size();

        // Create the CategoriaRequerimento with an existing ID
        categoriaRequerimento.setId(1L);
        CategoriaRequerimentoDTO categoriaRequerimentoDTO = categoriaRequerimentoMapper.toDto(categoriaRequerimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaRequerimentoMockMvc.perform(post("/api/categoria-requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRequerimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaRequerimento in the database
        List<CategoriaRequerimento> categoriaRequerimentoList = categoriaRequerimentoRepository.findAll();
        assertThat(categoriaRequerimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CategoriaRequerimento in Elasticsearch
        verify(mockCategoriaRequerimentoSearchRepository, times(0)).save(categoriaRequerimento);
    }


    @Test
    @Transactional
    public void getAllCategoriaRequerimentos() throws Exception {
        // Initialize the database
        categoriaRequerimentoRepository.saveAndFlush(categoriaRequerimento);

        // Get all the categoriaRequerimentoList
        restCategoriaRequerimentoMockMvc.perform(get("/api/categoria-requerimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaRequerimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tempoResposta").value(hasItem(DEFAULT_TEMPO_RESPOSTA)))
            .andExpect(jsonPath("$.[*].pagase").value(hasItem(DEFAULT_PAGASE.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaRequerimento() throws Exception {
        // Initialize the database
        categoriaRequerimentoRepository.saveAndFlush(categoriaRequerimento);

        // Get the categoriaRequerimento
        restCategoriaRequerimentoMockMvc.perform(get("/api/categoria-requerimentos/{id}", categoriaRequerimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaRequerimento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.tempoResposta").value(DEFAULT_TEMPO_RESPOSTA))
            .andExpect(jsonPath("$.pagase").value(DEFAULT_PAGASE.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoriaRequerimento() throws Exception {
        // Get the categoriaRequerimento
        restCategoriaRequerimentoMockMvc.perform(get("/api/categoria-requerimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaRequerimento() throws Exception {
        // Initialize the database
        categoriaRequerimentoRepository.saveAndFlush(categoriaRequerimento);

        int databaseSizeBeforeUpdate = categoriaRequerimentoRepository.findAll().size();

        // Update the categoriaRequerimento
        CategoriaRequerimento updatedCategoriaRequerimento = categoriaRequerimentoRepository.findById(categoriaRequerimento.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaRequerimento are not directly saved in db
        em.detach(updatedCategoriaRequerimento);
        updatedCategoriaRequerimento
            .nome(UPDATED_NOME)
            .tempoResposta(UPDATED_TEMPO_RESPOSTA)
            .pagase(UPDATED_PAGASE)
            .descricao(UPDATED_DESCRICAO);
        CategoriaRequerimentoDTO categoriaRequerimentoDTO = categoriaRequerimentoMapper.toDto(updatedCategoriaRequerimento);

        restCategoriaRequerimentoMockMvc.perform(put("/api/categoria-requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRequerimentoDTO)))
            .andExpect(status().isOk());

        // Validate the CategoriaRequerimento in the database
        List<CategoriaRequerimento> categoriaRequerimentoList = categoriaRequerimentoRepository.findAll();
        assertThat(categoriaRequerimentoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaRequerimento testCategoriaRequerimento = categoriaRequerimentoList.get(categoriaRequerimentoList.size() - 1);
        assertThat(testCategoriaRequerimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaRequerimento.getTempoResposta()).isEqualTo(UPDATED_TEMPO_RESPOSTA);
        assertThat(testCategoriaRequerimento.isPagase()).isEqualTo(UPDATED_PAGASE);
        assertThat(testCategoriaRequerimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the CategoriaRequerimento in Elasticsearch
        verify(mockCategoriaRequerimentoSearchRepository, times(1)).save(testCategoriaRequerimento);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaRequerimento() throws Exception {
        int databaseSizeBeforeUpdate = categoriaRequerimentoRepository.findAll().size();

        // Create the CategoriaRequerimento
        CategoriaRequerimentoDTO categoriaRequerimentoDTO = categoriaRequerimentoMapper.toDto(categoriaRequerimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaRequerimentoMockMvc.perform(put("/api/categoria-requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRequerimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaRequerimento in the database
        List<CategoriaRequerimento> categoriaRequerimentoList = categoriaRequerimentoRepository.findAll();
        assertThat(categoriaRequerimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CategoriaRequerimento in Elasticsearch
        verify(mockCategoriaRequerimentoSearchRepository, times(0)).save(categoriaRequerimento);
    }

    @Test
    @Transactional
    public void deleteCategoriaRequerimento() throws Exception {
        // Initialize the database
        categoriaRequerimentoRepository.saveAndFlush(categoriaRequerimento);

        int databaseSizeBeforeDelete = categoriaRequerimentoRepository.findAll().size();

        // Delete the categoriaRequerimento
        restCategoriaRequerimentoMockMvc.perform(delete("/api/categoria-requerimentos/{id}", categoriaRequerimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaRequerimento> categoriaRequerimentoList = categoriaRequerimentoRepository.findAll();
        assertThat(categoriaRequerimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CategoriaRequerimento in Elasticsearch
        verify(mockCategoriaRequerimentoSearchRepository, times(1)).deleteById(categoriaRequerimento.getId());
    }

    @Test
    @Transactional
    public void searchCategoriaRequerimento() throws Exception {
        // Initialize the database
        categoriaRequerimentoRepository.saveAndFlush(categoriaRequerimento);
        when(mockCategoriaRequerimentoSearchRepository.search(queryStringQuery("id:" + categoriaRequerimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(categoriaRequerimento), PageRequest.of(0, 1), 1));
        // Search the categoriaRequerimento
        restCategoriaRequerimentoMockMvc.perform(get("/api/_search/categoria-requerimentos?query=id:" + categoriaRequerimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaRequerimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tempoResposta").value(hasItem(DEFAULT_TEMPO_RESPOSTA)))
            .andExpect(jsonPath("$.[*].pagase").value(hasItem(DEFAULT_PAGASE.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
}
