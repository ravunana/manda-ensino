package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.DetalhePagamento;
import com.ravunana.ensino.domain.User;
import com.ravunana.ensino.domain.Emolumento;
import com.ravunana.ensino.domain.Deposito;
import com.ravunana.ensino.repository.DetalhePagamentoRepository;
import com.ravunana.ensino.repository.search.DetalhePagamentoSearchRepository;
import com.ravunana.ensino.service.DetalhePagamentoService;
import com.ravunana.ensino.service.dto.DetalhePagamentoDTO;
import com.ravunana.ensino.service.mapper.DetalhePagamentoMapper;
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
 * Integration tests for the {@link DetalhePagamentoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class DetalhePagamentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MENSALIDADE = false;
    private static final Boolean UPDATED_MENSALIDADE = true;

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);

    private static final Double DEFAULT_DESCONTO = 0D;
    private static final Double UPDATED_DESCONTO = 1D;

    private static final Double DEFAULT_MULTA = 0D;
    private static final Double UPDATED_MULTA = 1D;

    private static final Double DEFAULT_JURO = 0D;
    private static final Double UPDATED_JURO = 1D;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_QUITADO = false;
    private static final Boolean UPDATED_QUITADO = true;

    @Autowired
    private DetalhePagamentoRepository detalhePagamentoRepository;

    @Autowired
    private DetalhePagamentoMapper detalhePagamentoMapper;

    @Autowired
    private DetalhePagamentoService detalhePagamentoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.DetalhePagamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DetalhePagamentoSearchRepository mockDetalhePagamentoSearchRepository;

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

    private MockMvc restDetalhePagamentoMockMvc;

    private DetalhePagamento detalhePagamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalhePagamentoResource detalhePagamentoResource = new DetalhePagamentoResource(detalhePagamentoService);
        this.restDetalhePagamentoMockMvc = MockMvcBuilders.standaloneSetup(detalhePagamentoResource)
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
    public static DetalhePagamento createEntity(EntityManager em) {
        DetalhePagamento detalhePagamento = new DetalhePagamento()
            .descricao(DEFAULT_DESCRICAO)
            .mensalidade(DEFAULT_MENSALIDADE)
            .quantidade(DEFAULT_QUANTIDADE)
            .valor(DEFAULT_VALOR)
            .desconto(DEFAULT_DESCONTO)
            .multa(DEFAULT_MULTA)
            .juro(DEFAULT_JURO)
            .data(DEFAULT_DATA)
            .vencimento(DEFAULT_VENCIMENTO)
            .quitado(DEFAULT_QUITADO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        detalhePagamento.setUtilizador(user);
        // Add required entity
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumento = EmolumentoResourceIT.createEntity(em);
            em.persist(emolumento);
            em.flush();
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        detalhePagamento.setEmolumento(emolumento);
        // Add required entity
        Deposito deposito;
        if (TestUtil.findAll(em, Deposito.class).isEmpty()) {
            deposito = DepositoResourceIT.createEntity(em);
            em.persist(deposito);
            em.flush();
        } else {
            deposito = TestUtil.findAll(em, Deposito.class).get(0);
        }
        detalhePagamento.setDeposito(deposito);
        return detalhePagamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalhePagamento createUpdatedEntity(EntityManager em) {
        DetalhePagamento detalhePagamento = new DetalhePagamento()
            .descricao(UPDATED_DESCRICAO)
            .mensalidade(UPDATED_MENSALIDADE)
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .multa(UPDATED_MULTA)
            .juro(UPDATED_JURO)
            .data(UPDATED_DATA)
            .vencimento(UPDATED_VENCIMENTO)
            .quitado(UPDATED_QUITADO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        detalhePagamento.setUtilizador(user);
        // Add required entity
        Emolumento emolumento;
        if (TestUtil.findAll(em, Emolumento.class).isEmpty()) {
            emolumento = EmolumentoResourceIT.createUpdatedEntity(em);
            em.persist(emolumento);
            em.flush();
        } else {
            emolumento = TestUtil.findAll(em, Emolumento.class).get(0);
        }
        detalhePagamento.setEmolumento(emolumento);
        // Add required entity
        Deposito deposito;
        if (TestUtil.findAll(em, Deposito.class).isEmpty()) {
            deposito = DepositoResourceIT.createUpdatedEntity(em);
            em.persist(deposito);
            em.flush();
        } else {
            deposito = TestUtil.findAll(em, Deposito.class).get(0);
        }
        detalhePagamento.setDeposito(deposito);
        return detalhePagamento;
    }

    @BeforeEach
    public void initTest() {
        detalhePagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalhePagamento() throws Exception {
        int databaseSizeBeforeCreate = detalhePagamentoRepository.findAll().size();

        // Create the DetalhePagamento
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);
        restDetalhePagamentoMockMvc.perform(post("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalhePagamento in the database
        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        DetalhePagamento testDetalhePagamento = detalhePagamentoList.get(detalhePagamentoList.size() - 1);
        assertThat(testDetalhePagamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDetalhePagamento.isMensalidade()).isEqualTo(DEFAULT_MENSALIDADE);
        assertThat(testDetalhePagamento.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testDetalhePagamento.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testDetalhePagamento.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testDetalhePagamento.getMulta()).isEqualTo(DEFAULT_MULTA);
        assertThat(testDetalhePagamento.getJuro()).isEqualTo(DEFAULT_JURO);
        assertThat(testDetalhePagamento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDetalhePagamento.getVencimento()).isEqualTo(DEFAULT_VENCIMENTO);
        assertThat(testDetalhePagamento.isQuitado()).isEqualTo(DEFAULT_QUITADO);

        // Validate the DetalhePagamento in Elasticsearch
        verify(mockDetalhePagamentoSearchRepository, times(1)).save(testDetalhePagamento);
    }

    @Test
    @Transactional
    public void createDetalhePagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalhePagamentoRepository.findAll().size();

        // Create the DetalhePagamento with an existing ID
        detalhePagamento.setId(1L);
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalhePagamentoMockMvc.perform(post("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalhePagamento in the database
        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the DetalhePagamento in Elasticsearch
        verify(mockDetalhePagamentoSearchRepository, times(0)).save(detalhePagamento);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalhePagamentoRepository.findAll().size();
        // set the field null
        detalhePagamento.setDescricao(null);

        // Create the DetalhePagamento, which fails.
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);

        restDetalhePagamentoMockMvc.perform(post("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMensalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalhePagamentoRepository.findAll().size();
        // set the field null
        detalhePagamento.setMensalidade(null);

        // Create the DetalhePagamento, which fails.
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);

        restDetalhePagamentoMockMvc.perform(post("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalhePagamentoRepository.findAll().size();
        // set the field null
        detalhePagamento.setQuantidade(null);

        // Create the DetalhePagamento, which fails.
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);

        restDetalhePagamentoMockMvc.perform(post("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalhePagamentoRepository.findAll().size();
        // set the field null
        detalhePagamento.setValor(null);

        // Create the DetalhePagamento, which fails.
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);

        restDetalhePagamentoMockMvc.perform(post("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuitadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalhePagamentoRepository.findAll().size();
        // set the field null
        detalhePagamento.setQuitado(null);

        // Create the DetalhePagamento, which fails.
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);

        restDetalhePagamentoMockMvc.perform(post("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetalhePagamentos() throws Exception {
        // Initialize the database
        detalhePagamentoRepository.saveAndFlush(detalhePagamento);

        // Get all the detalhePagamentoList
        restDetalhePagamentoMockMvc.perform(get("/api/detalhe-pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhePagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].mensalidade").value(hasItem(DEFAULT_MENSALIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].multa").value(hasItem(DEFAULT_MULTA.doubleValue())))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(DEFAULT_JURO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].vencimento").value(hasItem(DEFAULT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].quitado").value(hasItem(DEFAULT_QUITADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDetalhePagamento() throws Exception {
        // Initialize the database
        detalhePagamentoRepository.saveAndFlush(detalhePagamento);

        // Get the detalhePagamento
        restDetalhePagamentoMockMvc.perform(get("/api/detalhe-pagamentos/{id}", detalhePagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalhePagamento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.mensalidade").value(DEFAULT_MENSALIDADE.booleanValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.multa").value(DEFAULT_MULTA.doubleValue()))
            .andExpect(jsonPath("$.juro").value(DEFAULT_JURO.doubleValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.vencimento").value(DEFAULT_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.quitado").value(DEFAULT_QUITADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalhePagamento() throws Exception {
        // Get the detalhePagamento
        restDetalhePagamentoMockMvc.perform(get("/api/detalhe-pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalhePagamento() throws Exception {
        // Initialize the database
        detalhePagamentoRepository.saveAndFlush(detalhePagamento);

        int databaseSizeBeforeUpdate = detalhePagamentoRepository.findAll().size();

        // Update the detalhePagamento
        DetalhePagamento updatedDetalhePagamento = detalhePagamentoRepository.findById(detalhePagamento.getId()).get();
        // Disconnect from session so that the updates on updatedDetalhePagamento are not directly saved in db
        em.detach(updatedDetalhePagamento);
        updatedDetalhePagamento
            .descricao(UPDATED_DESCRICAO)
            .mensalidade(UPDATED_MENSALIDADE)
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .multa(UPDATED_MULTA)
            .juro(UPDATED_JURO)
            .data(UPDATED_DATA)
            .vencimento(UPDATED_VENCIMENTO)
            .quitado(UPDATED_QUITADO);
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(updatedDetalhePagamento);

        restDetalhePagamentoMockMvc.perform(put("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isOk());

        // Validate the DetalhePagamento in the database
        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeUpdate);
        DetalhePagamento testDetalhePagamento = detalhePagamentoList.get(detalhePagamentoList.size() - 1);
        assertThat(testDetalhePagamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDetalhePagamento.isMensalidade()).isEqualTo(UPDATED_MENSALIDADE);
        assertThat(testDetalhePagamento.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testDetalhePagamento.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testDetalhePagamento.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testDetalhePagamento.getMulta()).isEqualTo(UPDATED_MULTA);
        assertThat(testDetalhePagamento.getJuro()).isEqualTo(UPDATED_JURO);
        assertThat(testDetalhePagamento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDetalhePagamento.getVencimento()).isEqualTo(UPDATED_VENCIMENTO);
        assertThat(testDetalhePagamento.isQuitado()).isEqualTo(UPDATED_QUITADO);

        // Validate the DetalhePagamento in Elasticsearch
        verify(mockDetalhePagamentoSearchRepository, times(1)).save(testDetalhePagamento);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalhePagamento() throws Exception {
        int databaseSizeBeforeUpdate = detalhePagamentoRepository.findAll().size();

        // Create the DetalhePagamento
        DetalhePagamentoDTO detalhePagamentoDTO = detalhePagamentoMapper.toDto(detalhePagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalhePagamentoMockMvc.perform(put("/api/detalhe-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalhePagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalhePagamento in the database
        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DetalhePagamento in Elasticsearch
        verify(mockDetalhePagamentoSearchRepository, times(0)).save(detalhePagamento);
    }

    @Test
    @Transactional
    public void deleteDetalhePagamento() throws Exception {
        // Initialize the database
        detalhePagamentoRepository.saveAndFlush(detalhePagamento);

        int databaseSizeBeforeDelete = detalhePagamentoRepository.findAll().size();

        // Delete the detalhePagamento
        restDetalhePagamentoMockMvc.perform(delete("/api/detalhe-pagamentos/{id}", detalhePagamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalhePagamento> detalhePagamentoList = detalhePagamentoRepository.findAll();
        assertThat(detalhePagamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DetalhePagamento in Elasticsearch
        verify(mockDetalhePagamentoSearchRepository, times(1)).deleteById(detalhePagamento.getId());
    }

    @Test
    @Transactional
    public void searchDetalhePagamento() throws Exception {
        // Initialize the database
        detalhePagamentoRepository.saveAndFlush(detalhePagamento);
        when(mockDetalhePagamentoSearchRepository.search(queryStringQuery("id:" + detalhePagamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(detalhePagamento), PageRequest.of(0, 1), 1));
        // Search the detalhePagamento
        restDetalhePagamentoMockMvc.perform(get("/api/_search/detalhe-pagamentos?query=id:" + detalhePagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalhePagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].mensalidade").value(hasItem(DEFAULT_MENSALIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].multa").value(hasItem(DEFAULT_MULTA.doubleValue())))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(DEFAULT_JURO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].vencimento").value(hasItem(DEFAULT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].quitado").value(hasItem(DEFAULT_QUITADO.booleanValue())));
    }
}
