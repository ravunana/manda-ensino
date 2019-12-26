package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.AssinaturaDigital;
import com.ravunana.ensino.domain.InstituicaoEnsino;
import com.ravunana.ensino.repository.AssinaturaDigitalRepository;
import com.ravunana.ensino.repository.search.AssinaturaDigitalSearchRepository;
import com.ravunana.ensino.service.AssinaturaDigitalService;
import com.ravunana.ensino.service.dto.AssinaturaDigitalDTO;
import com.ravunana.ensino.service.mapper.AssinaturaDigitalMapper;
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
 * Integration tests for the {@link AssinaturaDigitalResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class AssinaturaDigitalResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ASSINATURA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSINATURA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSINATURA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSINATURA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_HASHCODE = "AAAAAAAAAA";
    private static final String UPDATED_HASHCODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AssinaturaDigitalRepository assinaturaDigitalRepository;

    @Autowired
    private AssinaturaDigitalMapper assinaturaDigitalMapper;

    @Autowired
    private AssinaturaDigitalService assinaturaDigitalService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.AssinaturaDigitalSearchRepositoryMockConfiguration
     */
    @Autowired
    private AssinaturaDigitalSearchRepository mockAssinaturaDigitalSearchRepository;

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

    private MockMvc restAssinaturaDigitalMockMvc;

    private AssinaturaDigital assinaturaDigital;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssinaturaDigitalResource assinaturaDigitalResource = new AssinaturaDigitalResource(assinaturaDigitalService);
        this.restAssinaturaDigitalMockMvc = MockMvcBuilders.standaloneSetup(assinaturaDigitalResource)
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
    public static AssinaturaDigital createEntity(EntityManager em) {
        AssinaturaDigital assinaturaDigital = new AssinaturaDigital()
            .tipo(DEFAULT_TIPO)
            .assinatura(DEFAULT_ASSINATURA)
            .assinaturaContentType(DEFAULT_ASSINATURA_CONTENT_TYPE)
            .hashcode(DEFAULT_HASHCODE)
            .data(DEFAULT_DATA);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        assinaturaDigital.setInstituicao(instituicaoEnsino);
        return assinaturaDigital;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssinaturaDigital createUpdatedEntity(EntityManager em) {
        AssinaturaDigital assinaturaDigital = new AssinaturaDigital()
            .tipo(UPDATED_TIPO)
            .assinatura(UPDATED_ASSINATURA)
            .assinaturaContentType(UPDATED_ASSINATURA_CONTENT_TYPE)
            .hashcode(UPDATED_HASHCODE)
            .data(UPDATED_DATA);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createUpdatedEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        assinaturaDigital.setInstituicao(instituicaoEnsino);
        return assinaturaDigital;
    }

    @BeforeEach
    public void initTest() {
        assinaturaDigital = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssinaturaDigital() throws Exception {
        int databaseSizeBeforeCreate = assinaturaDigitalRepository.findAll().size();

        // Create the AssinaturaDigital
        AssinaturaDigitalDTO assinaturaDigitalDTO = assinaturaDigitalMapper.toDto(assinaturaDigital);
        restAssinaturaDigitalMockMvc.perform(post("/api/assinatura-digitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assinaturaDigitalDTO)))
            .andExpect(status().isCreated());

        // Validate the AssinaturaDigital in the database
        List<AssinaturaDigital> assinaturaDigitalList = assinaturaDigitalRepository.findAll();
        assertThat(assinaturaDigitalList).hasSize(databaseSizeBeforeCreate + 1);
        AssinaturaDigital testAssinaturaDigital = assinaturaDigitalList.get(assinaturaDigitalList.size() - 1);
        assertThat(testAssinaturaDigital.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAssinaturaDigital.getAssinatura()).isEqualTo(DEFAULT_ASSINATURA);
        assertThat(testAssinaturaDigital.getAssinaturaContentType()).isEqualTo(DEFAULT_ASSINATURA_CONTENT_TYPE);
        assertThat(testAssinaturaDigital.getHashcode()).isEqualTo(DEFAULT_HASHCODE);
        assertThat(testAssinaturaDigital.getData()).isEqualTo(DEFAULT_DATA);

        // Validate the AssinaturaDigital in Elasticsearch
        verify(mockAssinaturaDigitalSearchRepository, times(1)).save(testAssinaturaDigital);
    }

    @Test
    @Transactional
    public void createAssinaturaDigitalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assinaturaDigitalRepository.findAll().size();

        // Create the AssinaturaDigital with an existing ID
        assinaturaDigital.setId(1L);
        AssinaturaDigitalDTO assinaturaDigitalDTO = assinaturaDigitalMapper.toDto(assinaturaDigital);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssinaturaDigitalMockMvc.perform(post("/api/assinatura-digitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assinaturaDigitalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaDigital in the database
        List<AssinaturaDigital> assinaturaDigitalList = assinaturaDigitalRepository.findAll();
        assertThat(assinaturaDigitalList).hasSize(databaseSizeBeforeCreate);

        // Validate the AssinaturaDigital in Elasticsearch
        verify(mockAssinaturaDigitalSearchRepository, times(0)).save(assinaturaDigital);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = assinaturaDigitalRepository.findAll().size();
        // set the field null
        assinaturaDigital.setTipo(null);

        // Create the AssinaturaDigital, which fails.
        AssinaturaDigitalDTO assinaturaDigitalDTO = assinaturaDigitalMapper.toDto(assinaturaDigital);

        restAssinaturaDigitalMockMvc.perform(post("/api/assinatura-digitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assinaturaDigitalDTO)))
            .andExpect(status().isBadRequest());

        List<AssinaturaDigital> assinaturaDigitalList = assinaturaDigitalRepository.findAll();
        assertThat(assinaturaDigitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssinaturaDigitals() throws Exception {
        // Initialize the database
        assinaturaDigitalRepository.saveAndFlush(assinaturaDigital);

        // Get all the assinaturaDigitalList
        restAssinaturaDigitalMockMvc.perform(get("/api/assinatura-digitals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assinaturaDigital.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].assinaturaContentType").value(hasItem(DEFAULT_ASSINATURA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assinatura").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSINATURA))))
            .andExpect(jsonPath("$.[*].hashcode").value(hasItem(DEFAULT_HASHCODE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getAssinaturaDigital() throws Exception {
        // Initialize the database
        assinaturaDigitalRepository.saveAndFlush(assinaturaDigital);

        // Get the assinaturaDigital
        restAssinaturaDigitalMockMvc.perform(get("/api/assinatura-digitals/{id}", assinaturaDigital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assinaturaDigital.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.assinaturaContentType").value(DEFAULT_ASSINATURA_CONTENT_TYPE))
            .andExpect(jsonPath("$.assinatura").value(Base64Utils.encodeToString(DEFAULT_ASSINATURA)))
            .andExpect(jsonPath("$.hashcode").value(DEFAULT_HASHCODE))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingAssinaturaDigital() throws Exception {
        // Get the assinaturaDigital
        restAssinaturaDigitalMockMvc.perform(get("/api/assinatura-digitals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssinaturaDigital() throws Exception {
        // Initialize the database
        assinaturaDigitalRepository.saveAndFlush(assinaturaDigital);

        int databaseSizeBeforeUpdate = assinaturaDigitalRepository.findAll().size();

        // Update the assinaturaDigital
        AssinaturaDigital updatedAssinaturaDigital = assinaturaDigitalRepository.findById(assinaturaDigital.getId()).get();
        // Disconnect from session so that the updates on updatedAssinaturaDigital are not directly saved in db
        em.detach(updatedAssinaturaDigital);
        updatedAssinaturaDigital
            .tipo(UPDATED_TIPO)
            .assinatura(UPDATED_ASSINATURA)
            .assinaturaContentType(UPDATED_ASSINATURA_CONTENT_TYPE)
            .hashcode(UPDATED_HASHCODE)
            .data(UPDATED_DATA);
        AssinaturaDigitalDTO assinaturaDigitalDTO = assinaturaDigitalMapper.toDto(updatedAssinaturaDigital);

        restAssinaturaDigitalMockMvc.perform(put("/api/assinatura-digitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assinaturaDigitalDTO)))
            .andExpect(status().isOk());

        // Validate the AssinaturaDigital in the database
        List<AssinaturaDigital> assinaturaDigitalList = assinaturaDigitalRepository.findAll();
        assertThat(assinaturaDigitalList).hasSize(databaseSizeBeforeUpdate);
        AssinaturaDigital testAssinaturaDigital = assinaturaDigitalList.get(assinaturaDigitalList.size() - 1);
        assertThat(testAssinaturaDigital.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAssinaturaDigital.getAssinatura()).isEqualTo(UPDATED_ASSINATURA);
        assertThat(testAssinaturaDigital.getAssinaturaContentType()).isEqualTo(UPDATED_ASSINATURA_CONTENT_TYPE);
        assertThat(testAssinaturaDigital.getHashcode()).isEqualTo(UPDATED_HASHCODE);
        assertThat(testAssinaturaDigital.getData()).isEqualTo(UPDATED_DATA);

        // Validate the AssinaturaDigital in Elasticsearch
        verify(mockAssinaturaDigitalSearchRepository, times(1)).save(testAssinaturaDigital);
    }

    @Test
    @Transactional
    public void updateNonExistingAssinaturaDigital() throws Exception {
        int databaseSizeBeforeUpdate = assinaturaDigitalRepository.findAll().size();

        // Create the AssinaturaDigital
        AssinaturaDigitalDTO assinaturaDigitalDTO = assinaturaDigitalMapper.toDto(assinaturaDigital);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssinaturaDigitalMockMvc.perform(put("/api/assinatura-digitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assinaturaDigitalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaDigital in the database
        List<AssinaturaDigital> assinaturaDigitalList = assinaturaDigitalRepository.findAll();
        assertThat(assinaturaDigitalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AssinaturaDigital in Elasticsearch
        verify(mockAssinaturaDigitalSearchRepository, times(0)).save(assinaturaDigital);
    }

    @Test
    @Transactional
    public void deleteAssinaturaDigital() throws Exception {
        // Initialize the database
        assinaturaDigitalRepository.saveAndFlush(assinaturaDigital);

        int databaseSizeBeforeDelete = assinaturaDigitalRepository.findAll().size();

        // Delete the assinaturaDigital
        restAssinaturaDigitalMockMvc.perform(delete("/api/assinatura-digitals/{id}", assinaturaDigital.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssinaturaDigital> assinaturaDigitalList = assinaturaDigitalRepository.findAll();
        assertThat(assinaturaDigitalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AssinaturaDigital in Elasticsearch
        verify(mockAssinaturaDigitalSearchRepository, times(1)).deleteById(assinaturaDigital.getId());
    }

    @Test
    @Transactional
    public void searchAssinaturaDigital() throws Exception {
        // Initialize the database
        assinaturaDigitalRepository.saveAndFlush(assinaturaDigital);
        when(mockAssinaturaDigitalSearchRepository.search(queryStringQuery("id:" + assinaturaDigital.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(assinaturaDigital), PageRequest.of(0, 1), 1));
        // Search the assinaturaDigital
        restAssinaturaDigitalMockMvc.perform(get("/api/_search/assinatura-digitals?query=id:" + assinaturaDigital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assinaturaDigital.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].assinaturaContentType").value(hasItem(DEFAULT_ASSINATURA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assinatura").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSINATURA))))
            .andExpect(jsonPath("$.[*].hashcode").value(hasItem(DEFAULT_HASHCODE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
}
