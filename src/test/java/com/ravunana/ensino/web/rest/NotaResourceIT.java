package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Nota;
import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.domain.Turma;
import com.ravunana.ensino.domain.Matricula;
import com.ravunana.ensino.repository.NotaRepository;
import com.ravunana.ensino.repository.search.NotaSearchRepository;
import com.ravunana.ensino.service.NotaService;
import com.ravunana.ensino.service.dto.NotaDTO;
import com.ravunana.ensino.service.mapper.NotaMapper;
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
 * Integration tests for the {@link NotaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class NotaResourceIT {

    private static final Double DEFAULT_VALOR = 0D;
    private static final Double UPDATED_VALOR = 1D;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_ANO_LECTIVO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_LECTIVO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PERIODO_LECTIVO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO_LECTIVO = "BBBBBBBBBB";

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private NotaMapper notaMapper;

    @Autowired
    private NotaService notaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.NotaSearchRepositoryMockConfiguration
     */
    @Autowired
    private NotaSearchRepository mockNotaSearchRepository;

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

    private MockMvc restNotaMockMvc;

    private Nota nota;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotaResource notaResource = new NotaResource(notaService);
        this.restNotaMockMvc = MockMvcBuilders.standaloneSetup(notaResource)
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
    public static Nota createEntity(EntityManager em) {
        Nota nota = new Nota()
            .valor(DEFAULT_VALOR)
            .data(DEFAULT_DATA)
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .periodoLectivo(DEFAULT_PERIODO_LECTIVO);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        nota.setDisciplina(disciplina);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        nota.setTurma(turma);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        nota.setMatricula(matricula);
        return nota;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nota createUpdatedEntity(EntityManager em) {
        Nota nota = new Nota()
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .periodoLectivo(UPDATED_PERIODO_LECTIVO);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        nota.setDisciplina(disciplina);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        nota.setTurma(turma);
        // Add required entity
        Matricula matricula;
        if (TestUtil.findAll(em, Matricula.class).isEmpty()) {
            matricula = MatriculaResourceIT.createUpdatedEntity(em);
            em.persist(matricula);
            em.flush();
        } else {
            matricula = TestUtil.findAll(em, Matricula.class).get(0);
        }
        nota.setMatricula(matricula);
        return nota;
    }

    @BeforeEach
    public void initTest() {
        nota = createEntity(em);
    }

    @Test
    @Transactional
    public void createNota() throws Exception {
        int databaseSizeBeforeCreate = notaRepository.findAll().size();

        // Create the Nota
        NotaDTO notaDTO = notaMapper.toDto(nota);
        restNotaMockMvc.perform(post("/api/notas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notaDTO)))
            .andExpect(status().isCreated());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeCreate + 1);
        Nota testNota = notaList.get(notaList.size() - 1);
        assertThat(testNota.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testNota.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testNota.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testNota.getPeriodoLectivo()).isEqualTo(DEFAULT_PERIODO_LECTIVO);

        // Validate the Nota in Elasticsearch
        verify(mockNotaSearchRepository, times(1)).save(testNota);
    }

    @Test
    @Transactional
    public void createNotaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notaRepository.findAll().size();

        // Create the Nota with an existing ID
        nota.setId(1L);
        NotaDTO notaDTO = notaMapper.toDto(nota);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotaMockMvc.perform(post("/api/notas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Nota in Elasticsearch
        verify(mockNotaSearchRepository, times(0)).save(nota);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaRepository.findAll().size();
        // set the field null
        nota.setValor(null);

        // Create the Nota, which fails.
        NotaDTO notaDTO = notaMapper.toDto(nota);

        restNotaMockMvc.perform(post("/api/notas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notaDTO)))
            .andExpect(status().isBadRequest());

        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotas() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        // Get all the notaList
        restNotaMockMvc.perform(get("/api/notas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nota.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].periodoLectivo").value(hasItem(DEFAULT_PERIODO_LECTIVO)));
    }
    
    @Test
    @Transactional
    public void getNota() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        // Get the nota
        restNotaMockMvc.perform(get("/api/notas/{id}", nota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nota.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO.toString()))
            .andExpect(jsonPath("$.periodoLectivo").value(DEFAULT_PERIODO_LECTIVO));
    }

    @Test
    @Transactional
    public void getNonExistingNota() throws Exception {
        // Get the nota
        restNotaMockMvc.perform(get("/api/notas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNota() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        int databaseSizeBeforeUpdate = notaRepository.findAll().size();

        // Update the nota
        Nota updatedNota = notaRepository.findById(nota.getId()).get();
        // Disconnect from session so that the updates on updatedNota are not directly saved in db
        em.detach(updatedNota);
        updatedNota
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .periodoLectivo(UPDATED_PERIODO_LECTIVO);
        NotaDTO notaDTO = notaMapper.toDto(updatedNota);

        restNotaMockMvc.perform(put("/api/notas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notaDTO)))
            .andExpect(status().isOk());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeUpdate);
        Nota testNota = notaList.get(notaList.size() - 1);
        assertThat(testNota.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testNota.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testNota.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testNota.getPeriodoLectivo()).isEqualTo(UPDATED_PERIODO_LECTIVO);

        // Validate the Nota in Elasticsearch
        verify(mockNotaSearchRepository, times(1)).save(testNota);
    }

    @Test
    @Transactional
    public void updateNonExistingNota() throws Exception {
        int databaseSizeBeforeUpdate = notaRepository.findAll().size();

        // Create the Nota
        NotaDTO notaDTO = notaMapper.toDto(nota);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaMockMvc.perform(put("/api/notas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nota in the database
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Nota in Elasticsearch
        verify(mockNotaSearchRepository, times(0)).save(nota);
    }

    @Test
    @Transactional
    public void deleteNota() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);

        int databaseSizeBeforeDelete = notaRepository.findAll().size();

        // Delete the nota
        restNotaMockMvc.perform(delete("/api/notas/{id}", nota.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nota> notaList = notaRepository.findAll();
        assertThat(notaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Nota in Elasticsearch
        verify(mockNotaSearchRepository, times(1)).deleteById(nota.getId());
    }

    @Test
    @Transactional
    public void searchNota() throws Exception {
        // Initialize the database
        notaRepository.saveAndFlush(nota);
        when(mockNotaSearchRepository.search(queryStringQuery("id:" + nota.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(nota), PageRequest.of(0, 1), 1));
        // Search the nota
        restNotaMockMvc.perform(get("/api/_search/notas?query=id:" + nota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nota.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].periodoLectivo").value(hasItem(DEFAULT_PERIODO_LECTIVO)));
    }
}
