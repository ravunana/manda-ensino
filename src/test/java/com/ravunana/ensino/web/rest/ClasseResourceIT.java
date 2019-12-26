package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Classe;
import com.ravunana.ensino.repository.ClasseRepository;
import com.ravunana.ensino.repository.search.ClasseSearchRepository;
import com.ravunana.ensino.service.ClasseService;
import com.ravunana.ensino.service.dto.ClasseDTO;
import com.ravunana.ensino.service.mapper.ClasseMapper;
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
 * Integration tests for the {@link ClasseResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class ClasseResourceIT {

    private static final Integer DEFAULT_DESCRICAO = 1;
    private static final Integer UPDATED_DESCRICAO = 2;

    private static final String DEFAULT_TIPO_ENSINO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ENSINO = "BBBBBBBBBB";

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private ClasseMapper classeMapper;

    @Autowired
    private ClasseService classeService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.ClasseSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClasseSearchRepository mockClasseSearchRepository;

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

    private MockMvc restClasseMockMvc;

    private Classe classe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClasseResource classeResource = new ClasseResource(classeService);
        this.restClasseMockMvc = MockMvcBuilders.standaloneSetup(classeResource)
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
    public static Classe createEntity(EntityManager em) {
        Classe classe = new Classe()
            .descricao(DEFAULT_DESCRICAO)
            .tipoEnsino(DEFAULT_TIPO_ENSINO);
        return classe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classe createUpdatedEntity(EntityManager em) {
        Classe classe = new Classe()
            .descricao(UPDATED_DESCRICAO)
            .tipoEnsino(UPDATED_TIPO_ENSINO);
        return classe;
    }

    @BeforeEach
    public void initTest() {
        classe = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasse() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();

        // Create the Classe
        ClasseDTO classeDTO = classeMapper.toDto(classe);
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isCreated());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate + 1);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testClasse.getTipoEnsino()).isEqualTo(DEFAULT_TIPO_ENSINO);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(1)).save(testClasse);
    }

    @Test
    @Transactional
    public void createClasseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();

        // Create the Classe with an existing ID
        classe.setId(1L);
        ClasseDTO classeDTO = classeMapper.toDto(classe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(0)).save(classe);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = classeRepository.findAll().size();
        // set the field null
        classe.setDescricao(null);

        // Create the Classe, which fails.
        ClasseDTO classeDTO = classeMapper.toDto(classe);

        restClasseMockMvc.perform(post("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isBadRequest());

        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasses() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipoEnsino").value(hasItem(DEFAULT_TIPO_ENSINO)));
    }
    
    @Test
    @Transactional
    public void getClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", classe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classe.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.tipoEnsino").value(DEFAULT_TIPO_ENSINO));
    }

    @Test
    @Transactional
    public void getNonExistingClasse() throws Exception {
        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // Update the classe
        Classe updatedClasse = classeRepository.findById(classe.getId()).get();
        // Disconnect from session so that the updates on updatedClasse are not directly saved in db
        em.detach(updatedClasse);
        updatedClasse
            .descricao(UPDATED_DESCRICAO)
            .tipoEnsino(UPDATED_TIPO_ENSINO);
        ClasseDTO classeDTO = classeMapper.toDto(updatedClasse);

        restClasseMockMvc.perform(put("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isOk());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testClasse.getTipoEnsino()).isEqualTo(UPDATED_TIPO_ENSINO);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(1)).save(testClasse);
    }

    @Test
    @Transactional
    public void updateNonExistingClasse() throws Exception {
        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // Create the Classe
        ClasseDTO classeDTO = classeMapper.toDto(classe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseMockMvc.perform(put("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(0)).save(classe);
    }

    @Test
    @Transactional
    public void deleteClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeDelete = classeRepository.findAll().size();

        // Delete the classe
        restClasseMockMvc.perform(delete("/api/classes/{id}", classe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(1)).deleteById(classe.getId());
    }

    @Test
    @Transactional
    public void searchClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);
        when(mockClasseSearchRepository.search(queryStringQuery("id:" + classe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classe), PageRequest.of(0, 1), 1));
        // Search the classe
        restClasseMockMvc.perform(get("/api/_search/classes?query=id:" + classe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipoEnsino").value(hasItem(DEFAULT_TIPO_ENSINO)));
    }
}
