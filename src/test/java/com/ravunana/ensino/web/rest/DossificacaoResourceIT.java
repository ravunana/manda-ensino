package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Dossificacao;
import com.ravunana.ensino.domain.Curso;
import com.ravunana.ensino.domain.Classe;
import com.ravunana.ensino.domain.Disciplina;
import com.ravunana.ensino.repository.DossificacaoRepository;
import com.ravunana.ensino.repository.search.DossificacaoSearchRepository;
import com.ravunana.ensino.service.DossificacaoService;
import com.ravunana.ensino.service.dto.DossificacaoDTO;
import com.ravunana.ensino.service.mapper.DossificacaoMapper;
import com.ravunana.ensino.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
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
 * Integration tests for the {@link DossificacaoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class DossificacaoResourceIT {

    private static final String DEFAULT_PERIODO_LECTIVO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO_LECTIVO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ANO_LECTIVO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_LECTIVO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBJECTIVO_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVO_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVO_ESPECIFICO = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVO_ESPECIFICO = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEMANA_LECTIVA = 1;
    private static final Integer UPDATED_SEMANA_LECTIVA = 2;

    private static final LocalDate DEFAULT_DE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDIMENTO_ENSINO = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDIMENTO_ENSINO = "BBBBBBBBBB";

    private static final String DEFAULT_RECURSOS_DIDATICO = "AAAAAAAAAA";
    private static final String UPDATED_RECURSOS_DIDATICO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TEMPO_AULA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TEMPO_AULA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FORMA_AVALIACAO = "AAAAAAAAAA";
    private static final String UPDATED_FORMA_AVALIACAO = "BBBBBBBBBB";

    @Autowired
    private DossificacaoRepository dossificacaoRepository;

    @Mock
    private DossificacaoRepository dossificacaoRepositoryMock;

    @Autowired
    private DossificacaoMapper dossificacaoMapper;

    @Mock
    private DossificacaoService dossificacaoServiceMock;

    @Autowired
    private DossificacaoService dossificacaoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.DossificacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DossificacaoSearchRepository mockDossificacaoSearchRepository;

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

    private MockMvc restDossificacaoMockMvc;

    private Dossificacao dossificacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DossificacaoResource dossificacaoResource = new DossificacaoResource(dossificacaoService);
        this.restDossificacaoMockMvc = MockMvcBuilders.standaloneSetup(dossificacaoResource)
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
    public static Dossificacao createEntity(EntityManager em) {
        Dossificacao dossificacao = new Dossificacao()
            .periodoLectivo(DEFAULT_PERIODO_LECTIVO)
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .objectivoGeral(DEFAULT_OBJECTIVO_GERAL)
            .objectivoEspecifico(DEFAULT_OBJECTIVO_ESPECIFICO)
            .semanaLectiva(DEFAULT_SEMANA_LECTIVA)
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .conteudo(DEFAULT_CONTEUDO)
            .procedimentoEnsino(DEFAULT_PROCEDIMENTO_ENSINO)
            .recursosDidatico(DEFAULT_RECURSOS_DIDATICO)
            .tempoAula(DEFAULT_TEMPO_AULA)
            .formaAvaliacao(DEFAULT_FORMA_AVALIACAO);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        dossificacao.getCursos().add(curso);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        dossificacao.getClasses().add(classe);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        dossificacao.setDisciplina(disciplina);
        return dossificacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossificacao createUpdatedEntity(EntityManager em) {
        Dossificacao dossificacao = new Dossificacao()
            .periodoLectivo(UPDATED_PERIODO_LECTIVO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivoEspecifico(UPDATED_OBJECTIVO_ESPECIFICO)
            .semanaLectiva(UPDATED_SEMANA_LECTIVA)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .conteudo(UPDATED_CONTEUDO)
            .procedimentoEnsino(UPDATED_PROCEDIMENTO_ENSINO)
            .recursosDidatico(UPDATED_RECURSOS_DIDATICO)
            .tempoAula(UPDATED_TEMPO_AULA)
            .formaAvaliacao(UPDATED_FORMA_AVALIACAO);
        // Add required entity
        Curso curso;
        if (TestUtil.findAll(em, Curso.class).isEmpty()) {
            curso = CursoResourceIT.createUpdatedEntity(em);
            em.persist(curso);
            em.flush();
        } else {
            curso = TestUtil.findAll(em, Curso.class).get(0);
        }
        dossificacao.getCursos().add(curso);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createUpdatedEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        dossificacao.getClasses().add(classe);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        dossificacao.setDisciplina(disciplina);
        return dossificacao;
    }

    @BeforeEach
    public void initTest() {
        dossificacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createDossificacao() throws Exception {
        int databaseSizeBeforeCreate = dossificacaoRepository.findAll().size();

        // Create the Dossificacao
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);
        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Dossificacao in the database
        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Dossificacao testDossificacao = dossificacaoList.get(dossificacaoList.size() - 1);
        assertThat(testDossificacao.getPeriodoLectivo()).isEqualTo(DEFAULT_PERIODO_LECTIVO);
        assertThat(testDossificacao.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testDossificacao.getObjectivoGeral()).isEqualTo(DEFAULT_OBJECTIVO_GERAL);
        assertThat(testDossificacao.getObjectivoEspecifico()).isEqualTo(DEFAULT_OBJECTIVO_ESPECIFICO);
        assertThat(testDossificacao.getSemanaLectiva()).isEqualTo(DEFAULT_SEMANA_LECTIVA);
        assertThat(testDossificacao.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testDossificacao.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testDossificacao.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testDossificacao.getProcedimentoEnsino()).isEqualTo(DEFAULT_PROCEDIMENTO_ENSINO);
        assertThat(testDossificacao.getRecursosDidatico()).isEqualTo(DEFAULT_RECURSOS_DIDATICO);
        assertThat(testDossificacao.getTempoAula()).isEqualTo(DEFAULT_TEMPO_AULA);
        assertThat(testDossificacao.getFormaAvaliacao()).isEqualTo(DEFAULT_FORMA_AVALIACAO);

        // Validate the Dossificacao in Elasticsearch
        verify(mockDossificacaoSearchRepository, times(1)).save(testDossificacao);
    }

    @Test
    @Transactional
    public void createDossificacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dossificacaoRepository.findAll().size();

        // Create the Dossificacao with an existing ID
        dossificacao.setId(1L);
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dossificacao in the database
        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dossificacao in Elasticsearch
        verify(mockDossificacaoSearchRepository, times(0)).save(dossificacao);
    }


    @Test
    @Transactional
    public void checkPeriodoLectivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setPeriodoLectivo(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoLectivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setAnoLectivo(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObjectivoGeralIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setObjectivoGeral(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemanaLectivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setSemanaLectiva(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setDe(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setAte(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempoAulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setTempoAula(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormaAvaliacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossificacaoRepository.findAll().size();
        // set the field null
        dossificacao.setFormaAvaliacao(null);

        // Create the Dossificacao, which fails.
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        restDossificacaoMockMvc.perform(post("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDossificacaos() throws Exception {
        // Initialize the database
        dossificacaoRepository.saveAndFlush(dossificacao);

        // Get all the dossificacaoList
        restDossificacaoMockMvc.perform(get("/api/dossificacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossificacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodoLectivo").value(hasItem(DEFAULT_PERIODO_LECTIVO)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].objectivoGeral").value(hasItem(DEFAULT_OBJECTIVO_GERAL)))
            .andExpect(jsonPath("$.[*].objectivoEspecifico").value(hasItem(DEFAULT_OBJECTIVO_ESPECIFICO.toString())))
            .andExpect(jsonPath("$.[*].semanaLectiva").value(hasItem(DEFAULT_SEMANA_LECTIVA)))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].procedimentoEnsino").value(hasItem(DEFAULT_PROCEDIMENTO_ENSINO.toString())))
            .andExpect(jsonPath("$.[*].recursosDidatico").value(hasItem(DEFAULT_RECURSOS_DIDATICO.toString())))
            .andExpect(jsonPath("$.[*].tempoAula").value(hasItem(sameInstant(DEFAULT_TEMPO_AULA))))
            .andExpect(jsonPath("$.[*].formaAvaliacao").value(hasItem(DEFAULT_FORMA_AVALIACAO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDossificacaosWithEagerRelationshipsIsEnabled() throws Exception {
        DossificacaoResource dossificacaoResource = new DossificacaoResource(dossificacaoServiceMock);
        when(dossificacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDossificacaoMockMvc = MockMvcBuilders.standaloneSetup(dossificacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDossificacaoMockMvc.perform(get("/api/dossificacaos?eagerload=true"))
        .andExpect(status().isOk());

        verify(dossificacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDossificacaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        DossificacaoResource dossificacaoResource = new DossificacaoResource(dossificacaoServiceMock);
            when(dossificacaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDossificacaoMockMvc = MockMvcBuilders.standaloneSetup(dossificacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDossificacaoMockMvc.perform(get("/api/dossificacaos?eagerload=true"))
        .andExpect(status().isOk());

            verify(dossificacaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDossificacao() throws Exception {
        // Initialize the database
        dossificacaoRepository.saveAndFlush(dossificacao);

        // Get the dossificacao
        restDossificacaoMockMvc.perform(get("/api/dossificacaos/{id}", dossificacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dossificacao.getId().intValue()))
            .andExpect(jsonPath("$.periodoLectivo").value(DEFAULT_PERIODO_LECTIVO))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO.toString()))
            .andExpect(jsonPath("$.objectivoGeral").value(DEFAULT_OBJECTIVO_GERAL))
            .andExpect(jsonPath("$.objectivoEspecifico").value(DEFAULT_OBJECTIVO_ESPECIFICO.toString()))
            .andExpect(jsonPath("$.semanaLectiva").value(DEFAULT_SEMANA_LECTIVA))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO.toString()))
            .andExpect(jsonPath("$.procedimentoEnsino").value(DEFAULT_PROCEDIMENTO_ENSINO.toString()))
            .andExpect(jsonPath("$.recursosDidatico").value(DEFAULT_RECURSOS_DIDATICO.toString()))
            .andExpect(jsonPath("$.tempoAula").value(sameInstant(DEFAULT_TEMPO_AULA)))
            .andExpect(jsonPath("$.formaAvaliacao").value(DEFAULT_FORMA_AVALIACAO));
    }

    @Test
    @Transactional
    public void getNonExistingDossificacao() throws Exception {
        // Get the dossificacao
        restDossificacaoMockMvc.perform(get("/api/dossificacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDossificacao() throws Exception {
        // Initialize the database
        dossificacaoRepository.saveAndFlush(dossificacao);

        int databaseSizeBeforeUpdate = dossificacaoRepository.findAll().size();

        // Update the dossificacao
        Dossificacao updatedDossificacao = dossificacaoRepository.findById(dossificacao.getId()).get();
        // Disconnect from session so that the updates on updatedDossificacao are not directly saved in db
        em.detach(updatedDossificacao);
        updatedDossificacao
            .periodoLectivo(UPDATED_PERIODO_LECTIVO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .objectivoGeral(UPDATED_OBJECTIVO_GERAL)
            .objectivoEspecifico(UPDATED_OBJECTIVO_ESPECIFICO)
            .semanaLectiva(UPDATED_SEMANA_LECTIVA)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .conteudo(UPDATED_CONTEUDO)
            .procedimentoEnsino(UPDATED_PROCEDIMENTO_ENSINO)
            .recursosDidatico(UPDATED_RECURSOS_DIDATICO)
            .tempoAula(UPDATED_TEMPO_AULA)
            .formaAvaliacao(UPDATED_FORMA_AVALIACAO);
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(updatedDossificacao);

        restDossificacaoMockMvc.perform(put("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Dossificacao in the database
        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeUpdate);
        Dossificacao testDossificacao = dossificacaoList.get(dossificacaoList.size() - 1);
        assertThat(testDossificacao.getPeriodoLectivo()).isEqualTo(UPDATED_PERIODO_LECTIVO);
        assertThat(testDossificacao.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testDossificacao.getObjectivoGeral()).isEqualTo(UPDATED_OBJECTIVO_GERAL);
        assertThat(testDossificacao.getObjectivoEspecifico()).isEqualTo(UPDATED_OBJECTIVO_ESPECIFICO);
        assertThat(testDossificacao.getSemanaLectiva()).isEqualTo(UPDATED_SEMANA_LECTIVA);
        assertThat(testDossificacao.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testDossificacao.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testDossificacao.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testDossificacao.getProcedimentoEnsino()).isEqualTo(UPDATED_PROCEDIMENTO_ENSINO);
        assertThat(testDossificacao.getRecursosDidatico()).isEqualTo(UPDATED_RECURSOS_DIDATICO);
        assertThat(testDossificacao.getTempoAula()).isEqualTo(UPDATED_TEMPO_AULA);
        assertThat(testDossificacao.getFormaAvaliacao()).isEqualTo(UPDATED_FORMA_AVALIACAO);

        // Validate the Dossificacao in Elasticsearch
        verify(mockDossificacaoSearchRepository, times(1)).save(testDossificacao);
    }

    @Test
    @Transactional
    public void updateNonExistingDossificacao() throws Exception {
        int databaseSizeBeforeUpdate = dossificacaoRepository.findAll().size();

        // Create the Dossificacao
        DossificacaoDTO dossificacaoDTO = dossificacaoMapper.toDto(dossificacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossificacaoMockMvc.perform(put("/api/dossificacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossificacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dossificacao in the database
        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dossificacao in Elasticsearch
        verify(mockDossificacaoSearchRepository, times(0)).save(dossificacao);
    }

    @Test
    @Transactional
    public void deleteDossificacao() throws Exception {
        // Initialize the database
        dossificacaoRepository.saveAndFlush(dossificacao);

        int databaseSizeBeforeDelete = dossificacaoRepository.findAll().size();

        // Delete the dossificacao
        restDossificacaoMockMvc.perform(delete("/api/dossificacaos/{id}", dossificacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dossificacao> dossificacaoList = dossificacaoRepository.findAll();
        assertThat(dossificacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dossificacao in Elasticsearch
        verify(mockDossificacaoSearchRepository, times(1)).deleteById(dossificacao.getId());
    }

    @Test
    @Transactional
    public void searchDossificacao() throws Exception {
        // Initialize the database
        dossificacaoRepository.saveAndFlush(dossificacao);
        when(mockDossificacaoSearchRepository.search(queryStringQuery("id:" + dossificacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dossificacao), PageRequest.of(0, 1), 1));
        // Search the dossificacao
        restDossificacaoMockMvc.perform(get("/api/_search/dossificacaos?query=id:" + dossificacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossificacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodoLectivo").value(hasItem(DEFAULT_PERIODO_LECTIVO)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO.toString())))
            .andExpect(jsonPath("$.[*].objectivoGeral").value(hasItem(DEFAULT_OBJECTIVO_GERAL)))
            .andExpect(jsonPath("$.[*].objectivoEspecifico").value(hasItem(DEFAULT_OBJECTIVO_ESPECIFICO.toString())))
            .andExpect(jsonPath("$.[*].semanaLectiva").value(hasItem(DEFAULT_SEMANA_LECTIVA)))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].procedimentoEnsino").value(hasItem(DEFAULT_PROCEDIMENTO_ENSINO.toString())))
            .andExpect(jsonPath("$.[*].recursosDidatico").value(hasItem(DEFAULT_RECURSOS_DIDATICO.toString())))
            .andExpect(jsonPath("$.[*].tempoAula").value(hasItem(sameInstant(DEFAULT_TEMPO_AULA))))
            .andExpect(jsonPath("$.[*].formaAvaliacao").value(hasItem(DEFAULT_FORMA_AVALIACAO)));
    }
}
