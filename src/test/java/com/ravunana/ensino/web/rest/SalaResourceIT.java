package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Sala;
import com.ravunana.ensino.repository.SalaRepository;
import com.ravunana.ensino.repository.search.SalaSearchRepository;
import com.ravunana.ensino.service.SalaService;
import com.ravunana.ensino.service.dto.SalaDTO;
import com.ravunana.ensino.service.mapper.SalaMapper;
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
 * Integration tests for the {@link SalaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class SalaResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOTACAO = 30;
    private static final Integer UPDATED_LOTACAO = 31;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaMapper salaMapper;

    @Autowired
    private SalaService salaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.SalaSearchRepositoryMockConfiguration
     */
    @Autowired
    private SalaSearchRepository mockSalaSearchRepository;

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

    private MockMvc restSalaMockMvc;

    private Sala sala;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalaResource salaResource = new SalaResource(salaService);
        this.restSalaMockMvc = MockMvcBuilders.standaloneSetup(salaResource)
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
    public static Sala createEntity(EntityManager em) {
        Sala sala = new Sala()
            .numero(DEFAULT_NUMERO)
            .descricao(DEFAULT_DESCRICAO)
            .lotacao(DEFAULT_LOTACAO);
        return sala;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sala createUpdatedEntity(EntityManager em) {
        Sala sala = new Sala()
            .numero(UPDATED_NUMERO)
            .descricao(UPDATED_DESCRICAO)
            .lotacao(UPDATED_LOTACAO);
        return sala;
    }

    @BeforeEach
    public void initTest() {
        sala = createEntity(em);
    }

    @Test
    @Transactional
    public void createSala() throws Exception {
        int databaseSizeBeforeCreate = salaRepository.findAll().size();

        // Create the Sala
        SalaDTO salaDTO = salaMapper.toDto(sala);
        restSalaMockMvc.perform(post("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isCreated());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeCreate + 1);
        Sala testSala = salaList.get(salaList.size() - 1);
        assertThat(testSala.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testSala.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testSala.getLotacao()).isEqualTo(DEFAULT_LOTACAO);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).save(testSala);
    }

    @Test
    @Transactional
    public void createSalaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salaRepository.findAll().size();

        // Create the Sala with an existing ID
        sala.setId(1L);
        SalaDTO salaDTO = salaMapper.toDto(sala);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaMockMvc.perform(post("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(0)).save(sala);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setNumero(null);

        // Create the Sala, which fails.
        SalaDTO salaDTO = salaMapper.toDto(sala);

        restSalaMockMvc.perform(post("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLotacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setLotacao(null);

        // Create the Sala, which fails.
        SalaDTO salaDTO = salaMapper.toDto(sala);

        restSalaMockMvc.perform(post("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalas() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get all the salaList
        restSalaMockMvc.perform(get("/api/salas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].lotacao").value(hasItem(DEFAULT_LOTACAO)));
    }
    
    @Test
    @Transactional
    public void getSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", sala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sala.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.lotacao").value(DEFAULT_LOTACAO));
    }

    @Test
    @Transactional
    public void getNonExistingSala() throws Exception {
        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        int databaseSizeBeforeUpdate = salaRepository.findAll().size();

        // Update the sala
        Sala updatedSala = salaRepository.findById(sala.getId()).get();
        // Disconnect from session so that the updates on updatedSala are not directly saved in db
        em.detach(updatedSala);
        updatedSala
            .numero(UPDATED_NUMERO)
            .descricao(UPDATED_DESCRICAO)
            .lotacao(UPDATED_LOTACAO);
        SalaDTO salaDTO = salaMapper.toDto(updatedSala);

        restSalaMockMvc.perform(put("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isOk());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeUpdate);
        Sala testSala = salaList.get(salaList.size() - 1);
        assertThat(testSala.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testSala.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testSala.getLotacao()).isEqualTo(UPDATED_LOTACAO);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).save(testSala);
    }

    @Test
    @Transactional
    public void updateNonExistingSala() throws Exception {
        int databaseSizeBeforeUpdate = salaRepository.findAll().size();

        // Create the Sala
        SalaDTO salaDTO = salaMapper.toDto(sala);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaMockMvc.perform(put("/api/salas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(0)).save(sala);
    }

    @Test
    @Transactional
    public void deleteSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        int databaseSizeBeforeDelete = salaRepository.findAll().size();

        // Delete the sala
        restSalaMockMvc.perform(delete("/api/salas/{id}", sala.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).deleteById(sala.getId());
    }

    @Test
    @Transactional
    public void searchSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);
        when(mockSalaSearchRepository.search(queryStringQuery("id:" + sala.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sala), PageRequest.of(0, 1), 1));
        // Search the sala
        restSalaMockMvc.perform(get("/api/_search/salas?query=id:" + sala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].lotacao").value(hasItem(DEFAULT_LOTACAO)));
    }
}
