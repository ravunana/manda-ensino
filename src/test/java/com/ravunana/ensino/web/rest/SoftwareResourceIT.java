package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Software;
import com.ravunana.ensino.repository.SoftwareRepository;
import com.ravunana.ensino.repository.search.SoftwareSearchRepository;
import com.ravunana.ensino.service.SoftwareService;
import com.ravunana.ensino.service.dto.SoftwareDTO;
import com.ravunana.ensino.service.mapper.SoftwareMapper;
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
 * Integration tests for the {@link SoftwareResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class SoftwareResourceIT {

    private static final String DEFAULT_INSTITUICAO_ENSINO = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUICAO_ENSINO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SISTEMA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SISTEMA = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_VALIDACAO_AGT = 1;
    private static final Integer UPDATED_NUMERO_VALIDACAO_AGT = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSAO = "AAAAAAAAAA";
    private static final String UPDATED_VERSAO = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_CONTROL = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CONTROL = "BBBBBBBBBB";

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private SoftwareService softwareService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.SoftwareSearchRepositoryMockConfiguration
     */
    @Autowired
    private SoftwareSearchRepository mockSoftwareSearchRepository;

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

    private MockMvc restSoftwareMockMvc;

    private Software software;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SoftwareResource softwareResource = new SoftwareResource(softwareService);
        this.restSoftwareMockMvc = MockMvcBuilders.standaloneSetup(softwareResource)
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
    public static Software createEntity(EntityManager em) {
        Software software = new Software()
            .instituicaoEnsino(DEFAULT_INSTITUICAO_ENSINO)
            .tipoSistema(DEFAULT_TIPO_SISTEMA)
            .nif(DEFAULT_NIF)
            .numeroValidacaoAGT(DEFAULT_NUMERO_VALIDACAO_AGT)
            .nome(DEFAULT_NOME)
            .versao(DEFAULT_VERSAO)
            .hashCode(DEFAULT_HASH_CODE)
            .hashControl(DEFAULT_HASH_CONTROL);
        return software;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Software createUpdatedEntity(EntityManager em) {
        Software software = new Software()
            .instituicaoEnsino(UPDATED_INSTITUICAO_ENSINO)
            .tipoSistema(UPDATED_TIPO_SISTEMA)
            .nif(UPDATED_NIF)
            .numeroValidacaoAGT(UPDATED_NUMERO_VALIDACAO_AGT)
            .nome(UPDATED_NOME)
            .versao(UPDATED_VERSAO)
            .hashCode(UPDATED_HASH_CODE)
            .hashControl(UPDATED_HASH_CONTROL);
        return software;
    }

    @BeforeEach
    public void initTest() {
        software = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoftware() throws Exception {
        int databaseSizeBeforeCreate = softwareRepository.findAll().size();

        // Create the Software
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);
        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isCreated());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeCreate + 1);
        Software testSoftware = softwareList.get(softwareList.size() - 1);
        assertThat(testSoftware.getInstituicaoEnsino()).isEqualTo(DEFAULT_INSTITUICAO_ENSINO);
        assertThat(testSoftware.getTipoSistema()).isEqualTo(DEFAULT_TIPO_SISTEMA);
        assertThat(testSoftware.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testSoftware.getNumeroValidacaoAGT()).isEqualTo(DEFAULT_NUMERO_VALIDACAO_AGT);
        assertThat(testSoftware.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSoftware.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testSoftware.getHashCode()).isEqualTo(DEFAULT_HASH_CODE);
        assertThat(testSoftware.getHashControl()).isEqualTo(DEFAULT_HASH_CONTROL);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(1)).save(testSoftware);
    }

    @Test
    @Transactional
    public void createSoftwareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = softwareRepository.findAll().size();

        // Create the Software with an existing ID
        software.setId(1L);
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeCreate);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(0)).save(software);
    }


    @Test
    @Transactional
    public void checkInstituicaoEnsinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setInstituicaoEnsino(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoSistemaIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setTipoSistema(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNifIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setNif(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroValidacaoAGTIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setNumeroValidacaoAGT(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setNome(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setVersao(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHashCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setHashCode(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHashControlIsRequired() throws Exception {
        int databaseSizeBeforeTest = softwareRepository.findAll().size();
        // set the field null
        software.setHashControl(null);

        // Create the Software, which fails.
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        restSoftwareMockMvc.perform(post("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSoftwares() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList
        restSoftwareMockMvc.perform(get("/api/softwares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(software.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituicaoEnsino").value(hasItem(DEFAULT_INSTITUICAO_ENSINO)))
            .andExpect(jsonPath("$.[*].tipoSistema").value(hasItem(DEFAULT_TIPO_SISTEMA)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].numeroValidacaoAGT").value(hasItem(DEFAULT_NUMERO_VALIDACAO_AGT)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO)))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)));
    }
    
    @Test
    @Transactional
    public void getSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get the software
        restSoftwareMockMvc.perform(get("/api/softwares/{id}", software.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(software.getId().intValue()))
            .andExpect(jsonPath("$.instituicaoEnsino").value(DEFAULT_INSTITUICAO_ENSINO))
            .andExpect(jsonPath("$.tipoSistema").value(DEFAULT_TIPO_SISTEMA))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.numeroValidacaoAGT").value(DEFAULT_NUMERO_VALIDACAO_AGT))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO))
            .andExpect(jsonPath("$.hashCode").value(DEFAULT_HASH_CODE))
            .andExpect(jsonPath("$.hashControl").value(DEFAULT_HASH_CONTROL));
    }

    @Test
    @Transactional
    public void getNonExistingSoftware() throws Exception {
        // Get the software
        restSoftwareMockMvc.perform(get("/api/softwares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        int databaseSizeBeforeUpdate = softwareRepository.findAll().size();

        // Update the software
        Software updatedSoftware = softwareRepository.findById(software.getId()).get();
        // Disconnect from session so that the updates on updatedSoftware are not directly saved in db
        em.detach(updatedSoftware);
        updatedSoftware
            .instituicaoEnsino(UPDATED_INSTITUICAO_ENSINO)
            .tipoSistema(UPDATED_TIPO_SISTEMA)
            .nif(UPDATED_NIF)
            .numeroValidacaoAGT(UPDATED_NUMERO_VALIDACAO_AGT)
            .nome(UPDATED_NOME)
            .versao(UPDATED_VERSAO)
            .hashCode(UPDATED_HASH_CODE)
            .hashControl(UPDATED_HASH_CONTROL);
        SoftwareDTO softwareDTO = softwareMapper.toDto(updatedSoftware);

        restSoftwareMockMvc.perform(put("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isOk());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeUpdate);
        Software testSoftware = softwareList.get(softwareList.size() - 1);
        assertThat(testSoftware.getInstituicaoEnsino()).isEqualTo(UPDATED_INSTITUICAO_ENSINO);
        assertThat(testSoftware.getTipoSistema()).isEqualTo(UPDATED_TIPO_SISTEMA);
        assertThat(testSoftware.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testSoftware.getNumeroValidacaoAGT()).isEqualTo(UPDATED_NUMERO_VALIDACAO_AGT);
        assertThat(testSoftware.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSoftware.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testSoftware.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testSoftware.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(1)).save(testSoftware);
    }

    @Test
    @Transactional
    public void updateNonExistingSoftware() throws Exception {
        int databaseSizeBeforeUpdate = softwareRepository.findAll().size();

        // Create the Software
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoftwareMockMvc.perform(put("/api/softwares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(0)).save(software);
    }

    @Test
    @Transactional
    public void deleteSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        int databaseSizeBeforeDelete = softwareRepository.findAll().size();

        // Delete the software
        restSoftwareMockMvc.perform(delete("/api/softwares/{id}", software.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(1)).deleteById(software.getId());
    }

    @Test
    @Transactional
    public void searchSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);
        when(mockSoftwareSearchRepository.search(queryStringQuery("id:" + software.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(software), PageRequest.of(0, 1), 1));
        // Search the software
        restSoftwareMockMvc.perform(get("/api/_search/softwares?query=id:" + software.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(software.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituicaoEnsino").value(hasItem(DEFAULT_INSTITUICAO_ENSINO)))
            .andExpect(jsonPath("$.[*].tipoSistema").value(hasItem(DEFAULT_TIPO_SISTEMA)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].numeroValidacaoAGT").value(hasItem(DEFAULT_NUMERO_VALIDACAO_AGT)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO)))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)));
    }
}
