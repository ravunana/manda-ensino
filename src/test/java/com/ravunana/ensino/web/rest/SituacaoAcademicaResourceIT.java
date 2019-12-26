package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.SituacaoAcademica;
import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.repository.SituacaoAcademicaRepository;
import com.ravunana.ensino.repository.search.SituacaoAcademicaSearchRepository;
import com.ravunana.ensino.service.SituacaoAcademicaService;
import com.ravunana.ensino.service.dto.SituacaoAcademicaDTO;
import com.ravunana.ensino.service.mapper.SituacaoAcademicaMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.ravunana.ensino.web.rest.TestUtil.sameInstant;
import static com.ravunana.ensino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SituacaoAcademicaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class SituacaoAcademicaResourceIT {

    private static final Integer DEFAULT_ANO_LECTIVO = 1;
    private static final Integer UPDATED_ANO_LECTIVO = 2;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private SituacaoAcademicaRepository situacaoAcademicaRepository;

    @Autowired
    private SituacaoAcademicaMapper situacaoAcademicaMapper;

    @Autowired
    private SituacaoAcademicaService situacaoAcademicaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.SituacaoAcademicaSearchRepositoryMockConfiguration
     */
    @Autowired
    private SituacaoAcademicaSearchRepository mockSituacaoAcademicaSearchRepository;

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

    private MockMvc restSituacaoAcademicaMockMvc;

    private SituacaoAcademica situacaoAcademica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SituacaoAcademicaResource situacaoAcademicaResource = new SituacaoAcademicaResource(situacaoAcademicaService);
        this.restSituacaoAcademicaMockMvc = MockMvcBuilders.standaloneSetup(situacaoAcademicaResource)
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
    public static SituacaoAcademica createEntity(EntityManager em) {
        SituacaoAcademica situacaoAcademica = new SituacaoAcademica()
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .data(DEFAULT_DATA)
            .estado(DEFAULT_ESTADO)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        situacaoAcademica.setDisciplina(disciplina);
        return situacaoAcademica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituacaoAcademica createUpdatedEntity(EntityManager em) {
        SituacaoAcademica situacaoAcademica = new SituacaoAcademica()
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .data(UPDATED_DATA)
            .estado(UPDATED_ESTADO)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        situacaoAcademica.setDisciplina(disciplina);
        return situacaoAcademica;
    }

    @BeforeEach
    public void initTest() {
        situacaoAcademica = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituacaoAcademica() throws Exception {
        int databaseSizeBeforeCreate = situacaoAcademicaRepository.findAll().size();

        // Create the SituacaoAcademica
        SituacaoAcademicaDTO situacaoAcademicaDTO = situacaoAcademicaMapper.toDto(situacaoAcademica);
        restSituacaoAcademicaMockMvc.perform(post("/api/situacao-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoAcademicaDTO)))
            .andExpect(status().isCreated());

        // Validate the SituacaoAcademica in the database
        List<SituacaoAcademica> situacaoAcademicaList = situacaoAcademicaRepository.findAll();
        assertThat(situacaoAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        SituacaoAcademica testSituacaoAcademica = situacaoAcademicaList.get(situacaoAcademicaList.size() - 1);
        assertThat(testSituacaoAcademica.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testSituacaoAcademica.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testSituacaoAcademica.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testSituacaoAcademica.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the SituacaoAcademica in Elasticsearch
        verify(mockSituacaoAcademicaSearchRepository, times(1)).save(testSituacaoAcademica);
    }

    @Test
    @Transactional
    public void createSituacaoAcademicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situacaoAcademicaRepository.findAll().size();

        // Create the SituacaoAcademica with an existing ID
        situacaoAcademica.setId(1L);
        SituacaoAcademicaDTO situacaoAcademicaDTO = situacaoAcademicaMapper.toDto(situacaoAcademica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituacaoAcademicaMockMvc.perform(post("/api/situacao-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoAcademicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SituacaoAcademica in the database
        List<SituacaoAcademica> situacaoAcademicaList = situacaoAcademicaRepository.findAll();
        assertThat(situacaoAcademicaList).hasSize(databaseSizeBeforeCreate);

        // Validate the SituacaoAcademica in Elasticsearch
        verify(mockSituacaoAcademicaSearchRepository, times(0)).save(situacaoAcademica);
    }


    @Test
    @Transactional
    public void getAllSituacaoAcademicas() throws Exception {
        // Initialize the database
        situacaoAcademicaRepository.saveAndFlush(situacaoAcademica);

        // Get all the situacaoAcademicaList
        restSituacaoAcademicaMockMvc.perform(get("/api/situacao-academicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getSituacaoAcademica() throws Exception {
        // Initialize the database
        situacaoAcademicaRepository.saveAndFlush(situacaoAcademica);

        // Get the situacaoAcademica
        restSituacaoAcademicaMockMvc.perform(get("/api/situacao-academicas/{id}", situacaoAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(situacaoAcademica.getId().intValue()))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSituacaoAcademica() throws Exception {
        // Get the situacaoAcademica
        restSituacaoAcademicaMockMvc.perform(get("/api/situacao-academicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituacaoAcademica() throws Exception {
        // Initialize the database
        situacaoAcademicaRepository.saveAndFlush(situacaoAcademica);

        int databaseSizeBeforeUpdate = situacaoAcademicaRepository.findAll().size();

        // Update the situacaoAcademica
        SituacaoAcademica updatedSituacaoAcademica = situacaoAcademicaRepository.findById(situacaoAcademica.getId()).get();
        // Disconnect from session so that the updates on updatedSituacaoAcademica are not directly saved in db
        em.detach(updatedSituacaoAcademica);
        updatedSituacaoAcademica
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .data(UPDATED_DATA)
            .estado(UPDATED_ESTADO)
            .descricao(UPDATED_DESCRICAO);
        SituacaoAcademicaDTO situacaoAcademicaDTO = situacaoAcademicaMapper.toDto(updatedSituacaoAcademica);

        restSituacaoAcademicaMockMvc.perform(put("/api/situacao-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoAcademicaDTO)))
            .andExpect(status().isOk());

        // Validate the SituacaoAcademica in the database
        List<SituacaoAcademica> situacaoAcademicaList = situacaoAcademicaRepository.findAll();
        assertThat(situacaoAcademicaList).hasSize(databaseSizeBeforeUpdate);
        SituacaoAcademica testSituacaoAcademica = situacaoAcademicaList.get(situacaoAcademicaList.size() - 1);
        assertThat(testSituacaoAcademica.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testSituacaoAcademica.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testSituacaoAcademica.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testSituacaoAcademica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the SituacaoAcademica in Elasticsearch
        verify(mockSituacaoAcademicaSearchRepository, times(1)).save(testSituacaoAcademica);
    }

    @Test
    @Transactional
    public void updateNonExistingSituacaoAcademica() throws Exception {
        int databaseSizeBeforeUpdate = situacaoAcademicaRepository.findAll().size();

        // Create the SituacaoAcademica
        SituacaoAcademicaDTO situacaoAcademicaDTO = situacaoAcademicaMapper.toDto(situacaoAcademica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituacaoAcademicaMockMvc.perform(put("/api/situacao-academicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoAcademicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SituacaoAcademica in the database
        List<SituacaoAcademica> situacaoAcademicaList = situacaoAcademicaRepository.findAll();
        assertThat(situacaoAcademicaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SituacaoAcademica in Elasticsearch
        verify(mockSituacaoAcademicaSearchRepository, times(0)).save(situacaoAcademica);
    }

    @Test
    @Transactional
    public void deleteSituacaoAcademica() throws Exception {
        // Initialize the database
        situacaoAcademicaRepository.saveAndFlush(situacaoAcademica);

        int databaseSizeBeforeDelete = situacaoAcademicaRepository.findAll().size();

        // Delete the situacaoAcademica
        restSituacaoAcademicaMockMvc.perform(delete("/api/situacao-academicas/{id}", situacaoAcademica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SituacaoAcademica> situacaoAcademicaList = situacaoAcademicaRepository.findAll();
        assertThat(situacaoAcademicaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SituacaoAcademica in Elasticsearch
        verify(mockSituacaoAcademicaSearchRepository, times(1)).deleteById(situacaoAcademica.getId());
    }

    @Test
    @Transactional
    public void searchSituacaoAcademica() throws Exception {
        // Initialize the database
        situacaoAcademicaRepository.saveAndFlush(situacaoAcademica);
        when(mockSituacaoAcademicaSearchRepository.search(queryStringQuery("id:" + situacaoAcademica.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(situacaoAcademica), PageRequest.of(0, 1), 1));
        // Search the situacaoAcademica
        restSituacaoAcademicaMockMvc.perform(get("/api/_search/situacao-academicas?query=id:" + situacaoAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
}
