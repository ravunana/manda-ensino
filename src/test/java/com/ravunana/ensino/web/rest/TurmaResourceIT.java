package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Turma;
import com.ravunana.ensino.domain.Sala;
import com.ravunana.ensino.domain.Classe;
import com.ravunana.ensino.domain.Curso;
import com.ravunana.ensino.repository.TurmaRepository;
import com.ravunana.ensino.repository.search.TurmaSearchRepository;
import com.ravunana.ensino.service.TurmaService;
import com.ravunana.ensino.service.dto.TurmaDTO;
import com.ravunana.ensino.service.mapper.TurmaMapper;
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
import java.time.LocalDate;
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
 * Integration tests for the {@link TurmaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class TurmaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ANO_LECTIVO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_LECTIVO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REGIME = "AAAAAAAAAA";
    private static final String UPDATED_REGIME = "BBBBBBBBBB";

    private static final String DEFAULT_TURNO = "AAAAAAAAAA";
    private static final String UPDATED_TURNO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaMapper turmaMapper;

    @Autowired
    private TurmaService turmaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.TurmaSearchRepositoryMockConfiguration
     */
    @Autowired
    private TurmaSearchRepository mockTurmaSearchRepository;

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

    private MockMvc restTurmaMockMvc;

    private Turma turma;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TurmaResource turmaResource = new TurmaResource(turmaService);
        this.restTurmaMockMvc = MockMvcBuilders.standaloneSetup(turmaResource)
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
    public static Turma createEntity(EntityManager em) {
        Turma turma = new Turma()
            .descricao(DEFAULT_DESCRICAO)
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .regime(DEFAULT_REGIME)
            .turno(DEFAULT_TURNO)
            .data(DEFAULT_DATA)
            .ativo(DEFAULT_ATIVO);
        // Add required entity
        Sala sala;
        if (TestUtil.findAll(em, Sala.class).isEmpty()) {
            sala = SalaResourceIT.createEntity(em);
            em.persist(sala);
            em.flush();
        } else {
            sala = TestUtil.findAll(em, Sala.class).get(0);
        }
        turma.setSala(sala);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        turma.setClasse(classe);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        turma.setCurso(curso);
        return turma;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turma createUpdatedEntity(EntityManager em) {
        Turma turma = new Turma()
            .descricao(UPDATED_DESCRICAO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .regime(UPDATED_REGIME)
            .turno(UPDATED_TURNO)
            .data(UPDATED_DATA)
            .ativo(UPDATED_ATIVO);
        // Add required entity
        Sala sala;
        if (TestUtil.findAll(em, Sala.class).isEmpty()) {
            sala = SalaResourceIT.createUpdatedEntity(em);
            em.persist(sala);
            em.flush();
        } else {
            sala = TestUtil.findAll(em, Sala.class).get(0);
        }
        turma.setSala(sala);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createUpdatedEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        turma.setClasse(classe);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createUpdatedEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        turma.setCurso(curso);
        return turma;
    }

    @BeforeEach
    public void initTest() {
        turma = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurma() throws Exception {
        int databaseSizeBeforeCreate = turmaRepository.findAll().size();

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);
        restTurmaMockMvc.perform(post("/api/turmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isCreated());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeCreate + 1);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTurma.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testTurma.getRegime()).isEqualTo(DEFAULT_REGIME);
        assertThat(testTurma.getTurno()).isEqualTo(DEFAULT_TURNO);
        assertThat(testTurma.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTurma.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the Turma in Elasticsearch
        verify(mockTurmaSearchRepository, times(1)).save(testTurma);
    }

    @Test
    @Transactional
    public void createTurmaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = turmaRepository.findAll().size();

        // Create the Turma with an existing ID
        turma.setId(1L);
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurmaMockMvc.perform(post("/api/turmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Turma in Elasticsearch
        verify(mockTurmaSearchRepository, times(0)).save(turma);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setDescricao(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc.perform(post("/api/turmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setRegime(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc.perform(post("/api/turmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTurnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setTurno(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc.perform(post("/api/turmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTurmas() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList
        restTurmaMockMvc.perform(get("/api/turmas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turma.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].regime").value(hasItem(DEFAULT_REGIME)))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get the turma
        restTurmaMockMvc.perform(get("/api/turmas/{id}", turma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(turma.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO.toString()))
            .andExpect(jsonPath("$.regime").value(DEFAULT_REGIME))
            .andExpect(jsonPath("$.turno").value(DEFAULT_TURNO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTurma() throws Exception {
        // Get the turma
        restTurmaMockMvc.perform(get("/api/turmas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma
        Turma updatedTurma = turmaRepository.findById(turma.getId()).get();
        // Disconnect from session so that the updates on updatedTurma are not directly saved in db
        em.detach(updatedTurma);
        updatedTurma
            .descricao(UPDATED_DESCRICAO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .regime(UPDATED_REGIME)
            .turno(UPDATED_TURNO)
            .data(UPDATED_DATA)
            .ativo(UPDATED_ATIVO);
        TurmaDTO turmaDTO = turmaMapper.toDto(updatedTurma);

        restTurmaMockMvc.perform(put("/api/turmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTurma.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testTurma.getRegime()).isEqualTo(UPDATED_REGIME);
        assertThat(testTurma.getTurno()).isEqualTo(UPDATED_TURNO);
        assertThat(testTurma.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTurma.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the Turma in Elasticsearch
        verify(mockTurmaSearchRepository, times(1)).save(testTurma);
    }

    @Test
    @Transactional
    public void updateNonExistingTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurmaMockMvc.perform(put("/api/turmas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Turma in Elasticsearch
        verify(mockTurmaSearchRepository, times(0)).save(turma);
    }

    @Test
    @Transactional
    public void deleteTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeDelete = turmaRepository.findAll().size();

        // Delete the turma
        restTurmaMockMvc.perform(delete("/api/turmas/{id}", turma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Turma in Elasticsearch
        verify(mockTurmaSearchRepository, times(1)).deleteById(turma.getId());
    }

    @Test
    @Transactional
    public void searchTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);
        when(mockTurmaSearchRepository.search(queryStringQuery("id:" + turma.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(turma), PageRequest.of(0, 1), 1));
        // Search the turma
        restTurmaMockMvc.perform(get("/api/_search/turmas?query=id:" + turma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turma.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].regime").value(hasItem(DEFAULT_REGIME)))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
}
