package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.UnidadeCurricular;
import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.domain.Classe;
import com.ravunana.ensino.repository.UnidadeCurricularRepository;
import com.ravunana.ensino.repository.search.UnidadeCurricularSearchRepository;
import com.ravunana.ensino.service.UnidadeCurricularService;
import com.ravunana.ensino.service.dto.UnidadeCurricularDTO;
import com.ravunana.ensino.service.mapper.UnidadeCurricularMapper;
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
 * Integration tests for the {@link UnidadeCurricularResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class UnidadeCurricularResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_UNIDADE = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;

    @Autowired
    private UnidadeCurricularMapper unidadeCurricularMapper;

    @Autowired
    private UnidadeCurricularService unidadeCurricularService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.UnidadeCurricularSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeCurricularSearchRepository mockUnidadeCurricularSearchRepository;

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

    private MockMvc restUnidadeCurricularMockMvc;

    private UnidadeCurricular unidadeCurricular;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeCurricularResource unidadeCurricularResource = new UnidadeCurricularResource(unidadeCurricularService);
        this.restUnidadeCurricularMockMvc = MockMvcBuilders.standaloneSetup(unidadeCurricularResource)
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
    public static UnidadeCurricular createEntity(EntityManager em) {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular()
            .descricao(DEFAULT_DESCRICAO)
            .unidade(DEFAULT_UNIDADE)
            .numero(DEFAULT_NUMERO);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        unidadeCurricular.setDisciplina(disciplina);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        unidadeCurricular.setClasse(classe);
        return unidadeCurricular;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeCurricular createUpdatedEntity(EntityManager em) {
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular()
            .descricao(UPDATED_DESCRICAO)
            .unidade(UPDATED_UNIDADE)
            .numero(UPDATED_NUMERO);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        unidadeCurricular.setDisciplina(disciplina);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createUpdatedEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        unidadeCurricular.setClasse(classe);
        return unidadeCurricular;
    }

    @BeforeEach
    public void initTest() {
        unidadeCurricular = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeCurricular() throws Exception {
        int databaseSizeBeforeCreate = unidadeCurricularRepository.findAll().size();

        // Create the UnidadeCurricular
        UnidadeCurricularDTO unidadeCurricularDTO = unidadeCurricularMapper.toDto(unidadeCurricular);
        restUnidadeCurricularMockMvc.perform(post("/api/unidade-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeCurricularDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadeCurricular in the database
        List<UnidadeCurricular> unidadeCurricularList = unidadeCurricularRepository.findAll();
        assertThat(unidadeCurricularList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeCurricular testUnidadeCurricular = unidadeCurricularList.get(unidadeCurricularList.size() - 1);
        assertThat(testUnidadeCurricular.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUnidadeCurricular.getUnidade()).isEqualTo(DEFAULT_UNIDADE);
        assertThat(testUnidadeCurricular.getNumero()).isEqualTo(DEFAULT_NUMERO);

        // Validate the UnidadeCurricular in Elasticsearch
        verify(mockUnidadeCurricularSearchRepository, times(1)).save(testUnidadeCurricular);
    }

    @Test
    @Transactional
    public void createUnidadeCurricularWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeCurricularRepository.findAll().size();

        // Create the UnidadeCurricular with an existing ID
        unidadeCurricular.setId(1L);
        UnidadeCurricularDTO unidadeCurricularDTO = unidadeCurricularMapper.toDto(unidadeCurricular);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeCurricularMockMvc.perform(post("/api/unidade-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeCurricularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeCurricular in the database
        List<UnidadeCurricular> unidadeCurricularList = unidadeCurricularRepository.findAll();
        assertThat(unidadeCurricularList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnidadeCurricular in Elasticsearch
        verify(mockUnidadeCurricularSearchRepository, times(0)).save(unidadeCurricular);
    }


    @Test
    @Transactional
    public void checkUnidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeCurricularRepository.findAll().size();
        // set the field null
        unidadeCurricular.setUnidade(null);

        // Create the UnidadeCurricular, which fails.
        UnidadeCurricularDTO unidadeCurricularDTO = unidadeCurricularMapper.toDto(unidadeCurricular);

        restUnidadeCurricularMockMvc.perform(post("/api/unidade-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeCurricularDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeCurricular> unidadeCurricularList = unidadeCurricularRepository.findAll();
        assertThat(unidadeCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeCurricularRepository.findAll().size();
        // set the field null
        unidadeCurricular.setNumero(null);

        // Create the UnidadeCurricular, which fails.
        UnidadeCurricularDTO unidadeCurricularDTO = unidadeCurricularMapper.toDto(unidadeCurricular);

        restUnidadeCurricularMockMvc.perform(post("/api/unidade-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeCurricularDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeCurricular> unidadeCurricularList = unidadeCurricularRepository.findAll();
        assertThat(unidadeCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeCurriculars() throws Exception {
        // Initialize the database
        unidadeCurricularRepository.saveAndFlush(unidadeCurricular);

        // Get all the unidadeCurricularList
        restUnidadeCurricularMockMvc.perform(get("/api/unidade-curriculars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getUnidadeCurricular() throws Exception {
        // Initialize the database
        unidadeCurricularRepository.saveAndFlush(unidadeCurricular);

        // Get the unidadeCurricular
        restUnidadeCurricularMockMvc.perform(get("/api/unidade-curriculars/{id}", unidadeCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeCurricular.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.unidade").value(DEFAULT_UNIDADE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeCurricular() throws Exception {
        // Get the unidadeCurricular
        restUnidadeCurricularMockMvc.perform(get("/api/unidade-curriculars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeCurricular() throws Exception {
        // Initialize the database
        unidadeCurricularRepository.saveAndFlush(unidadeCurricular);

        int databaseSizeBeforeUpdate = unidadeCurricularRepository.findAll().size();

        // Update the unidadeCurricular
        UnidadeCurricular updatedUnidadeCurricular = unidadeCurricularRepository.findById(unidadeCurricular.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeCurricular are not directly saved in db
        em.detach(updatedUnidadeCurricular);
        updatedUnidadeCurricular
            .descricao(UPDATED_DESCRICAO)
            .unidade(UPDATED_UNIDADE)
            .numero(UPDATED_NUMERO);
        UnidadeCurricularDTO unidadeCurricularDTO = unidadeCurricularMapper.toDto(updatedUnidadeCurricular);

        restUnidadeCurricularMockMvc.perform(put("/api/unidade-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeCurricularDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadeCurricular in the database
        List<UnidadeCurricular> unidadeCurricularList = unidadeCurricularRepository.findAll();
        assertThat(unidadeCurricularList).hasSize(databaseSizeBeforeUpdate);
        UnidadeCurricular testUnidadeCurricular = unidadeCurricularList.get(unidadeCurricularList.size() - 1);
        assertThat(testUnidadeCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUnidadeCurricular.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testUnidadeCurricular.getNumero()).isEqualTo(UPDATED_NUMERO);

        // Validate the UnidadeCurricular in Elasticsearch
        verify(mockUnidadeCurricularSearchRepository, times(1)).save(testUnidadeCurricular);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeCurricular() throws Exception {
        int databaseSizeBeforeUpdate = unidadeCurricularRepository.findAll().size();

        // Create the UnidadeCurricular
        UnidadeCurricularDTO unidadeCurricularDTO = unidadeCurricularMapper.toDto(unidadeCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeCurricularMockMvc.perform(put("/api/unidade-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeCurricularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeCurricular in the database
        List<UnidadeCurricular> unidadeCurricularList = unidadeCurricularRepository.findAll();
        assertThat(unidadeCurricularList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnidadeCurricular in Elasticsearch
        verify(mockUnidadeCurricularSearchRepository, times(0)).save(unidadeCurricular);
    }

    @Test
    @Transactional
    public void deleteUnidadeCurricular() throws Exception {
        // Initialize the database
        unidadeCurricularRepository.saveAndFlush(unidadeCurricular);

        int databaseSizeBeforeDelete = unidadeCurricularRepository.findAll().size();

        // Delete the unidadeCurricular
        restUnidadeCurricularMockMvc.perform(delete("/api/unidade-curriculars/{id}", unidadeCurricular.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeCurricular> unidadeCurricularList = unidadeCurricularRepository.findAll();
        assertThat(unidadeCurricularList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnidadeCurricular in Elasticsearch
        verify(mockUnidadeCurricularSearchRepository, times(1)).deleteById(unidadeCurricular.getId());
    }

    @Test
    @Transactional
    public void searchUnidadeCurricular() throws Exception {
        // Initialize the database
        unidadeCurricularRepository.saveAndFlush(unidadeCurricular);
        when(mockUnidadeCurricularSearchRepository.search(queryStringQuery("id:" + unidadeCurricular.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unidadeCurricular), PageRequest.of(0, 1), 1));
        // Search the unidadeCurricular
        restUnidadeCurricularMockMvc.perform(get("/api/_search/unidade-curriculars?query=id:" + unidadeCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
}
