package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Pagamento;
import com.ravunana.ensino.domain.User;
import com.ravunana.ensino.domain.Aluno;
import com.ravunana.ensino.domain.FormaLiquidacao;
import com.ravunana.ensino.repository.PagamentoRepository;
import com.ravunana.ensino.repository.search.PagamentoSearchRepository;
import com.ravunana.ensino.service.PagamentoService;
import com.ravunana.ensino.service.dto.PagamentoDTO;
import com.ravunana.ensino.service.mapper.PagamentoMapper;
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
 * Integration tests for the {@link PagamentoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class PagamentoResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Autowired
    private PagamentoService pagamentoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.PagamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PagamentoSearchRepository mockPagamentoSearchRepository;

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

    private MockMvc restPagamentoMockMvc;

    private Pagamento pagamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagamentoResource pagamentoResource = new PagamentoResource(pagamentoService);
        this.restPagamentoMockMvc = MockMvcBuilders.standaloneSetup(pagamentoResource)
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
    public static Pagamento createEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .data(DEFAULT_DATA)
            .numero(DEFAULT_NUMERO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        pagamento.setUtilizador(user);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        pagamento.setAluno(aluno);
        // Add required entity
        FormaLiquidacao formaLiquidacao;
        if (TestUtil.findAll(em, FormaLiquidacao.class).isEmpty()) {
            formaLiquidacao = FormaLiquidacaoResourceIT.createEntity(em);
            em.persist(formaLiquidacao);
            em.flush();
        } else {
            formaLiquidacao = TestUtil.findAll(em, FormaLiquidacao.class).get(0);
        }
        pagamento.setFormaLiquidacao(formaLiquidacao);
        return pagamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagamento createUpdatedEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .data(UPDATED_DATA)
            .numero(UPDATED_NUMERO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        pagamento.setUtilizador(user);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createUpdatedEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        pagamento.setAluno(aluno);
        // Add required entity
        FormaLiquidacao formaLiquidacao;
        if (TestUtil.findAll(em, FormaLiquidacao.class).isEmpty()) {
            formaLiquidacao = FormaLiquidacaoResourceIT.createUpdatedEntity(em);
            em.persist(formaLiquidacao);
            em.flush();
        } else {
            formaLiquidacao = TestUtil.findAll(em, FormaLiquidacao.class).get(0);
        }
        pagamento.setFormaLiquidacao(formaLiquidacao);
        return pagamento;
    }

    @BeforeEach
    public void initTest() {
        pagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagamento() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPagamento.getNumero()).isEqualTo(DEFAULT_NUMERO);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(1)).save(testPagamento);
    }

    @Test
    @Transactional
    public void createPagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // Create the Pagamento with an existing ID
        pagamento.setId(1L);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(0)).save(pagamento);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setNumero(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        restPagamentoMockMvc.perform(post("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPagamentos() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList
        restPagamentoMockMvc.perform(get("/api/pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagamento.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingPagamento() throws Exception {
        // Get the pagamento
        restPagamentoMockMvc.perform(get("/api/pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento
        Pagamento updatedPagamento = pagamentoRepository.findById(pagamento.getId()).get();
        // Disconnect from session so that the updates on updatedPagamento are not directly saved in db
        em.detach(updatedPagamento);
        updatedPagamento
            .data(UPDATED_DATA)
            .numero(UPDATED_NUMERO);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(updatedPagamento);

        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPagamento.getNumero()).isEqualTo(UPDATED_NUMERO);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(1)).save(testPagamento);
    }

    @Test
    @Transactional
    public void updateNonExistingPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc.perform(put("/api/pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(0)).save(pagamento);
    }

    @Test
    @Transactional
    public void deletePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeDelete = pagamentoRepository.findAll().size();

        // Delete the pagamento
        restPagamentoMockMvc.perform(delete("/api/pagamentos/{id}", pagamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pagamento in Elasticsearch
        verify(mockPagamentoSearchRepository, times(1)).deleteById(pagamento.getId());
    }

    @Test
    @Transactional
    public void searchPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);
        when(mockPagamentoSearchRepository.search(queryStringQuery("id:" + pagamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pagamento), PageRequest.of(0, 1), 1));
        // Search the pagamento
        restPagamentoMockMvc.perform(get("/api/_search/pagamentos?query=id:" + pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
}
