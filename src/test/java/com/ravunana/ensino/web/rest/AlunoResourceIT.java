package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Aluno;
import com.ravunana.ensino.domain.Pessoa;
import com.ravunana.ensino.repository.AlunoRepository;
import com.ravunana.ensino.repository.search.AlunoSearchRepository;
import com.ravunana.ensino.service.AlunoService;
import com.ravunana.ensino.service.dto.AlunoDTO;
import com.ravunana.ensino.service.mapper.AlunoMapper;
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
 * Integration tests for the {@link AlunoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class AlunoResourceIT {

    private static final String DEFAULT_NUMERO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TRANSFERIDO = false;
    private static final Boolean UPDATED_TRANSFERIDO = true;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TURMA_ANTERIOR = "AAAAAAAAAA";
    private static final String UPDATED_TURMA_ANTERIOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO_CONCLUSAO = 1;
    private static final Integer UPDATED_ANO_CONCLUSAO = 2;

    private static final String DEFAULT_CURSO_FREQUENTADO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO_FREQUENTADO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_ESCOLA_ANTERIROR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ESCOLA_ANTERIROR = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO_ESCOLA_ANTERIOR = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_ESCOLA_ANTERIOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLASSE_CONCLUIDA = 1;
    private static final Integer UPDATED_CLASSE_CONCLUIDA = 2;

    private static final String DEFAULT_NUMERO_PROCESSO_ANTERIOR = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_ANTERIOR = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACAO_ANTERIOR = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO_ANTERIOR = "BBBBBBBBBB";

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoService alunoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.AlunoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlunoSearchRepository mockAlunoSearchRepository;

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

    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlunoResource alunoResource = new AlunoResource(alunoService);
        this.restAlunoMockMvc = MockMvcBuilders.standaloneSetup(alunoResource)
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
    public static Aluno createEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .numeroProcesso(DEFAULT_NUMERO_PROCESSO)
            .transferido(DEFAULT_TRANSFERIDO)
            .data(DEFAULT_DATA)
            .turmaAnterior(DEFAULT_TURMA_ANTERIOR)
            .anoConclusao(DEFAULT_ANO_CONCLUSAO)
            .cursoFrequentado(DEFAULT_CURSO_FREQUENTADO)
            .nomeEscolaAnteriror(DEFAULT_NOME_ESCOLA_ANTERIROR)
            .enderecoEscolaAnterior(DEFAULT_ENDERECO_ESCOLA_ANTERIOR)
            .classeConcluida(DEFAULT_CLASSE_CONCLUIDA)
            .numeroProcessoAnterior(DEFAULT_NUMERO_PROCESSO_ANTERIOR)
            .situacaoAnterior(DEFAULT_SITUACAO_ANTERIOR);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        aluno.setPessoa(pessoa);
        return aluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createUpdatedEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .transferido(UPDATED_TRANSFERIDO)
            .data(UPDATED_DATA)
            .turmaAnterior(UPDATED_TURMA_ANTERIOR)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .cursoFrequentado(UPDATED_CURSO_FREQUENTADO)
            .nomeEscolaAnteriror(UPDATED_NOME_ESCOLA_ANTERIROR)
            .enderecoEscolaAnterior(UPDATED_ENDERECO_ESCOLA_ANTERIOR)
            .classeConcluida(UPDATED_CLASSE_CONCLUIDA)
            .numeroProcessoAnterior(UPDATED_NUMERO_PROCESSO_ANTERIOR)
            .situacaoAnterior(UPDATED_SITUACAO_ANTERIOR);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        aluno.setPessoa(pessoa);
        return aluno;
    }

    @BeforeEach
    public void initTest() {
        aluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAluno() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isCreated());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate + 1);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNumeroProcesso()).isEqualTo(DEFAULT_NUMERO_PROCESSO);
        assertThat(testAluno.isTransferido()).isEqualTo(DEFAULT_TRANSFERIDO);
        assertThat(testAluno.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAluno.getTurmaAnterior()).isEqualTo(DEFAULT_TURMA_ANTERIOR);
        assertThat(testAluno.getAnoConclusao()).isEqualTo(DEFAULT_ANO_CONCLUSAO);
        assertThat(testAluno.getCursoFrequentado()).isEqualTo(DEFAULT_CURSO_FREQUENTADO);
        assertThat(testAluno.getNomeEscolaAnteriror()).isEqualTo(DEFAULT_NOME_ESCOLA_ANTERIROR);
        assertThat(testAluno.getEnderecoEscolaAnterior()).isEqualTo(DEFAULT_ENDERECO_ESCOLA_ANTERIOR);
        assertThat(testAluno.getClasseConcluida()).isEqualTo(DEFAULT_CLASSE_CONCLUIDA);
        assertThat(testAluno.getNumeroProcessoAnterior()).isEqualTo(DEFAULT_NUMERO_PROCESSO_ANTERIOR);
        assertThat(testAluno.getSituacaoAnterior()).isEqualTo(DEFAULT_SITUACAO_ANTERIOR);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).save(testAluno);
    }

    @Test
    @Transactional
    public void createAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno with an existing ID
        aluno.setId(1L);
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(0)).save(aluno);
    }


    @Test
    @Transactional
    public void checkNumeroProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setNumeroProcesso(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransferidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alunoRepository.findAll().size();
        // set the field null
        aluno.setTransferido(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlunos() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].transferido").value(hasItem(DEFAULT_TRANSFERIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].turmaAnterior").value(hasItem(DEFAULT_TURMA_ANTERIOR)))
            .andExpect(jsonPath("$.[*].anoConclusao").value(hasItem(DEFAULT_ANO_CONCLUSAO)))
            .andExpect(jsonPath("$.[*].cursoFrequentado").value(hasItem(DEFAULT_CURSO_FREQUENTADO)))
            .andExpect(jsonPath("$.[*].nomeEscolaAnteriror").value(hasItem(DEFAULT_NOME_ESCOLA_ANTERIROR)))
            .andExpect(jsonPath("$.[*].enderecoEscolaAnterior").value(hasItem(DEFAULT_ENDERECO_ESCOLA_ANTERIOR)))
            .andExpect(jsonPath("$.[*].classeConcluida").value(hasItem(DEFAULT_CLASSE_CONCLUIDA)))
            .andExpect(jsonPath("$.[*].numeroProcessoAnterior").value(hasItem(DEFAULT_NUMERO_PROCESSO_ANTERIOR)))
            .andExpect(jsonPath("$.[*].situacaoAnterior").value(hasItem(DEFAULT_SITUACAO_ANTERIOR)));
    }
    
    @Test
    @Transactional
    public void getAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.numeroProcesso").value(DEFAULT_NUMERO_PROCESSO))
            .andExpect(jsonPath("$.transferido").value(DEFAULT_TRANSFERIDO.booleanValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.turmaAnterior").value(DEFAULT_TURMA_ANTERIOR))
            .andExpect(jsonPath("$.anoConclusao").value(DEFAULT_ANO_CONCLUSAO))
            .andExpect(jsonPath("$.cursoFrequentado").value(DEFAULT_CURSO_FREQUENTADO))
            .andExpect(jsonPath("$.nomeEscolaAnteriror").value(DEFAULT_NOME_ESCOLA_ANTERIROR))
            .andExpect(jsonPath("$.enderecoEscolaAnterior").value(DEFAULT_ENDERECO_ESCOLA_ANTERIOR))
            .andExpect(jsonPath("$.classeConcluida").value(DEFAULT_CLASSE_CONCLUIDA))
            .andExpect(jsonPath("$.numeroProcessoAnterior").value(DEFAULT_NUMERO_PROCESSO_ANTERIOR))
            .andExpect(jsonPath("$.situacaoAnterior").value(DEFAULT_SITUACAO_ANTERIOR));
    }

    @Test
    @Transactional
    public void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findById(aluno.getId()).get();
        // Disconnect from session so that the updates on updatedAluno are not directly saved in db
        em.detach(updatedAluno);
        updatedAluno
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .transferido(UPDATED_TRANSFERIDO)
            .data(UPDATED_DATA)
            .turmaAnterior(UPDATED_TURMA_ANTERIOR)
            .anoConclusao(UPDATED_ANO_CONCLUSAO)
            .cursoFrequentado(UPDATED_CURSO_FREQUENTADO)
            .nomeEscolaAnteriror(UPDATED_NOME_ESCOLA_ANTERIROR)
            .enderecoEscolaAnterior(UPDATED_ENDERECO_ESCOLA_ANTERIOR)
            .classeConcluida(UPDATED_CLASSE_CONCLUIDA)
            .numeroProcessoAnterior(UPDATED_NUMERO_PROCESSO_ANTERIOR)
            .situacaoAnterior(UPDATED_SITUACAO_ANTERIOR);
        AlunoDTO alunoDTO = alunoMapper.toDto(updatedAluno);

        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isOk());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testAluno.isTransferido()).isEqualTo(UPDATED_TRANSFERIDO);
        assertThat(testAluno.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAluno.getTurmaAnterior()).isEqualTo(UPDATED_TURMA_ANTERIOR);
        assertThat(testAluno.getAnoConclusao()).isEqualTo(UPDATED_ANO_CONCLUSAO);
        assertThat(testAluno.getCursoFrequentado()).isEqualTo(UPDATED_CURSO_FREQUENTADO);
        assertThat(testAluno.getNomeEscolaAnteriror()).isEqualTo(UPDATED_NOME_ESCOLA_ANTERIROR);
        assertThat(testAluno.getEnderecoEscolaAnterior()).isEqualTo(UPDATED_ENDERECO_ESCOLA_ANTERIOR);
        assertThat(testAluno.getClasseConcluida()).isEqualTo(UPDATED_CLASSE_CONCLUIDA);
        assertThat(testAluno.getNumeroProcessoAnterior()).isEqualTo(UPDATED_NUMERO_PROCESSO_ANTERIOR);
        assertThat(testAluno.getSituacaoAnterior()).isEqualTo(UPDATED_SITUACAO_ANTERIOR);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).save(testAluno);
    }

    @Test
    @Transactional
    public void updateNonExistingAluno() throws Exception {
        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(0)).save(aluno);
    }

    @Test
    @Transactional
    public void deleteAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeDelete = alunoRepository.findAll().size();

        // Delete the aluno
        restAlunoMockMvc.perform(delete("/api/alunos/{id}", aluno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Aluno in Elasticsearch
        verify(mockAlunoSearchRepository, times(1)).deleteById(aluno.getId());
    }

    @Test
    @Transactional
    public void searchAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);
        when(mockAlunoSearchRepository.search(queryStringQuery("id:" + aluno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(aluno), PageRequest.of(0, 1), 1));
        // Search the aluno
        restAlunoMockMvc.perform(get("/api/_search/alunos?query=id:" + aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].transferido").value(hasItem(DEFAULT_TRANSFERIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].turmaAnterior").value(hasItem(DEFAULT_TURMA_ANTERIOR)))
            .andExpect(jsonPath("$.[*].anoConclusao").value(hasItem(DEFAULT_ANO_CONCLUSAO)))
            .andExpect(jsonPath("$.[*].cursoFrequentado").value(hasItem(DEFAULT_CURSO_FREQUENTADO)))
            .andExpect(jsonPath("$.[*].nomeEscolaAnteriror").value(hasItem(DEFAULT_NOME_ESCOLA_ANTERIROR)))
            .andExpect(jsonPath("$.[*].enderecoEscolaAnterior").value(hasItem(DEFAULT_ENDERECO_ESCOLA_ANTERIOR)))
            .andExpect(jsonPath("$.[*].classeConcluida").value(hasItem(DEFAULT_CLASSE_CONCLUIDA)))
            .andExpect(jsonPath("$.[*].numeroProcessoAnterior").value(hasItem(DEFAULT_NUMERO_PROCESSO_ANTERIOR)))
            .andExpect(jsonPath("$.[*].situacaoAnterior").value(hasItem(DEFAULT_SITUACAO_ANTERIOR)));
    }
}
