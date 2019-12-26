package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Deposito;
import com.ravunana.ensino.domain.User;
import com.ravunana.ensino.domain.MeioLiquidacao;
import com.ravunana.ensino.domain.Aluno;
import com.ravunana.ensino.domain.CoordenadaBancaria;
import com.ravunana.ensino.repository.DepositoRepository;
import com.ravunana.ensino.repository.search.DepositoSearchRepository;
import com.ravunana.ensino.service.DepositoService;
import com.ravunana.ensino.service.dto.DepositoDTO;
import com.ravunana.ensino.service.mapper.DepositoMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DepositoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class DepositoResourceIT {

    private static final String DEFAULT_NUMERO_TALAO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TALAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DEPOSITO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DEPOSITO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SALDO = new BigDecimal(0);
    private static final BigDecimal UPDATED_SALDO = new BigDecimal(1);

    @Autowired
    private DepositoRepository depositoRepository;

    @Autowired
    private DepositoMapper depositoMapper;

    @Autowired
    private DepositoService depositoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.DepositoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DepositoSearchRepository mockDepositoSearchRepository;

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

    private MockMvc restDepositoMockMvc;

    private Deposito deposito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepositoResource depositoResource = new DepositoResource(depositoService);
        this.restDepositoMockMvc = MockMvcBuilders.standaloneSetup(depositoResource)
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
    public static Deposito createEntity(EntityManager em) {
        Deposito deposito = new Deposito()
            .numeroTalao(DEFAULT_NUMERO_TALAO)
            .dataDeposito(DEFAULT_DATA_DEPOSITO)
            .valor(DEFAULT_VALOR)
            .saldo(DEFAULT_SALDO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        deposito.setUtilizador(user);
        // Add required entity
        MeioLiquidacao meioLiquidacao;
        if (TestUtil.findAll(em, MeioLiquidacao.class).isEmpty()) {
            meioLiquidacao = MeioLiquidacaoResourceIT.createEntity(em);
            em.persist(meioLiquidacao);
            em.flush();
        } else {
            meioLiquidacao = TestUtil.findAll(em, MeioLiquidacao.class).get(0);
        }
        deposito.setMeioLiquidacao(meioLiquidacao);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        deposito.setAluno(aluno);
        // Add required entity
        CoordenadaBancaria coordenadaBancaria;
        if (TestUtil.findAll(em, CoordenadaBancaria.class).isEmpty()) {
            coordenadaBancaria = CoordenadaBancariaResourceIT.createEntity(em);
            em.persist(coordenadaBancaria);
            em.flush();
        } else {
            coordenadaBancaria = TestUtil.findAll(em, CoordenadaBancaria.class).get(0);
        }
        deposito.setConta(coordenadaBancaria);
        return deposito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposito createUpdatedEntity(EntityManager em) {
        Deposito deposito = new Deposito()
            .numeroTalao(UPDATED_NUMERO_TALAO)
            .dataDeposito(UPDATED_DATA_DEPOSITO)
            .valor(UPDATED_VALOR)
            .saldo(UPDATED_SALDO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        deposito.setUtilizador(user);
        // Add required entity
        MeioLiquidacao meioLiquidacao;
        if (TestUtil.findAll(em, MeioLiquidacao.class).isEmpty()) {
            meioLiquidacao = MeioLiquidacaoResourceIT.createUpdatedEntity(em);
            em.persist(meioLiquidacao);
            em.flush();
        } else {
            meioLiquidacao = TestUtil.findAll(em, MeioLiquidacao.class).get(0);
        }
        deposito.setMeioLiquidacao(meioLiquidacao);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createUpdatedEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        deposito.setAluno(aluno);
        // Add required entity
        CoordenadaBancaria coordenadaBancaria;
        if (TestUtil.findAll(em, CoordenadaBancaria.class).isEmpty()) {
            coordenadaBancaria = CoordenadaBancariaResourceIT.createUpdatedEntity(em);
            em.persist(coordenadaBancaria);
            em.flush();
        } else {
            coordenadaBancaria = TestUtil.findAll(em, CoordenadaBancaria.class).get(0);
        }
        deposito.setConta(coordenadaBancaria);
        return deposito;
    }

    @BeforeEach
    public void initTest() {
        deposito = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeposito() throws Exception {
        int databaseSizeBeforeCreate = depositoRepository.findAll().size();

        // Create the Deposito
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);
        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isCreated());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeCreate + 1);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getNumeroTalao()).isEqualTo(DEFAULT_NUMERO_TALAO);
        assertThat(testDeposito.getDataDeposito()).isEqualTo(DEFAULT_DATA_DEPOSITO);
        assertThat(testDeposito.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testDeposito.getSaldo()).isEqualTo(DEFAULT_SALDO);

        // Validate the Deposito in Elasticsearch
        verify(mockDepositoSearchRepository, times(1)).save(testDeposito);
    }

    @Test
    @Transactional
    public void createDepositoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depositoRepository.findAll().size();

        // Create the Deposito with an existing ID
        deposito.setId(1L);
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Deposito in Elasticsearch
        verify(mockDepositoSearchRepository, times(0)).save(deposito);
    }


    @Test
    @Transactional
    public void checkNumeroTalaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoRepository.findAll().size();
        // set the field null
        deposito.setNumeroTalao(null);

        // Create the Deposito, which fails.
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDepositoIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoRepository.findAll().size();
        // set the field null
        deposito.setDataDeposito(null);

        // Create the Deposito, which fails.
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoRepository.findAll().size();
        // set the field null
        deposito.setValor(null);

        // Create the Deposito, which fails.
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoRepository.findAll().size();
        // set the field null
        deposito.setSaldo(null);

        // Create the Deposito, which fails.
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepositos() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        // Get all the depositoList
        restDepositoMockMvc.perform(get("/api/depositos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deposito.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTalao").value(hasItem(DEFAULT_NUMERO_TALAO)))
            .andExpect(jsonPath("$.[*].dataDeposito").value(hasItem(DEFAULT_DATA_DEPOSITO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.intValue())));
    }
    
    @Test
    @Transactional
    public void getDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        // Get the deposito
        restDepositoMockMvc.perform(get("/api/depositos/{id}", deposito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deposito.getId().intValue()))
            .andExpect(jsonPath("$.numeroTalao").value(DEFAULT_NUMERO_TALAO))
            .andExpect(jsonPath("$.dataDeposito").value(DEFAULT_DATA_DEPOSITO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeposito() throws Exception {
        // Get the deposito
        restDepositoMockMvc.perform(get("/api/depositos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();

        // Update the deposito
        Deposito updatedDeposito = depositoRepository.findById(deposito.getId()).get();
        // Disconnect from session so that the updates on updatedDeposito are not directly saved in db
        em.detach(updatedDeposito);
        updatedDeposito
            .numeroTalao(UPDATED_NUMERO_TALAO)
            .dataDeposito(UPDATED_DATA_DEPOSITO)
            .valor(UPDATED_VALOR)
            .saldo(UPDATED_SALDO);
        DepositoDTO depositoDTO = depositoMapper.toDto(updatedDeposito);

        restDepositoMockMvc.perform(put("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isOk());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getNumeroTalao()).isEqualTo(UPDATED_NUMERO_TALAO);
        assertThat(testDeposito.getDataDeposito()).isEqualTo(UPDATED_DATA_DEPOSITO);
        assertThat(testDeposito.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testDeposito.getSaldo()).isEqualTo(UPDATED_SALDO);

        // Validate the Deposito in Elasticsearch
        verify(mockDepositoSearchRepository, times(1)).save(testDeposito);
    }

    @Test
    @Transactional
    public void updateNonExistingDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();

        // Create the Deposito
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositoMockMvc.perform(put("/api/depositos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Deposito in Elasticsearch
        verify(mockDepositoSearchRepository, times(0)).save(deposito);
    }

    @Test
    @Transactional
    public void deleteDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeDelete = depositoRepository.findAll().size();

        // Delete the deposito
        restDepositoMockMvc.perform(delete("/api/depositos/{id}", deposito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Deposito in Elasticsearch
        verify(mockDepositoSearchRepository, times(1)).deleteById(deposito.getId());
    }

    @Test
    @Transactional
    public void searchDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);
        when(mockDepositoSearchRepository.search(queryStringQuery("id:" + deposito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(deposito), PageRequest.of(0, 1), 1));
        // Search the deposito
        restDepositoMockMvc.perform(get("/api/_search/depositos?query=id:" + deposito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deposito.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTalao").value(hasItem(DEFAULT_NUMERO_TALAO)))
            .andExpect(jsonPath("$.[*].dataDeposito").value(hasItem(DEFAULT_DATA_DEPOSITO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.intValue())));
    }
}
