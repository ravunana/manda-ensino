package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.MeioLiquidacao;
import com.ravunana.ensino.repository.MeioLiquidacaoRepository;
import com.ravunana.ensino.repository.search.MeioLiquidacaoSearchRepository;
import com.ravunana.ensino.service.MeioLiquidacaoService;
import com.ravunana.ensino.service.dto.MeioLiquidacaoDTO;
import com.ravunana.ensino.service.mapper.MeioLiquidacaoMapper;
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
 * Integration tests for the {@link MeioLiquidacaoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class MeioLiquidacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MOSTRAR_PONTO_VENDA = false;
    private static final Boolean UPDATED_MOSTRAR_PONTO_VENDA = true;

    @Autowired
    private MeioLiquidacaoRepository meioLiquidacaoRepository;

    @Autowired
    private MeioLiquidacaoMapper meioLiquidacaoMapper;

    @Autowired
    private MeioLiquidacaoService meioLiquidacaoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.MeioLiquidacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private MeioLiquidacaoSearchRepository mockMeioLiquidacaoSearchRepository;

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

    private MockMvc restMeioLiquidacaoMockMvc;

    private MeioLiquidacao meioLiquidacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeioLiquidacaoResource meioLiquidacaoResource = new MeioLiquidacaoResource(meioLiquidacaoService);
        this.restMeioLiquidacaoMockMvc = MockMvcBuilders.standaloneSetup(meioLiquidacaoResource)
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
    public static MeioLiquidacao createEntity(EntityManager em) {
        MeioLiquidacao meioLiquidacao = new MeioLiquidacao()
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA)
            .icon(DEFAULT_ICON)
            .mostrarPontoVenda(DEFAULT_MOSTRAR_PONTO_VENDA);
        return meioLiquidacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeioLiquidacao createUpdatedEntity(EntityManager em) {
        MeioLiquidacao meioLiquidacao = new MeioLiquidacao()
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .icon(UPDATED_ICON)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA);
        return meioLiquidacao;
    }

    @BeforeEach
    public void initTest() {
        meioLiquidacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeioLiquidacao() throws Exception {
        int databaseSizeBeforeCreate = meioLiquidacaoRepository.findAll().size();

        // Create the MeioLiquidacao
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);
        restMeioLiquidacaoMockMvc.perform(post("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeCreate + 1);
        MeioLiquidacao testMeioLiquidacao = meioLiquidacaoList.get(meioLiquidacaoList.size() - 1);
        assertThat(testMeioLiquidacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMeioLiquidacao.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testMeioLiquidacao.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMeioLiquidacao.isMostrarPontoVenda()).isEqualTo(DEFAULT_MOSTRAR_PONTO_VENDA);

        // Validate the MeioLiquidacao in Elasticsearch
        verify(mockMeioLiquidacaoSearchRepository, times(1)).save(testMeioLiquidacao);
    }

    @Test
    @Transactional
    public void createMeioLiquidacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meioLiquidacaoRepository.findAll().size();

        // Create the MeioLiquidacao with an existing ID
        meioLiquidacao.setId(1L);
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeioLiquidacaoMockMvc.perform(post("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the MeioLiquidacao in Elasticsearch
        verify(mockMeioLiquidacaoSearchRepository, times(0)).save(meioLiquidacao);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meioLiquidacaoRepository.findAll().size();
        // set the field null
        meioLiquidacao.setNome(null);

        // Create the MeioLiquidacao, which fails.
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);

        restMeioLiquidacaoMockMvc.perform(post("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMostrarPontoVendaIsRequired() throws Exception {
        int databaseSizeBeforeTest = meioLiquidacaoRepository.findAll().size();
        // set the field null
        meioLiquidacao.setMostrarPontoVenda(null);

        // Create the MeioLiquidacao, which fails.
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);

        restMeioLiquidacaoMockMvc.perform(post("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaos() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meioLiquidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMeioLiquidacao() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get the meioLiquidacao
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos/{id}", meioLiquidacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meioLiquidacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.mostrarPontoVenda").value(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeioLiquidacao() throws Exception {
        // Get the meioLiquidacao
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeioLiquidacao() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        int databaseSizeBeforeUpdate = meioLiquidacaoRepository.findAll().size();

        // Update the meioLiquidacao
        MeioLiquidacao updatedMeioLiquidacao = meioLiquidacaoRepository.findById(meioLiquidacao.getId()).get();
        // Disconnect from session so that the updates on updatedMeioLiquidacao are not directly saved in db
        em.detach(updatedMeioLiquidacao);
        updatedMeioLiquidacao
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .icon(UPDATED_ICON)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA);
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(updatedMeioLiquidacao);

        restMeioLiquidacaoMockMvc.perform(put("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isOk());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeUpdate);
        MeioLiquidacao testMeioLiquidacao = meioLiquidacaoList.get(meioLiquidacaoList.size() - 1);
        assertThat(testMeioLiquidacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMeioLiquidacao.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testMeioLiquidacao.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMeioLiquidacao.isMostrarPontoVenda()).isEqualTo(UPDATED_MOSTRAR_PONTO_VENDA);

        // Validate the MeioLiquidacao in Elasticsearch
        verify(mockMeioLiquidacaoSearchRepository, times(1)).save(testMeioLiquidacao);
    }

    @Test
    @Transactional
    public void updateNonExistingMeioLiquidacao() throws Exception {
        int databaseSizeBeforeUpdate = meioLiquidacaoRepository.findAll().size();

        // Create the MeioLiquidacao
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeioLiquidacaoMockMvc.perform(put("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MeioLiquidacao in Elasticsearch
        verify(mockMeioLiquidacaoSearchRepository, times(0)).save(meioLiquidacao);
    }

    @Test
    @Transactional
    public void deleteMeioLiquidacao() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        int databaseSizeBeforeDelete = meioLiquidacaoRepository.findAll().size();

        // Delete the meioLiquidacao
        restMeioLiquidacaoMockMvc.perform(delete("/api/meio-liquidacaos/{id}", meioLiquidacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MeioLiquidacao in Elasticsearch
        verify(mockMeioLiquidacaoSearchRepository, times(1)).deleteById(meioLiquidacao.getId());
    }

    @Test
    @Transactional
    public void searchMeioLiquidacao() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);
        when(mockMeioLiquidacaoSearchRepository.search(queryStringQuery("id:" + meioLiquidacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(meioLiquidacao), PageRequest.of(0, 1), 1));
        // Search the meioLiquidacao
        restMeioLiquidacaoMockMvc.perform(get("/api/_search/meio-liquidacaos?query=id:" + meioLiquidacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meioLiquidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())));
    }
}
