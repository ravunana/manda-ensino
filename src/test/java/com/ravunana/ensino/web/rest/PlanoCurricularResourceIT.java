package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.PlanoCurricular;
import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.domain.Classe;
import com.ravunana.ensino.repository.PlanoCurricularRepository;
import com.ravunana.ensino.repository.search.PlanoCurricularSearchRepository;
import com.ravunana.ensino.service.PlanoCurricularService;
import com.ravunana.ensino.service.dto.PlanoCurricularDTO;
import com.ravunana.ensino.service.mapper.PlanoCurricularMapper;
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
 * Integration tests for the {@link PlanoCurricularResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class PlanoCurricularResourceIT {

    private static final Integer DEFAULT_CARGA_HORARIA = 1;
    private static final Integer UPDATED_CARGA_HORARIA = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TERMINAL = false;
    private static final Boolean UPDATED_TERMINAL = true;

    private static final String DEFAULT_REGIME_CURRICULAR = "AAAAAAAAAA";
    private static final String UPDATED_REGIME_CURRICULAR = "BBBBBBBBBB";

    private static final String DEFAULT_COMPONENTE = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENTE = "BBBBBBBBBB";

    @Autowired
    private PlanoCurricularRepository planoCurricularRepository;

    @Autowired
    private PlanoCurricularMapper planoCurricularMapper;

    @Autowired
    private PlanoCurricularService planoCurricularService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.PlanoCurricularSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanoCurricularSearchRepository mockPlanoCurricularSearchRepository;

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

    private MockMvc restPlanoCurricularMockMvc;

    private PlanoCurricular planoCurricular;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanoCurricularResource planoCurricularResource = new PlanoCurricularResource(planoCurricularService);
        this.restPlanoCurricularMockMvc = MockMvcBuilders.standaloneSetup(planoCurricularResource)
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
    public static PlanoCurricular createEntity(EntityManager em) {
        PlanoCurricular planoCurricular = new PlanoCurricular()
            .cargaHoraria(DEFAULT_CARGA_HORARIA)
            .descricao(DEFAULT_DESCRICAO)
            .terminal(DEFAULT_TERMINAL)
            .regimeCurricular(DEFAULT_REGIME_CURRICULAR)
            .componente(DEFAULT_COMPONENTE);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        planoCurricular.setDisciplina(disciplina);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        planoCurricular.setClasse(classe);
        return planoCurricular;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoCurricular createUpdatedEntity(EntityManager em) {
        PlanoCurricular planoCurricular = new PlanoCurricular()
            .cargaHoraria(UPDATED_CARGA_HORARIA)
            .descricao(UPDATED_DESCRICAO)
            .terminal(UPDATED_TERMINAL)
            .regimeCurricular(UPDATED_REGIME_CURRICULAR)
            .componente(UPDATED_COMPONENTE);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        planoCurricular.setDisciplina(disciplina);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createUpdatedEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        planoCurricular.setClasse(classe);
        return planoCurricular;
    }

    @BeforeEach
    public void initTest() {
        planoCurricular = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoCurricular() throws Exception {
        int databaseSizeBeforeCreate = planoCurricularRepository.findAll().size();

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);
        restPlanoCurricularMockMvc.perform(post("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoCurricular testPlanoCurricular = planoCurricularList.get(planoCurricularList.size() - 1);
        assertThat(testPlanoCurricular.getCargaHoraria()).isEqualTo(DEFAULT_CARGA_HORARIA);
        assertThat(testPlanoCurricular.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoCurricular.isTerminal()).isEqualTo(DEFAULT_TERMINAL);
        assertThat(testPlanoCurricular.getRegimeCurricular()).isEqualTo(DEFAULT_REGIME_CURRICULAR);
        assertThat(testPlanoCurricular.getComponente()).isEqualTo(DEFAULT_COMPONENTE);

        // Validate the PlanoCurricular in Elasticsearch
        verify(mockPlanoCurricularSearchRepository, times(1)).save(testPlanoCurricular);
    }

    @Test
    @Transactional
    public void createPlanoCurricularWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoCurricularRepository.findAll().size();

        // Create the PlanoCurricular with an existing ID
        planoCurricular.setId(1L);
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoCurricularMockMvc.perform(post("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlanoCurricular in Elasticsearch
        verify(mockPlanoCurricularSearchRepository, times(0)).save(planoCurricular);
    }


    @Test
    @Transactional
    public void checkCargaHorariaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setCargaHoraria(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc.perform(post("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setDescricao(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc.perform(post("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTerminalIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setTerminal(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc.perform(post("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegimeCurricularIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setRegimeCurricular(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc.perform(post("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComponenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoCurricularRepository.findAll().size();
        // set the field null
        planoCurricular.setComponente(null);

        // Create the PlanoCurricular, which fails.
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        restPlanoCurricularMockMvc.perform(post("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoCurriculars() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get all the planoCurricularList
        restPlanoCurricularMockMvc.perform(get("/api/plano-curriculars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].cargaHoraria").value(hasItem(DEFAULT_CARGA_HORARIA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].terminal").value(hasItem(DEFAULT_TERMINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].regimeCurricular").value(hasItem(DEFAULT_REGIME_CURRICULAR)))
            .andExpect(jsonPath("$.[*].componente").value(hasItem(DEFAULT_COMPONENTE)));
    }
    
    @Test
    @Transactional
    public void getPlanoCurricular() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        // Get the planoCurricular
        restPlanoCurricularMockMvc.perform(get("/api/plano-curriculars/{id}", planoCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planoCurricular.getId().intValue()))
            .andExpect(jsonPath("$.cargaHoraria").value(DEFAULT_CARGA_HORARIA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.terminal").value(DEFAULT_TERMINAL.booleanValue()))
            .andExpect(jsonPath("$.regimeCurricular").value(DEFAULT_REGIME_CURRICULAR))
            .andExpect(jsonPath("$.componente").value(DEFAULT_COMPONENTE));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoCurricular() throws Exception {
        // Get the planoCurricular
        restPlanoCurricularMockMvc.perform(get("/api/plano-curriculars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoCurricular() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();

        // Update the planoCurricular
        PlanoCurricular updatedPlanoCurricular = planoCurricularRepository.findById(planoCurricular.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoCurricular are not directly saved in db
        em.detach(updatedPlanoCurricular);
        updatedPlanoCurricular
            .cargaHoraria(UPDATED_CARGA_HORARIA)
            .descricao(UPDATED_DESCRICAO)
            .terminal(UPDATED_TERMINAL)
            .regimeCurricular(UPDATED_REGIME_CURRICULAR)
            .componente(UPDATED_COMPONENTE);
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(updatedPlanoCurricular);

        restPlanoCurricularMockMvc.perform(put("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);
        PlanoCurricular testPlanoCurricular = planoCurricularList.get(planoCurricularList.size() - 1);
        assertThat(testPlanoCurricular.getCargaHoraria()).isEqualTo(UPDATED_CARGA_HORARIA);
        assertThat(testPlanoCurricular.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoCurricular.isTerminal()).isEqualTo(UPDATED_TERMINAL);
        assertThat(testPlanoCurricular.getRegimeCurricular()).isEqualTo(UPDATED_REGIME_CURRICULAR);
        assertThat(testPlanoCurricular.getComponente()).isEqualTo(UPDATED_COMPONENTE);

        // Validate the PlanoCurricular in Elasticsearch
        verify(mockPlanoCurricularSearchRepository, times(1)).save(testPlanoCurricular);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoCurricular() throws Exception {
        int databaseSizeBeforeUpdate = planoCurricularRepository.findAll().size();

        // Create the PlanoCurricular
        PlanoCurricularDTO planoCurricularDTO = planoCurricularMapper.toDto(planoCurricular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoCurricularMockMvc.perform(put("/api/plano-curriculars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoCurricularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoCurricular in the database
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlanoCurricular in Elasticsearch
        verify(mockPlanoCurricularSearchRepository, times(0)).save(planoCurricular);
    }

    @Test
    @Transactional
    public void deletePlanoCurricular() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);

        int databaseSizeBeforeDelete = planoCurricularRepository.findAll().size();

        // Delete the planoCurricular
        restPlanoCurricularMockMvc.perform(delete("/api/plano-curriculars/{id}", planoCurricular.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoCurricular> planoCurricularList = planoCurricularRepository.findAll();
        assertThat(planoCurricularList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlanoCurricular in Elasticsearch
        verify(mockPlanoCurricularSearchRepository, times(1)).deleteById(planoCurricular.getId());
    }

    @Test
    @Transactional
    public void searchPlanoCurricular() throws Exception {
        // Initialize the database
        planoCurricularRepository.saveAndFlush(planoCurricular);
        when(mockPlanoCurricularSearchRepository.search(queryStringQuery("id:" + planoCurricular.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(planoCurricular), PageRequest.of(0, 1), 1));
        // Search the planoCurricular
        restPlanoCurricularMockMvc.perform(get("/api/_search/plano-curriculars?query=id:" + planoCurricular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoCurricular.getId().intValue())))
            .andExpect(jsonPath("$.[*].cargaHoraria").value(hasItem(DEFAULT_CARGA_HORARIA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].terminal").value(hasItem(DEFAULT_TERMINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].regimeCurricular").value(hasItem(DEFAULT_REGIME_CURRICULAR)))
            .andExpect(jsonPath("$.[*].componente").value(hasItem(DEFAULT_COMPONENTE)));
    }
}
