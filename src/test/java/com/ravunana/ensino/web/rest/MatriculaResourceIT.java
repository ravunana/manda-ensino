package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Matricula;
import com.ravunana.ensino.domain.Aluno;
import com.ravunana.ensino.domain.CategoriaAluno;
import com.ravunana.ensino.domain.Turma;
import com.ravunana.ensino.repository.MatriculaRepository;
import com.ravunana.ensino.repository.search.MatriculaSearchRepository;
import com.ravunana.ensino.service.MatriculaService;
import com.ravunana.ensino.service.dto.MatriculaDTO;
import com.ravunana.ensino.service.mapper.MatriculaMapper;
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
 * Integration tests for the {@link MatriculaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class MatriculaResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO_LECTIVO = 1;
    private static final Integer UPDATED_ANO_LECTIVO = 2;

    private static final String DEFAULT_PERIDO_LECTIVO = "AAAAAAAAAA";
    private static final String UPDATED_PERIDO_LECTIVO = "BBBBBBBBBB";

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private MatriculaMapper matriculaMapper;

    @Autowired
    private MatriculaService matriculaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.MatriculaSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatriculaSearchRepository mockMatriculaSearchRepository;

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

    private MockMvc restMatriculaMockMvc;

    private Matricula matricula;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatriculaResource matriculaResource = new MatriculaResource(matriculaService);
        this.restMatriculaMockMvc = MockMvcBuilders.standaloneSetup(matriculaResource)
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
    public static Matricula createEntity(EntityManager em) {
        Matricula matricula = new Matricula()
            .data(DEFAULT_DATA)
            .numero(DEFAULT_NUMERO)
            .observacao(DEFAULT_OBSERVACAO)
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .peridoLectivo(DEFAULT_PERIDO_LECTIVO);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        matricula.setAluno(aluno);
        // Add required entity
        CategoriaAluno categoriaAluno;
        if (TestUtil.findAll(em, CategoriaAluno.class).isEmpty()) {
            categoriaAluno = CategoriaAlunoResourceIT.createEntity(em);
            em.persist(categoriaAluno);
            em.flush();
        } else {
            categoriaAluno = TestUtil.findAll(em, CategoriaAluno.class).get(0);
        }
        matricula.setCategoria(categoriaAluno);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        matricula.setTurma(turma);
        return matricula;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matricula createUpdatedEntity(EntityManager em) {
        Matricula matricula = new Matricula()
            .data(UPDATED_DATA)
            .numero(UPDATED_NUMERO)
            .observacao(UPDATED_OBSERVACAO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .peridoLectivo(UPDATED_PERIDO_LECTIVO);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createUpdatedEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        matricula.setAluno(aluno);
        // Add required entity
        CategoriaAluno categoriaAluno;
        if (TestUtil.findAll(em, CategoriaAluno.class).isEmpty()) {
            categoriaAluno = CategoriaAlunoResourceIT.createUpdatedEntity(em);
            em.persist(categoriaAluno);
            em.flush();
        } else {
            categoriaAluno = TestUtil.findAll(em, CategoriaAluno.class).get(0);
        }
        matricula.setCategoria(categoriaAluno);
        // Add required entity
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            turma = TurmaResourceIT.createUpdatedEntity(em);
            em.persist(turma);
            em.flush();
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        matricula.setTurma(turma);
        return matricula;
    }

    @BeforeEach
    public void initTest() {
        matricula = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatricula() throws Exception {
        int databaseSizeBeforeCreate = matriculaRepository.findAll().size();

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);
        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isCreated());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeCreate + 1);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testMatricula.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testMatricula.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testMatricula.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testMatricula.getPeridoLectivo()).isEqualTo(DEFAULT_PERIDO_LECTIVO);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(1)).save(testMatricula);
    }

    @Test
    @Transactional
    public void createMatriculaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matriculaRepository.findAll().size();

        // Create the Matricula with an existing ID
        matricula.setId(1L);
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(0)).save(matricula);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setNumero(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoLectivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setAnoLectivo(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeridoLectivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriculaRepository.findAll().size();
        // set the field null
        matricula.setPeridoLectivo(null);

        // Create the Matricula, which fails.
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        restMatriculaMockMvc.perform(post("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatriculas() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get all the matriculaList
        restMatriculaMockMvc.perform(get("/api/matriculas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].peridoLectivo").value(hasItem(DEFAULT_PERIDO_LECTIVO)));
    }
    
    @Test
    @Transactional
    public void getMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        // Get the matricula
        restMatriculaMockMvc.perform(get("/api/matriculas/{id}", matricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matricula.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO))
            .andExpect(jsonPath("$.peridoLectivo").value(DEFAULT_PERIDO_LECTIVO));
    }

    @Test
    @Transactional
    public void getNonExistingMatricula() throws Exception {
        // Get the matricula
        restMatriculaMockMvc.perform(get("/api/matriculas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();

        // Update the matricula
        Matricula updatedMatricula = matriculaRepository.findById(matricula.getId()).get();
        // Disconnect from session so that the updates on updatedMatricula are not directly saved in db
        em.detach(updatedMatricula);
        updatedMatricula
            .data(UPDATED_DATA)
            .numero(UPDATED_NUMERO)
            .observacao(UPDATED_OBSERVACAO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .peridoLectivo(UPDATED_PERIDO_LECTIVO);
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(updatedMatricula);

        restMatriculaMockMvc.perform(put("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isOk());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);
        Matricula testMatricula = matriculaList.get(matriculaList.size() - 1);
        assertThat(testMatricula.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMatricula.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testMatricula.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testMatricula.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testMatricula.getPeridoLectivo()).isEqualTo(UPDATED_PERIDO_LECTIVO);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(1)).save(testMatricula);
    }

    @Test
    @Transactional
    public void updateNonExistingMatricula() throws Exception {
        int databaseSizeBeforeUpdate = matriculaRepository.findAll().size();

        // Create the Matricula
        MatriculaDTO matriculaDTO = matriculaMapper.toDto(matricula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatriculaMockMvc.perform(put("/api/matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matriculaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matricula in the database
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(0)).save(matricula);
    }

    @Test
    @Transactional
    public void deleteMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);

        int databaseSizeBeforeDelete = matriculaRepository.findAll().size();

        // Delete the matricula
        restMatriculaMockMvc.perform(delete("/api/matriculas/{id}", matricula.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matricula> matriculaList = matriculaRepository.findAll();
        assertThat(matriculaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Matricula in Elasticsearch
        verify(mockMatriculaSearchRepository, times(1)).deleteById(matricula.getId());
    }

    @Test
    @Transactional
    public void searchMatricula() throws Exception {
        // Initialize the database
        matriculaRepository.saveAndFlush(matricula);
        when(mockMatriculaSearchRepository.search(queryStringQuery("id:" + matricula.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matricula), PageRequest.of(0, 1), 1));
        // Search the matricula
        restMatriculaMockMvc.perform(get("/api/_search/matriculas?query=id:" + matricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].peridoLectivo").value(hasItem(DEFAULT_PERIDO_LECTIVO)));
    }
}
