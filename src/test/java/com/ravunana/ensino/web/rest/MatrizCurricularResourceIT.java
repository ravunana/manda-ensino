package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.MatrizCurricular;
import com.ravunana.ensino.domain.Curso;
import com.ravunana.ensino.repository.MatrizCurricularRepository;
import com.ravunana.ensino.repository.search.MatrizCurricularSearchRepository;
import com.ravunana.ensino.service.MatrizCurricularService;
import com.ravunana.ensino.service.dto.MatrizCurricularDTO;
import com.ravunana.ensino.service.mapper.MatrizCurricularMapper;
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
 * Integration tests for the {@link MatrizCurricularResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class MatrizCurricularResourceIT {

    @Autowired
    private MatrizCurricularRepository matrizCurricularRepository;

    @Autowired
    private MatrizCurricularMapper matrizCurricularMapper;

    @Autowired
    private MatrizCurricularService matrizCurricularService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.MatrizCurricularSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatrizCurricularSearchRepository mockMatrizCurricularSearchRepository;

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

    private MockMvc restMatrizCurricularMockMvc;

    private MatrizCurricular matrizCurricular;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatrizCurricularResource matrizCurricularResource = new MatrizCurricularResource(matrizCurricularService);
        this.restMatrizCurricularMockMvc = MockMvcBuilders.standaloneSetup(matrizCurricularResource)
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
    public static MatrizCurricular createEntity(EntityManager em) {
        MatrizCurricular matrizCurricular = new MatrizCurricular();
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        matrizCurricular.setCurso(curso);
        return matrizCurricular;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatrizCurricular createUpdatedEntity(EntityManager em) {
        MatrizCurricular matrizCurricular = new MatrizCurricular();
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createUpdatedEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        matrizCurricular.setCurso(curso);
        return matrizCurricular;
    }

    @BeforeEach
    public void initTest() {
        matrizCurricular = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatrizCurricular() throws Exception {
        int databaseSizeBeforeCreate = matrizCurricularRepository.findAll().size();

        // Create the MatrizCurricular
        MatrizCurricularDTO matrizCurricularDTO = matrizCurricularMapper.toDto(matrizCurricular);
        restMatrizCurricularMockMvc.perform(post("/api/matriz-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matrizCurricularDTO)))
            .andExpect(status().isCreated());

        // Validate the MatrizCurricular in the database
        List<MatrizCurricular> matrizCurricularList = matrizCurricularRepository.findAll();
        assertThat(matrizCurricularList).hasSize(databaseSizeBeforeCreate + 1);
        MatrizCurricular testMatrizCurricular = matrizCurricularList.get(matrizCurricularList.size() - 1);

        // Validate the MatrizCurricular in Elasticsearch
        verify(mockMatrizCurricularSearchRepository, times(1)).save(testMatrizCurricular);
    }

    @Test
    @Transactional
    public void createMatrizCurricularWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matrizCurricularRepository.findAll().size();

        // Create the MatrizCurricular with an existing ID
        matrizCurricular.setId(1L);
        MatrizCurricularDTO matrizCurricularDTO = matrizCurricularMapper.toDto(matrizCurricular);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatrizCurricularMockMvc.perform(post("/api/matriz-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matrizCurricularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatrizCurricular in the database
        List<MatrizCurricular> matrizCurricularList = matrizCurricularRepository.findAll();
        assertThat(matrizCurricularList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatrizCurricular in Elasticsearch
        verify(mockMatrizCurricularSearchRepository, times(0)).save(matrizCurricular);
    }


    @Test
    @Transactional
    public void getAllMatrizCurriculars() throws Exception {
        // Initialize the database
        matrizCurricularRepository.saveAndFlush(matrizCurricular);

        // Get all the matrizCurricularList
        restMatrizCurricularMockMvc.perform(get("/api/matriz-curriculars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matrizCurricular.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMatrizCurricular() throws Exception {
        // Initialize the database
        matrizCurricularRepository.saveAndFlush(matrizCurricular);

        // Get the matrizCurricular
        restMatrizCurricularMockMvc.perform(get("/api/matriz-curriculars/{id}", matrizCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matrizCurricular.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMatrizCurricular() throws Exception {
        // Get the matrizCurricular
        restMatrizCurricularMockMvc.perform(get("/api/matriz-curriculars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatrizCurricular() throws Exception {
        // Initialize the database
        matrizCurricularRepository.saveAndFlush(matrizCurricular);

        int databaseSizeBeforeUpdate = matrizCurricularRepository.findAll().size();

        // Update the matrizCurricular
        MatrizCurricular updatedMatrizCurricular = matrizCurricularRepository.findById(matrizCurricular.getId()).get();
        // Disconnect from session so that the updates on updatedMatrizCurricular are not directly saved in db
        em.detach(updatedMatrizCurricular);
        MatrizCurricularDTO matrizCurricularDTO = matrizCurricularMapper.toDto(updatedMatrizCurricular);

        restMatrizCurricularMockMvc.perform(put("/api/matriz-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matrizCurricularDTO)))
            .andExpect(status().isOk());

        // Validate the MatrizCurricular in the database
        List<MatrizCurricular> matrizCurricularList = matrizCurricularRepository.findAll();
        assertThat(matrizCurricularList).hasSize(databaseSizeBeforeUpdate);
        MatrizCurricular testMatrizCurricular = matrizCurricularList.get(matrizCurricularList.size() - 1);

        // Validate the MatrizCurricular in Elasticsearch
        verify(mockMatrizCurricularSearchRepository, times(1)).save(testMatrizCurricular);
    }

    @Test
    @Transactional
    public void updateNonExistingMatrizCurricular() throws Exception {
        int databaseSizeBeforeUpdate = matrizCurricularRepository.findAll().size();

        // Create the MatrizCurricular
        MatrizCurricularDTO matrizCurricularDTO = matrizCurricularMapper.toDto(matrizCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatrizCurricularMockMvc.perform(put("/api/matriz-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matrizCurricularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatrizCurricular in the database
        List<MatrizCurricular> matrizCurricularList = matrizCurricularRepository.findAll();
        assertThat(matrizCurricularList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatrizCurricular in Elasticsearch
        verify(mockMatrizCurricularSearchRepository, times(0)).save(matrizCurricular);
    }

    @Test
    @Transactional
    public void deleteMatrizCurricular() throws Exception {
        // Initialize the database
        matrizCurricularRepository.saveAndFlush(matrizCurricular);

        int databaseSizeBeforeDelete = matrizCurricularRepository.findAll().size();

        // Delete the matrizCurricular
        restMatrizCurricularMockMvc.perform(delete("/api/matriz-curriculars/{id}", matrizCurricular.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatrizCurricular> matrizCurricularList = matrizCurricularRepository.findAll();
        assertThat(matrizCurricularList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatrizCurricular in Elasticsearch
        verify(mockMatrizCurricularSearchRepository, times(1)).deleteById(matrizCurricular.getId());
    }

    @Test
    @Transactional
    public void searchMatrizCurricular() throws Exception {
        // Initialize the database
        matrizCurricularRepository.saveAndFlush(matrizCurricular);
        when(mockMatrizCurricularSearchRepository.search(queryStringQuery("id:" + matrizCurricular.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matrizCurricular), PageRequest.of(0, 1), 1));
        // Search the matrizCurricular
        restMatrizCurricularMockMvc.perform(get("/api/_search/matriz-curriculars?query=id:" + matrizCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matrizCurricular.getId().intValue())));
    }
}
