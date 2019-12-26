package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.CoordenadaBancaria;
import com.ravunana.ensino.domain.InstituicaoEnsino;
import com.ravunana.ensino.repository.CoordenadaBancariaRepository;
import com.ravunana.ensino.repository.search.CoordenadaBancariaSearchRepository;
import com.ravunana.ensino.service.CoordenadaBancariaService;
import com.ravunana.ensino.service.dto.CoordenadaBancariaDTO;
import com.ravunana.ensino.service.mapper.CoordenadaBancariaMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
 * Integration tests for the {@link CoordenadaBancariaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class CoordenadaBancariaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_PROPRIETARIO = "AAAAAAAAAA";
    private static final String UPDATED_PROPRIETARIO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CONTA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CONTA = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_MOSTRAR_DOCUMENTO = false;
    private static final Boolean UPDATED_MOSTRAR_DOCUMENTO = true;

    private static final Boolean DEFAULT_MOSTRAR_PONTO_VENDA = false;
    private static final Boolean UPDATED_MOSTRAR_PONTO_VENDA = true;

    private static final Boolean DEFAULT_PADRAO_RECEBIMENTO = false;
    private static final Boolean UPDATED_PADRAO_RECEBIMENTO = true;

    private static final Boolean DEFAULT_PADRAO_PAGAMENTO = false;
    private static final Boolean UPDATED_PADRAO_PAGAMENTO = true;

    @Autowired
    private CoordenadaBancariaRepository coordenadaBancariaRepository;

    @Mock
    private CoordenadaBancariaRepository coordenadaBancariaRepositoryMock;

    @Autowired
    private CoordenadaBancariaMapper coordenadaBancariaMapper;

    @Mock
    private CoordenadaBancariaService coordenadaBancariaServiceMock;

    @Autowired
    private CoordenadaBancariaService coordenadaBancariaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.CoordenadaBancariaSearchRepositoryMockConfiguration
     */
    @Autowired
    private CoordenadaBancariaSearchRepository mockCoordenadaBancariaSearchRepository;

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

    private MockMvc restCoordenadaBancariaMockMvc;

    private CoordenadaBancaria coordenadaBancaria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordenadaBancariaResource coordenadaBancariaResource = new CoordenadaBancariaResource(coordenadaBancariaService);
        this.restCoordenadaBancariaMockMvc = MockMvcBuilders.standaloneSetup(coordenadaBancariaResource)
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
    public static CoordenadaBancaria createEntity(EntityManager em) {
        CoordenadaBancaria coordenadaBancaria = new CoordenadaBancaria()
            .descricao(DEFAULT_DESCRICAO)
            .proprietario(DEFAULT_PROPRIETARIO)
            .numeroConta(DEFAULT_NUMERO_CONTA)
            .iban(DEFAULT_IBAN)
            .ativo(DEFAULT_ATIVO)
            .mostrarDocumento(DEFAULT_MOSTRAR_DOCUMENTO)
            .mostrarPontoVenda(DEFAULT_MOSTRAR_PONTO_VENDA)
            .padraoRecebimento(DEFAULT_PADRAO_RECEBIMENTO)
            .padraoPagamento(DEFAULT_PADRAO_PAGAMENTO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        coordenadaBancaria.getInstituicaoEnsinos().add(instituicaoEnsino);
        return coordenadaBancaria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoordenadaBancaria createUpdatedEntity(EntityManager em) {
        CoordenadaBancaria coordenadaBancaria = new CoordenadaBancaria()
            .descricao(UPDATED_DESCRICAO)
            .proprietario(UPDATED_PROPRIETARIO)
            .numeroConta(UPDATED_NUMERO_CONTA)
            .iban(UPDATED_IBAN)
            .ativo(UPDATED_ATIVO)
            .mostrarDocumento(UPDATED_MOSTRAR_DOCUMENTO)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA)
            .padraoRecebimento(UPDATED_PADRAO_RECEBIMENTO)
            .padraoPagamento(UPDATED_PADRAO_PAGAMENTO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createUpdatedEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        coordenadaBancaria.getInstituicaoEnsinos().add(instituicaoEnsino);
        return coordenadaBancaria;
    }

    @BeforeEach
    public void initTest() {
        coordenadaBancaria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordenadaBancaria() throws Exception {
        int databaseSizeBeforeCreate = coordenadaBancariaRepository.findAll().size();

        // Create the CoordenadaBancaria
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);
        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isCreated());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeCreate + 1);
        CoordenadaBancaria testCoordenadaBancaria = coordenadaBancariaList.get(coordenadaBancariaList.size() - 1);
        assertThat(testCoordenadaBancaria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCoordenadaBancaria.getProprietario()).isEqualTo(DEFAULT_PROPRIETARIO);
        assertThat(testCoordenadaBancaria.getNumeroConta()).isEqualTo(DEFAULT_NUMERO_CONTA);
        assertThat(testCoordenadaBancaria.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testCoordenadaBancaria.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testCoordenadaBancaria.isMostrarDocumento()).isEqualTo(DEFAULT_MOSTRAR_DOCUMENTO);
        assertThat(testCoordenadaBancaria.isMostrarPontoVenda()).isEqualTo(DEFAULT_MOSTRAR_PONTO_VENDA);
        assertThat(testCoordenadaBancaria.isPadraoRecebimento()).isEqualTo(DEFAULT_PADRAO_RECEBIMENTO);
        assertThat(testCoordenadaBancaria.isPadraoPagamento()).isEqualTo(DEFAULT_PADRAO_PAGAMENTO);

        // Validate the CoordenadaBancaria in Elasticsearch
        verify(mockCoordenadaBancariaSearchRepository, times(1)).save(testCoordenadaBancaria);
    }

    @Test
    @Transactional
    public void createCoordenadaBancariaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordenadaBancariaRepository.findAll().size();

        // Create the CoordenadaBancaria with an existing ID
        coordenadaBancaria.setId(1L);
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeCreate);

        // Validate the CoordenadaBancaria in Elasticsearch
        verify(mockCoordenadaBancariaSearchRepository, times(0)).save(coordenadaBancaria);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setDescricao(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProprietarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setProprietario(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroContaIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setNumeroConta(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setAtivo(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancarias() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordenadaBancaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].proprietario").value(hasItem(DEFAULT_PROPRIETARIO)))
            .andExpect(jsonPath("$.[*].numeroConta").value(hasItem(DEFAULT_NUMERO_CONTA)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarDocumento").value(hasItem(DEFAULT_MOSTRAR_DOCUMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoRecebimento").value(hasItem(DEFAULT_PADRAO_RECEBIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoPagamento").value(hasItem(DEFAULT_PADRAO_PAGAMENTO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCoordenadaBancariasWithEagerRelationshipsIsEnabled() throws Exception {
        CoordenadaBancariaResource coordenadaBancariaResource = new CoordenadaBancariaResource(coordenadaBancariaServiceMock);
        when(coordenadaBancariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCoordenadaBancariaMockMvc = MockMvcBuilders.standaloneSetup(coordenadaBancariaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?eagerload=true"))
        .andExpect(status().isOk());

        verify(coordenadaBancariaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCoordenadaBancariasWithEagerRelationshipsIsNotEnabled() throws Exception {
        CoordenadaBancariaResource coordenadaBancariaResource = new CoordenadaBancariaResource(coordenadaBancariaServiceMock);
            when(coordenadaBancariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCoordenadaBancariaMockMvc = MockMvcBuilders.standaloneSetup(coordenadaBancariaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?eagerload=true"))
        .andExpect(status().isOk());

            verify(coordenadaBancariaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCoordenadaBancaria() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get the coordenadaBancaria
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias/{id}", coordenadaBancaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordenadaBancaria.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.proprietario").value(DEFAULT_PROPRIETARIO))
            .andExpect(jsonPath("$.numeroConta").value(DEFAULT_NUMERO_CONTA))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.mostrarDocumento").value(DEFAULT_MOSTRAR_DOCUMENTO.booleanValue()))
            .andExpect(jsonPath("$.mostrarPontoVenda").value(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue()))
            .andExpect(jsonPath("$.padraoRecebimento").value(DEFAULT_PADRAO_RECEBIMENTO.booleanValue()))
            .andExpect(jsonPath("$.padraoPagamento").value(DEFAULT_PADRAO_PAGAMENTO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordenadaBancaria() throws Exception {
        // Get the coordenadaBancaria
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordenadaBancaria() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        int databaseSizeBeforeUpdate = coordenadaBancariaRepository.findAll().size();

        // Update the coordenadaBancaria
        CoordenadaBancaria updatedCoordenadaBancaria = coordenadaBancariaRepository.findById(coordenadaBancaria.getId()).get();
        // Disconnect from session so that the updates on updatedCoordenadaBancaria are not directly saved in db
        em.detach(updatedCoordenadaBancaria);
        updatedCoordenadaBancaria
            .descricao(UPDATED_DESCRICAO)
            .proprietario(UPDATED_PROPRIETARIO)
            .numeroConta(UPDATED_NUMERO_CONTA)
            .iban(UPDATED_IBAN)
            .ativo(UPDATED_ATIVO)
            .mostrarDocumento(UPDATED_MOSTRAR_DOCUMENTO)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA)
            .padraoRecebimento(UPDATED_PADRAO_RECEBIMENTO)
            .padraoPagamento(UPDATED_PADRAO_PAGAMENTO);
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(updatedCoordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(put("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isOk());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeUpdate);
        CoordenadaBancaria testCoordenadaBancaria = coordenadaBancariaList.get(coordenadaBancariaList.size() - 1);
        assertThat(testCoordenadaBancaria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCoordenadaBancaria.getProprietario()).isEqualTo(UPDATED_PROPRIETARIO);
        assertThat(testCoordenadaBancaria.getNumeroConta()).isEqualTo(UPDATED_NUMERO_CONTA);
        assertThat(testCoordenadaBancaria.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testCoordenadaBancaria.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testCoordenadaBancaria.isMostrarDocumento()).isEqualTo(UPDATED_MOSTRAR_DOCUMENTO);
        assertThat(testCoordenadaBancaria.isMostrarPontoVenda()).isEqualTo(UPDATED_MOSTRAR_PONTO_VENDA);
        assertThat(testCoordenadaBancaria.isPadraoRecebimento()).isEqualTo(UPDATED_PADRAO_RECEBIMENTO);
        assertThat(testCoordenadaBancaria.isPadraoPagamento()).isEqualTo(UPDATED_PADRAO_PAGAMENTO);

        // Validate the CoordenadaBancaria in Elasticsearch
        verify(mockCoordenadaBancariaSearchRepository, times(1)).save(testCoordenadaBancaria);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordenadaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = coordenadaBancariaRepository.findAll().size();

        // Create the CoordenadaBancaria
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoordenadaBancariaMockMvc.perform(put("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CoordenadaBancaria in Elasticsearch
        verify(mockCoordenadaBancariaSearchRepository, times(0)).save(coordenadaBancaria);
    }

    @Test
    @Transactional
    public void deleteCoordenadaBancaria() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        int databaseSizeBeforeDelete = coordenadaBancariaRepository.findAll().size();

        // Delete the coordenadaBancaria
        restCoordenadaBancariaMockMvc.perform(delete("/api/coordenada-bancarias/{id}", coordenadaBancaria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CoordenadaBancaria in Elasticsearch
        verify(mockCoordenadaBancariaSearchRepository, times(1)).deleteById(coordenadaBancaria.getId());
    }

    @Test
    @Transactional
    public void searchCoordenadaBancaria() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);
        when(mockCoordenadaBancariaSearchRepository.search(queryStringQuery("id:" + coordenadaBancaria.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(coordenadaBancaria), PageRequest.of(0, 1), 1));
        // Search the coordenadaBancaria
        restCoordenadaBancariaMockMvc.perform(get("/api/_search/coordenada-bancarias?query=id:" + coordenadaBancaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordenadaBancaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].proprietario").value(hasItem(DEFAULT_PROPRIETARIO)))
            .andExpect(jsonPath("$.[*].numeroConta").value(hasItem(DEFAULT_NUMERO_CONTA)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarDocumento").value(hasItem(DEFAULT_MOSTRAR_DOCUMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoRecebimento").value(hasItem(DEFAULT_PADRAO_RECEBIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoPagamento").value(hasItem(DEFAULT_PADRAO_PAGAMENTO.booleanValue())));
    }
}
