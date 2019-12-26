package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.InstituicaoEnsino;
import com.ravunana.ensino.repository.InstituicaoEnsinoRepository;
import com.ravunana.ensino.repository.search.InstituicaoEnsinoSearchRepository;
import com.ravunana.ensino.service.InstituicaoEnsinoService;
import com.ravunana.ensino.service.dto.InstituicaoEnsinoDTO;
import com.ravunana.ensino.service.mapper.InstituicaoEnsinoMapper;
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
 * Integration tests for the {@link InstituicaoEnsinoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class InstituicaoEnsinoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGOTIPO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGOTIPO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGOTIPO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGOTIPO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_FUNDACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FUNDACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_VINCULO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_VINCULO = "BBBBBBBBBB";

    private static final String DEFAULT_UNIDADE_PAGADORA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_PAGADORA = "BBBBBBBBBB";

    private static final String DEFAULT_UNIDADE_ORGANICA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_ORGANICA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_INSTALACAO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_INSTALACAO = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSAO = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CARIMBO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CARIMBO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CARIMBO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CARIMBO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_SEDE = false;
    private static final Boolean UPDATED_SEDE = true;

    @Autowired
    private InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    @Autowired
    private InstituicaoEnsinoMapper instituicaoEnsinoMapper;

    @Autowired
    private InstituicaoEnsinoService instituicaoEnsinoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.InstituicaoEnsinoSearchRepositoryMockConfiguration
     */
    @Autowired
    private InstituicaoEnsinoSearchRepository mockInstituicaoEnsinoSearchRepository;

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

    private MockMvc restInstituicaoEnsinoMockMvc;

    private InstituicaoEnsino instituicaoEnsino;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstituicaoEnsinoResource instituicaoEnsinoResource = new InstituicaoEnsinoResource(instituicaoEnsinoService);
        this.restInstituicaoEnsinoMockMvc = MockMvcBuilders.standaloneSetup(instituicaoEnsinoResource)
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
    public static InstituicaoEnsino createEntity(EntityManager em) {
        InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino()
            .nome(DEFAULT_NOME)
            .logotipo(DEFAULT_LOGOTIPO)
            .logotipoContentType(DEFAULT_LOGOTIPO_CONTENT_TYPE)
            .fundacao(DEFAULT_FUNDACAO)
            .numero(DEFAULT_NUMERO)
            .tipoVinculo(DEFAULT_TIPO_VINCULO)
            .unidadePagadora(DEFAULT_UNIDADE_PAGADORA)
            .unidadeOrganica(DEFAULT_UNIDADE_ORGANICA)
            .tipoInstalacao(DEFAULT_TIPO_INSTALACAO)
            .dimensao(DEFAULT_DIMENSAO)
            .carimbo(DEFAULT_CARIMBO)
            .carimboContentType(DEFAULT_CARIMBO_CONTENT_TYPE)
            .sede(DEFAULT_SEDE);
        return instituicaoEnsino;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstituicaoEnsino createUpdatedEntity(EntityManager em) {
        InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino()
            .nome(UPDATED_NOME)
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .fundacao(UPDATED_FUNDACAO)
            .numero(UPDATED_NUMERO)
            .tipoVinculo(UPDATED_TIPO_VINCULO)
            .unidadePagadora(UPDATED_UNIDADE_PAGADORA)
            .unidadeOrganica(UPDATED_UNIDADE_ORGANICA)
            .tipoInstalacao(UPDATED_TIPO_INSTALACAO)
            .dimensao(UPDATED_DIMENSAO)
            .carimbo(UPDATED_CARIMBO)
            .carimboContentType(UPDATED_CARIMBO_CONTENT_TYPE)
            .sede(UPDATED_SEDE);
        return instituicaoEnsino;
    }

    @BeforeEach
    public void initTest() {
        instituicaoEnsino = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeCreate = instituicaoEnsinoRepository.findAll().size();

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);
        restInstituicaoEnsinoMockMvc.perform(post("/api/instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO)))
            .andExpect(status().isCreated());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeCreate + 1);
        InstituicaoEnsino testInstituicaoEnsino = instituicaoEnsinoList.get(instituicaoEnsinoList.size() - 1);
        assertThat(testInstituicaoEnsino.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testInstituicaoEnsino.getLogotipo()).isEqualTo(DEFAULT_LOGOTIPO);
        assertThat(testInstituicaoEnsino.getLogotipoContentType()).isEqualTo(DEFAULT_LOGOTIPO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.getFundacao()).isEqualTo(DEFAULT_FUNDACAO);
        assertThat(testInstituicaoEnsino.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testInstituicaoEnsino.getTipoVinculo()).isEqualTo(DEFAULT_TIPO_VINCULO);
        assertThat(testInstituicaoEnsino.getUnidadePagadora()).isEqualTo(DEFAULT_UNIDADE_PAGADORA);
        assertThat(testInstituicaoEnsino.getUnidadeOrganica()).isEqualTo(DEFAULT_UNIDADE_ORGANICA);
        assertThat(testInstituicaoEnsino.getTipoInstalacao()).isEqualTo(DEFAULT_TIPO_INSTALACAO);
        assertThat(testInstituicaoEnsino.getDimensao()).isEqualTo(DEFAULT_DIMENSAO);
        assertThat(testInstituicaoEnsino.getCarimbo()).isEqualTo(DEFAULT_CARIMBO);
        assertThat(testInstituicaoEnsino.getCarimboContentType()).isEqualTo(DEFAULT_CARIMBO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.isSede()).isEqualTo(DEFAULT_SEDE);

        // Validate the InstituicaoEnsino in Elasticsearch
        verify(mockInstituicaoEnsinoSearchRepository, times(1)).save(testInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void createInstituicaoEnsinoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instituicaoEnsinoRepository.findAll().size();

        // Create the InstituicaoEnsino with an existing ID
        instituicaoEnsino.setId(1L);
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituicaoEnsinoMockMvc.perform(post("/api/instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeCreate);

        // Validate the InstituicaoEnsino in Elasticsearch
        verify(mockInstituicaoEnsinoSearchRepository, times(0)).save(instituicaoEnsino);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoEnsinoRepository.findAll().size();
        // set the field null
        instituicaoEnsino.setNome(null);

        // Create the InstituicaoEnsino, which fails.
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        restInstituicaoEnsinoMockMvc.perform(post("/api/instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSedeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoEnsinoRepository.findAll().size();
        // set the field null
        instituicaoEnsino.setSede(null);

        // Create the InstituicaoEnsino, which fails.
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        restInstituicaoEnsinoMockMvc.perform(post("/api/instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituicaoEnsinos() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get all the instituicaoEnsinoList
        restInstituicaoEnsinoMockMvc.perform(get("/api/instituicao-ensinos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))))
            .andExpect(jsonPath("$.[*].fundacao").value(hasItem(DEFAULT_FUNDACAO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].tipoVinculo").value(hasItem(DEFAULT_TIPO_VINCULO)))
            .andExpect(jsonPath("$.[*].unidadePagadora").value(hasItem(DEFAULT_UNIDADE_PAGADORA)))
            .andExpect(jsonPath("$.[*].unidadeOrganica").value(hasItem(DEFAULT_UNIDADE_ORGANICA)))
            .andExpect(jsonPath("$.[*].tipoInstalacao").value(hasItem(DEFAULT_TIPO_INSTALACAO)))
            .andExpect(jsonPath("$.[*].dimensao").value(hasItem(DEFAULT_DIMENSAO)))
            .andExpect(jsonPath("$.[*].carimboContentType").value(hasItem(DEFAULT_CARIMBO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].carimbo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CARIMBO))))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInstituicaoEnsino() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        // Get the instituicaoEnsino
        restInstituicaoEnsinoMockMvc.perform(get("/api/instituicao-ensinos/{id}", instituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instituicaoEnsino.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.logotipoContentType").value(DEFAULT_LOGOTIPO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logotipo").value(Base64Utils.encodeToString(DEFAULT_LOGOTIPO)))
            .andExpect(jsonPath("$.fundacao").value(DEFAULT_FUNDACAO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.tipoVinculo").value(DEFAULT_TIPO_VINCULO))
            .andExpect(jsonPath("$.unidadePagadora").value(DEFAULT_UNIDADE_PAGADORA))
            .andExpect(jsonPath("$.unidadeOrganica").value(DEFAULT_UNIDADE_ORGANICA))
            .andExpect(jsonPath("$.tipoInstalacao").value(DEFAULT_TIPO_INSTALACAO))
            .andExpect(jsonPath("$.dimensao").value(DEFAULT_DIMENSAO))
            .andExpect(jsonPath("$.carimboContentType").value(DEFAULT_CARIMBO_CONTENT_TYPE))
            .andExpect(jsonPath("$.carimbo").value(Base64Utils.encodeToString(DEFAULT_CARIMBO)))
            .andExpect(jsonPath("$.sede").value(DEFAULT_SEDE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstituicaoEnsino() throws Exception {
        // Get the instituicaoEnsino
        restInstituicaoEnsinoMockMvc.perform(get("/api/instituicao-ensinos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituicaoEnsino() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();

        // Update the instituicaoEnsino
        InstituicaoEnsino updatedInstituicaoEnsino = instituicaoEnsinoRepository.findById(instituicaoEnsino.getId()).get();
        // Disconnect from session so that the updates on updatedInstituicaoEnsino are not directly saved in db
        em.detach(updatedInstituicaoEnsino);
        updatedInstituicaoEnsino
            .nome(UPDATED_NOME)
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .fundacao(UPDATED_FUNDACAO)
            .numero(UPDATED_NUMERO)
            .tipoVinculo(UPDATED_TIPO_VINCULO)
            .unidadePagadora(UPDATED_UNIDADE_PAGADORA)
            .unidadeOrganica(UPDATED_UNIDADE_ORGANICA)
            .tipoInstalacao(UPDATED_TIPO_INSTALACAO)
            .dimensao(UPDATED_DIMENSAO)
            .carimbo(UPDATED_CARIMBO)
            .carimboContentType(UPDATED_CARIMBO_CONTENT_TYPE)
            .sede(UPDATED_SEDE);
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(updatedInstituicaoEnsino);

        restInstituicaoEnsinoMockMvc.perform(put("/api/instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO)))
            .andExpect(status().isOk());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
        InstituicaoEnsino testInstituicaoEnsino = instituicaoEnsinoList.get(instituicaoEnsinoList.size() - 1);
        assertThat(testInstituicaoEnsino.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInstituicaoEnsino.getLogotipo()).isEqualTo(UPDATED_LOGOTIPO);
        assertThat(testInstituicaoEnsino.getLogotipoContentType()).isEqualTo(UPDATED_LOGOTIPO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.getFundacao()).isEqualTo(UPDATED_FUNDACAO);
        assertThat(testInstituicaoEnsino.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testInstituicaoEnsino.getTipoVinculo()).isEqualTo(UPDATED_TIPO_VINCULO);
        assertThat(testInstituicaoEnsino.getUnidadePagadora()).isEqualTo(UPDATED_UNIDADE_PAGADORA);
        assertThat(testInstituicaoEnsino.getUnidadeOrganica()).isEqualTo(UPDATED_UNIDADE_ORGANICA);
        assertThat(testInstituicaoEnsino.getTipoInstalacao()).isEqualTo(UPDATED_TIPO_INSTALACAO);
        assertThat(testInstituicaoEnsino.getDimensao()).isEqualTo(UPDATED_DIMENSAO);
        assertThat(testInstituicaoEnsino.getCarimbo()).isEqualTo(UPDATED_CARIMBO);
        assertThat(testInstituicaoEnsino.getCarimboContentType()).isEqualTo(UPDATED_CARIMBO_CONTENT_TYPE);
        assertThat(testInstituicaoEnsino.isSede()).isEqualTo(UPDATED_SEDE);

        // Validate the InstituicaoEnsino in Elasticsearch
        verify(mockInstituicaoEnsinoSearchRepository, times(1)).save(testInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void updateNonExistingInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoEnsinoRepository.findAll().size();

        // Create the InstituicaoEnsino
        InstituicaoEnsinoDTO instituicaoEnsinoDTO = instituicaoEnsinoMapper.toDto(instituicaoEnsino);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituicaoEnsinoMockMvc.perform(put("/api/instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstituicaoEnsino in the database
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InstituicaoEnsino in Elasticsearch
        verify(mockInstituicaoEnsinoSearchRepository, times(0)).save(instituicaoEnsino);
    }

    @Test
    @Transactional
    public void deleteInstituicaoEnsino() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);

        int databaseSizeBeforeDelete = instituicaoEnsinoRepository.findAll().size();

        // Delete the instituicaoEnsino
        restInstituicaoEnsinoMockMvc.perform(delete("/api/instituicao-ensinos/{id}", instituicaoEnsino.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstituicaoEnsino> instituicaoEnsinoList = instituicaoEnsinoRepository.findAll();
        assertThat(instituicaoEnsinoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InstituicaoEnsino in Elasticsearch
        verify(mockInstituicaoEnsinoSearchRepository, times(1)).deleteById(instituicaoEnsino.getId());
    }

    @Test
    @Transactional
    public void searchInstituicaoEnsino() throws Exception {
        // Initialize the database
        instituicaoEnsinoRepository.saveAndFlush(instituicaoEnsino);
        when(mockInstituicaoEnsinoSearchRepository.search(queryStringQuery("id:" + instituicaoEnsino.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(instituicaoEnsino), PageRequest.of(0, 1), 1));
        // Search the instituicaoEnsino
        restInstituicaoEnsinoMockMvc.perform(get("/api/_search/instituicao-ensinos?query=id:" + instituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))))
            .andExpect(jsonPath("$.[*].fundacao").value(hasItem(DEFAULT_FUNDACAO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].tipoVinculo").value(hasItem(DEFAULT_TIPO_VINCULO)))
            .andExpect(jsonPath("$.[*].unidadePagadora").value(hasItem(DEFAULT_UNIDADE_PAGADORA)))
            .andExpect(jsonPath("$.[*].unidadeOrganica").value(hasItem(DEFAULT_UNIDADE_ORGANICA)))
            .andExpect(jsonPath("$.[*].tipoInstalacao").value(hasItem(DEFAULT_TIPO_INSTALACAO)))
            .andExpect(jsonPath("$.[*].dimensao").value(hasItem(DEFAULT_DIMENSAO)))
            .andExpect(jsonPath("$.[*].carimboContentType").value(hasItem(DEFAULT_CARIMBO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].carimbo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CARIMBO))))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.booleanValue())));
    }
}
