package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.LicencaSoftware;
import com.ravunana.ensino.domain.InstituicaoEnsino;
import com.ravunana.ensino.repository.LicencaSoftwareRepository;
import com.ravunana.ensino.repository.search.LicencaSoftwareSearchRepository;
import com.ravunana.ensino.service.LicencaSoftwareService;
import com.ravunana.ensino.service.dto.LicencaSoftwareDTO;
import com.ravunana.ensino.service.mapper.LicencaSoftwareMapper;
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
 * Integration tests for the {@link LicencaSoftwareResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class LicencaSoftwareResourceIT {

    private static final String DEFAULT_TIPO_SUBSCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SUBSCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FIM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FIM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_USUARIO = 1;
    private static final Integer UPDATED_NUMERO_USUARIO = 2;

    private static final Integer DEFAULT_NUMERO_INSTITUICAO_ENSINO = 1;
    private static final Integer UPDATED_NUMERO_INSTITUICAO_ENSINO = 2;

    @Autowired
    private LicencaSoftwareRepository licencaSoftwareRepository;

    @Autowired
    private LicencaSoftwareMapper licencaSoftwareMapper;

    @Autowired
    private LicencaSoftwareService licencaSoftwareService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.LicencaSoftwareSearchRepositoryMockConfiguration
     */
    @Autowired
    private LicencaSoftwareSearchRepository mockLicencaSoftwareSearchRepository;

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

    private MockMvc restLicencaSoftwareMockMvc;

    private LicencaSoftware licencaSoftware;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LicencaSoftwareResource licencaSoftwareResource = new LicencaSoftwareResource(licencaSoftwareService);
        this.restLicencaSoftwareMockMvc = MockMvcBuilders.standaloneSetup(licencaSoftwareResource)
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
    public static LicencaSoftware createEntity(EntityManager em) {
        LicencaSoftware licencaSoftware = new LicencaSoftware()
            .tipoSubscricao(DEFAULT_TIPO_SUBSCRICAO)
            .inicio(DEFAULT_INICIO)
            .fim(DEFAULT_FIM)
            .data(DEFAULT_DATA)
            .valor(DEFAULT_VALOR)
            .codigo(DEFAULT_CODIGO)
            .numeroUsuario(DEFAULT_NUMERO_USUARIO)
            .numeroInstituicaoEnsino(DEFAULT_NUMERO_INSTITUICAO_ENSINO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        licencaSoftware.setInstituicaoEnsino(instituicaoEnsino);
        return licencaSoftware;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicencaSoftware createUpdatedEntity(EntityManager em) {
        LicencaSoftware licencaSoftware = new LicencaSoftware()
            .tipoSubscricao(UPDATED_TIPO_SUBSCRICAO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .data(UPDATED_DATA)
            .valor(UPDATED_VALOR)
            .codigo(UPDATED_CODIGO)
            .numeroUsuario(UPDATED_NUMERO_USUARIO)
            .numeroInstituicaoEnsino(UPDATED_NUMERO_INSTITUICAO_ENSINO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createUpdatedEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        licencaSoftware.setInstituicaoEnsino(instituicaoEnsino);
        return licencaSoftware;
    }

    @BeforeEach
    public void initTest() {
        licencaSoftware = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicencaSoftware() throws Exception {
        int databaseSizeBeforeCreate = licencaSoftwareRepository.findAll().size();

        // Create the LicencaSoftware
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);
        restLicencaSoftwareMockMvc.perform(post("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isCreated());

        // Validate the LicencaSoftware in the database
        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeCreate + 1);
        LicencaSoftware testLicencaSoftware = licencaSoftwareList.get(licencaSoftwareList.size() - 1);
        assertThat(testLicencaSoftware.getTipoSubscricao()).isEqualTo(DEFAULT_TIPO_SUBSCRICAO);
        assertThat(testLicencaSoftware.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testLicencaSoftware.getFim()).isEqualTo(DEFAULT_FIM);
        assertThat(testLicencaSoftware.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testLicencaSoftware.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testLicencaSoftware.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLicencaSoftware.getNumeroUsuario()).isEqualTo(DEFAULT_NUMERO_USUARIO);
        assertThat(testLicencaSoftware.getNumeroInstituicaoEnsino()).isEqualTo(DEFAULT_NUMERO_INSTITUICAO_ENSINO);

        // Validate the LicencaSoftware in Elasticsearch
        verify(mockLicencaSoftwareSearchRepository, times(1)).save(testLicencaSoftware);
    }

    @Test
    @Transactional
    public void createLicencaSoftwareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licencaSoftwareRepository.findAll().size();

        // Create the LicencaSoftware with an existing ID
        licencaSoftware.setId(1L);
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicencaSoftwareMockMvc.perform(post("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LicencaSoftware in the database
        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeCreate);

        // Validate the LicencaSoftware in Elasticsearch
        verify(mockLicencaSoftwareSearchRepository, times(0)).save(licencaSoftware);
    }


    @Test
    @Transactional
    public void checkTipoSubscricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = licencaSoftwareRepository.findAll().size();
        // set the field null
        licencaSoftware.setTipoSubscricao(null);

        // Create the LicencaSoftware, which fails.
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);

        restLicencaSoftwareMockMvc.perform(post("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isBadRequest());

        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = licencaSoftwareRepository.findAll().size();
        // set the field null
        licencaSoftware.setInicio(null);

        // Create the LicencaSoftware, which fails.
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);

        restLicencaSoftwareMockMvc.perform(post("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isBadRequest());

        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = licencaSoftwareRepository.findAll().size();
        // set the field null
        licencaSoftware.setFim(null);

        // Create the LicencaSoftware, which fails.
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);

        restLicencaSoftwareMockMvc.perform(post("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isBadRequest());

        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = licencaSoftwareRepository.findAll().size();
        // set the field null
        licencaSoftware.setValor(null);

        // Create the LicencaSoftware, which fails.
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);

        restLicencaSoftwareMockMvc.perform(post("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isBadRequest());

        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = licencaSoftwareRepository.findAll().size();
        // set the field null
        licencaSoftware.setCodigo(null);

        // Create the LicencaSoftware, which fails.
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);

        restLicencaSoftwareMockMvc.perform(post("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isBadRequest());

        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLicencaSoftwares() throws Exception {
        // Initialize the database
        licencaSoftwareRepository.saveAndFlush(licencaSoftware);

        // Get all the licencaSoftwareList
        restLicencaSoftwareMockMvc.perform(get("/api/licenca-softwares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licencaSoftware.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoSubscricao").value(hasItem(DEFAULT_TIPO_SUBSCRICAO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(sameInstant(DEFAULT_INICIO))))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(sameInstant(DEFAULT_FIM))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].numeroUsuario").value(hasItem(DEFAULT_NUMERO_USUARIO)))
            .andExpect(jsonPath("$.[*].numeroInstituicaoEnsino").value(hasItem(DEFAULT_NUMERO_INSTITUICAO_ENSINO)));
    }
    
    @Test
    @Transactional
    public void getLicencaSoftware() throws Exception {
        // Initialize the database
        licencaSoftwareRepository.saveAndFlush(licencaSoftware);

        // Get the licencaSoftware
        restLicencaSoftwareMockMvc.perform(get("/api/licenca-softwares/{id}", licencaSoftware.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(licencaSoftware.getId().intValue()))
            .andExpect(jsonPath("$.tipoSubscricao").value(DEFAULT_TIPO_SUBSCRICAO))
            .andExpect(jsonPath("$.inicio").value(sameInstant(DEFAULT_INICIO)))
            .andExpect(jsonPath("$.fim").value(sameInstant(DEFAULT_FIM)))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.numeroUsuario").value(DEFAULT_NUMERO_USUARIO))
            .andExpect(jsonPath("$.numeroInstituicaoEnsino").value(DEFAULT_NUMERO_INSTITUICAO_ENSINO));
    }

    @Test
    @Transactional
    public void getNonExistingLicencaSoftware() throws Exception {
        // Get the licencaSoftware
        restLicencaSoftwareMockMvc.perform(get("/api/licenca-softwares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicencaSoftware() throws Exception {
        // Initialize the database
        licencaSoftwareRepository.saveAndFlush(licencaSoftware);

        int databaseSizeBeforeUpdate = licencaSoftwareRepository.findAll().size();

        // Update the licencaSoftware
        LicencaSoftware updatedLicencaSoftware = licencaSoftwareRepository.findById(licencaSoftware.getId()).get();
        // Disconnect from session so that the updates on updatedLicencaSoftware are not directly saved in db
        em.detach(updatedLicencaSoftware);
        updatedLicencaSoftware
            .tipoSubscricao(UPDATED_TIPO_SUBSCRICAO)
            .inicio(UPDATED_INICIO)
            .fim(UPDATED_FIM)
            .data(UPDATED_DATA)
            .valor(UPDATED_VALOR)
            .codigo(UPDATED_CODIGO)
            .numeroUsuario(UPDATED_NUMERO_USUARIO)
            .numeroInstituicaoEnsino(UPDATED_NUMERO_INSTITUICAO_ENSINO);
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(updatedLicencaSoftware);

        restLicencaSoftwareMockMvc.perform(put("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isOk());

        // Validate the LicencaSoftware in the database
        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeUpdate);
        LicencaSoftware testLicencaSoftware = licencaSoftwareList.get(licencaSoftwareList.size() - 1);
        assertThat(testLicencaSoftware.getTipoSubscricao()).isEqualTo(UPDATED_TIPO_SUBSCRICAO);
        assertThat(testLicencaSoftware.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testLicencaSoftware.getFim()).isEqualTo(UPDATED_FIM);
        assertThat(testLicencaSoftware.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testLicencaSoftware.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testLicencaSoftware.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLicencaSoftware.getNumeroUsuario()).isEqualTo(UPDATED_NUMERO_USUARIO);
        assertThat(testLicencaSoftware.getNumeroInstituicaoEnsino()).isEqualTo(UPDATED_NUMERO_INSTITUICAO_ENSINO);

        // Validate the LicencaSoftware in Elasticsearch
        verify(mockLicencaSoftwareSearchRepository, times(1)).save(testLicencaSoftware);
    }

    @Test
    @Transactional
    public void updateNonExistingLicencaSoftware() throws Exception {
        int databaseSizeBeforeUpdate = licencaSoftwareRepository.findAll().size();

        // Create the LicencaSoftware
        LicencaSoftwareDTO licencaSoftwareDTO = licencaSoftwareMapper.toDto(licencaSoftware);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicencaSoftwareMockMvc.perform(put("/api/licenca-softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licencaSoftwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LicencaSoftware in the database
        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LicencaSoftware in Elasticsearch
        verify(mockLicencaSoftwareSearchRepository, times(0)).save(licencaSoftware);
    }

    @Test
    @Transactional
    public void deleteLicencaSoftware() throws Exception {
        // Initialize the database
        licencaSoftwareRepository.saveAndFlush(licencaSoftware);

        int databaseSizeBeforeDelete = licencaSoftwareRepository.findAll().size();

        // Delete the licencaSoftware
        restLicencaSoftwareMockMvc.perform(delete("/api/licenca-softwares/{id}", licencaSoftware.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LicencaSoftware> licencaSoftwareList = licencaSoftwareRepository.findAll();
        assertThat(licencaSoftwareList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LicencaSoftware in Elasticsearch
        verify(mockLicencaSoftwareSearchRepository, times(1)).deleteById(licencaSoftware.getId());
    }

    @Test
    @Transactional
    public void searchLicencaSoftware() throws Exception {
        // Initialize the database
        licencaSoftwareRepository.saveAndFlush(licencaSoftware);
        when(mockLicencaSoftwareSearchRepository.search(queryStringQuery("id:" + licencaSoftware.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(licencaSoftware), PageRequest.of(0, 1), 1));
        // Search the licencaSoftware
        restLicencaSoftwareMockMvc.perform(get("/api/_search/licenca-softwares?query=id:" + licencaSoftware.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licencaSoftware.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoSubscricao").value(hasItem(DEFAULT_TIPO_SUBSCRICAO)))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(sameInstant(DEFAULT_INICIO))))
            .andExpect(jsonPath("$.[*].fim").value(hasItem(sameInstant(DEFAULT_FIM))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].numeroUsuario").value(hasItem(DEFAULT_NUMERO_USUARIO)))
            .andExpect(jsonPath("$.[*].numeroInstituicaoEnsino").value(hasItem(DEFAULT_NUMERO_INSTITUICAO_ENSINO)));
    }
}
