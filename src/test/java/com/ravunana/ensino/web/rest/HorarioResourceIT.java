package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Horario;
import com.ravunana.ensino.domain.Turma;
import com.ravunana.ensino.domain.Professor;
import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.repository.HorarioRepository;
import com.ravunana.ensino.repository.search.HorarioSearchRepository;
import com.ravunana.ensino.service.HorarioService;
import com.ravunana.ensino.service.dto.HorarioDTO;
import com.ravunana.ensino.service.mapper.HorarioMapper;
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
 * Integration tests for the {@link HorarioResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class HorarioResourceIT {

    private static final ZonedDateTime DEFAULT_INICIO_AULA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INICIO_AULA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TERMINO_ALUA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TERMINO_ALUA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_INTERVALO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INTERVALO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DIA_SEMANA = "AAAAAAAAAA";
    private static final String UPDATED_DIA_SEMANA = "BBBBBBBBBB";

    private static final String DEFAULT_REGIME_CURRICULAR = "AAAAAAAAAA";
    private static final String UPDATED_REGIME_CURRICULAR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_ANO_LECTIVO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_LECTIVO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private HorarioMapper horarioMapper;

    @Autowired
    private HorarioService horarioService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.HorarioSearchRepositoryMockConfiguration
     */
    @Autowired
    private HorarioSearchRepository mockHorarioSearchRepository;

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

    private MockMvc restHorarioMockMvc;

    private Horario horario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HorarioResource horarioResource = new HorarioResource(horarioService);
        this.restHorarioMockMvc = MockMvcBuilders.standaloneSetup(horarioResource)
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
    public static Horario createEntity(EntityManager em) {
        Horario horario = new Horario()
            .inicioAula(DEFAULT_INICIO_AULA)
            .terminoAlua(DEFAULT_TERMINO_ALUA)
            .intervalo(DEFAULT_INTERVALO)
            .diaSemana(DEFAULT_DIA_SEMANA)
            .regimeCurricular(DEFAULT_REGIME_CURRICULAR)
            .data(DEFAULT_DATA)
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .categoria(DEFAULT_CATEGORIA);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        horario.setTurma(turma);
        // Add required entity
        Professor professor;
        if (TestUtil.findAll(em, Professor.class).isEmpty()) {
            professor = ProfessorResourceIT.createEntity(em);
            em.persist(professor);
            em.flush();
        } else {
            professor = TestUtil.findAll(em, Professor.class).get(0);
        }
        horario.setProfessor(professor);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        horario.setDisciplina(disciplina);
        return horario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horario createUpdatedEntity(EntityManager em) {
        Horario horario = new Horario()
            .inicioAula(UPDATED_INICIO_AULA)
            .terminoAlua(UPDATED_TERMINO_ALUA)
            .intervalo(UPDATED_INTERVALO)
            .diaSemana(UPDATED_DIA_SEMANA)
            .regimeCurricular(UPDATED_REGIME_CURRICULAR)
            .data(UPDATED_DATA)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .categoria(UPDATED_CATEGORIA);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        horario.setTurma(turma);
        // Add required entity
        Professor professor;
        if (TestUtil.findAll(em, Professor.class).isEmpty()) {
            professor = ProfessorResourceIT.createUpdatedEntity(em);
            em.persist(professor);
            em.flush();
        } else {
            professor = TestUtil.findAll(em, Professor.class).get(0);
        }
        horario.setProfessor(professor);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        horario.setDisciplina(disciplina);
        return horario;
    }

    @BeforeEach
    public void initTest() {
        horario = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorario() throws Exception {
        int databaseSizeBeforeCreate = horarioRepository.findAll().size();

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);
        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeCreate + 1);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getInicioAula()).isEqualTo(DEFAULT_INICIO_AULA);
        assertThat(testHorario.getTerminoAlua()).isEqualTo(DEFAULT_TERMINO_ALUA);
        assertThat(testHorario.getIntervalo()).isEqualTo(DEFAULT_INTERVALO);
        assertThat(testHorario.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
        assertThat(testHorario.getRegimeCurricular()).isEqualTo(DEFAULT_REGIME_CURRICULAR);
        assertThat(testHorario.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testHorario.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testHorario.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);

        // Validate the Horario in Elasticsearch
        verify(mockHorarioSearchRepository, times(1)).save(testHorario);
    }

    @Test
    @Transactional
    public void createHorarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioRepository.findAll().size();

        // Create the Horario with an existing ID
        horario.setId(1L);
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeCreate);

        // Validate the Horario in Elasticsearch
        verify(mockHorarioSearchRepository, times(0)).save(horario);
    }


    @Test
    @Transactional
    public void checkInicioAulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setInicioAula(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTerminoAluaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setTerminoAlua(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntervaloIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setIntervalo(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiaSemanaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setDiaSemana(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegimeCurricularIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setRegimeCurricular(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setData(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarios() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList
        restHorarioMockMvc.perform(get("/api/horarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horario.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicioAula").value(hasItem(sameInstant(DEFAULT_INICIO_AULA))))
            .andExpect(jsonPath("$.[*].terminoAlua").value(hasItem(sameInstant(DEFAULT_TERMINO_ALUA))))
            .andExpect(jsonPath("$.[*].intervalo").value(hasItem(sameInstant(DEFAULT_INTERVALO))))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA)))
            .andExpect(jsonPath("$.[*].regimeCurricular").value(hasItem(DEFAULT_REGIME_CURRICULAR)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));
    }
    
    @Test
    @Transactional
    public void getHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get the horario
        restHorarioMockMvc.perform(get("/api/horarios/{id}", horario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horario.getId().intValue()))
            .andExpect(jsonPath("$.inicioAula").value(sameInstant(DEFAULT_INICIO_AULA)))
            .andExpect(jsonPath("$.terminoAlua").value(sameInstant(DEFAULT_TERMINO_ALUA)))
            .andExpect(jsonPath("$.intervalo").value(sameInstant(DEFAULT_INTERVALO)))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA))
            .andExpect(jsonPath("$.regimeCurricular").value(DEFAULT_REGIME_CURRICULAR))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO.toString()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA));
    }

    @Test
    @Transactional
    public void getNonExistingHorario() throws Exception {
        // Get the horario
        restHorarioMockMvc.perform(get("/api/horarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();

        // Update the horario
        Horario updatedHorario = horarioRepository.findById(horario.getId()).get();
        // Disconnect from session so that the updates on updatedHorario are not directly saved in db
        em.detach(updatedHorario);
        updatedHorario
            .inicioAula(UPDATED_INICIO_AULA)
            .terminoAlua(UPDATED_TERMINO_ALUA)
            .intervalo(UPDATED_INTERVALO)
            .diaSemana(UPDATED_DIA_SEMANA)
            .regimeCurricular(UPDATED_REGIME_CURRICULAR)
            .data(UPDATED_DATA)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .categoria(UPDATED_CATEGORIA);
        HorarioDTO horarioDTO = horarioMapper.toDto(updatedHorario);

        restHorarioMockMvc.perform(put("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isOk());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getInicioAula()).isEqualTo(UPDATED_INICIO_AULA);
        assertThat(testHorario.getTerminoAlua()).isEqualTo(UPDATED_TERMINO_ALUA);
        assertThat(testHorario.getIntervalo()).isEqualTo(UPDATED_INTERVALO);
        assertThat(testHorario.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
        assertThat(testHorario.getRegimeCurricular()).isEqualTo(UPDATED_REGIME_CURRICULAR);
        assertThat(testHorario.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testHorario.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testHorario.getCategoria()).isEqualTo(UPDATED_CATEGORIA);

        // Validate the Horario in Elasticsearch
        verify(mockHorarioSearchRepository, times(1)).save(testHorario);
    }

    @Test
    @Transactional
    public void updateNonExistingHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.toDto(horario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioMockMvc.perform(put("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Horario in Elasticsearch
        verify(mockHorarioSearchRepository, times(0)).save(horario);
    }

    @Test
    @Transactional
    public void deleteHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        int databaseSizeBeforeDelete = horarioRepository.findAll().size();

        // Delete the horario
        restHorarioMockMvc.perform(delete("/api/horarios/{id}", horario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Horario in Elasticsearch
        verify(mockHorarioSearchRepository, times(1)).deleteById(horario.getId());
    }

    @Test
    @Transactional
    public void searchHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);
        when(mockHorarioSearchRepository.search(queryStringQuery("id:" + horario.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(horario), PageRequest.of(0, 1), 1));
        // Search the horario
        restHorarioMockMvc.perform(get("/api/_search/horarios?query=id:" + horario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horario.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicioAula").value(hasItem(sameInstant(DEFAULT_INICIO_AULA))))
            .andExpect(jsonPath("$.[*].terminoAlua").value(hasItem(sameInstant(DEFAULT_TERMINO_ALUA))))
            .andExpect(jsonPath("$.[*].intervalo").value(hasItem(sameInstant(DEFAULT_INTERVALO))))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA)))
            .andExpect(jsonPath("$.[*].regimeCurricular").value(hasItem(DEFAULT_REGIME_CURRICULAR)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));
    }
}
