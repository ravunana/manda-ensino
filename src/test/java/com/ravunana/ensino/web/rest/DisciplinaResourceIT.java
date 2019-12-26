package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.repository.DisciplinaRepository;
import com.ravunana.ensino.repository.search.DisciplinaSearchRepository;
import com.ravunana.ensino.service.DisciplinaService;
import com.ravunana.ensino.service.dto.DisciplinaDTO;
import com.ravunana.ensino.service.mapper.DisciplinaMapper;
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
 * Integration tests for the {@link DisciplinaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class DisciplinaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    @Autowired
    private DisciplinaService disciplinaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.DisciplinaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DisciplinaSearchRepository mockDisciplinaSearchRepository;

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

    private MockMvc restDisciplinaMockMvc;

    private Disciplina disciplina;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplinaResource disciplinaResource = new DisciplinaResource(disciplinaService);
        this.restDisciplinaMockMvc = MockMvcBuilders.standaloneSetup(disciplinaResource)
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
    public static Disciplina createEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA);
        return disciplina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplina createUpdatedEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA);
        return disciplina;
    }

    @BeforeEach
    public void initTest() {
        disciplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplina() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDisciplina.getSigla()).isEqualTo(DEFAULT_SIGLA);

        // Validate the Disciplina in Elasticsearch
        verify(mockDisciplinaSearchRepository, times(1)).save(testDisciplina);
    }

    @Test
    @Transactional
    public void createDisciplinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina with an existing ID
        disciplina.setId(1L);
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Disciplina in Elasticsearch
        verify(mockDisciplinaSearchRepository, times(0)).save(disciplina);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setNome(null);

        // Create the Disciplina, which fails.
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isBadRequest());

        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setSigla(null);

        // Create the Disciplina, which fails.
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isBadRequest());

        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisciplinas() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disciplina.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    public void getNonExistingDisciplina() throws Exception {
        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Update the disciplina
        Disciplina updatedDisciplina = disciplinaRepository.findById(disciplina.getId()).get();
        // Disconnect from session so that the updates on updatedDisciplina are not directly saved in db
        em.detach(updatedDisciplina);
        updatedDisciplina
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA);
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(updatedDisciplina);

        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isOk());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDisciplina.getSigla()).isEqualTo(UPDATED_SIGLA);

        // Validate the Disciplina in Elasticsearch
        verify(mockDisciplinaSearchRepository, times(1)).save(testDisciplina);
    }

    @Test
    @Transactional
    public void updateNonExistingDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Create the Disciplina
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Disciplina in Elasticsearch
        verify(mockDisciplinaSearchRepository, times(0)).save(disciplina);
    }

    @Test
    @Transactional
    public void deleteDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        int databaseSizeBeforeDelete = disciplinaRepository.findAll().size();

        // Delete the disciplina
        restDisciplinaMockMvc.perform(delete("/api/disciplinas/{id}", disciplina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Disciplina in Elasticsearch
        verify(mockDisciplinaSearchRepository, times(1)).deleteById(disciplina.getId());
    }

    @Test
    @Transactional
    public void searchDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);
        when(mockDisciplinaSearchRepository.search(queryStringQuery("id:" + disciplina.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(disciplina), PageRequest.of(0, 1), 1));
        // Search the disciplina
        restDisciplinaMockMvc.perform(get("/api/_search/disciplinas?query=id:" + disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
}
