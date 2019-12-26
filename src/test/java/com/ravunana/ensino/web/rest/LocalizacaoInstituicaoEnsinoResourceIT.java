package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.LocalizacaoInstituicaoEnsino;
import com.ravunana.ensino.domain.InstituicaoEnsino;
import com.ravunana.ensino.repository.LocalizacaoInstituicaoEnsinoRepository;
import com.ravunana.ensino.repository.search.LocalizacaoInstituicaoEnsinoSearchRepository;
import com.ravunana.ensino.service.LocalizacaoInstituicaoEnsinoService;
import com.ravunana.ensino.service.dto.LocalizacaoInstituicaoEnsinoDTO;
import com.ravunana.ensino.service.mapper.LocalizacaoInstituicaoEnsinoMapper;
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
 * Integration tests for the {@link LocalizacaoInstituicaoEnsinoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class LocalizacaoInstituicaoEnsinoResourceIT {

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_RUA = "AAAAAAAAAA";
    private static final String UPDATED_RUA = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTEIRAO = "AAAAAAAAAA";
    private static final String UPDATED_QUARTEIRAO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PORTA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PORTA = "BBBBBBBBBB";

    private static final String DEFAULT_CAIXA_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CAIXA_POSTAL = "BBBBBBBBBB";

    @Autowired
    private LocalizacaoInstituicaoEnsinoRepository localizacaoInstituicaoEnsinoRepository;

    @Autowired
    private LocalizacaoInstituicaoEnsinoMapper localizacaoInstituicaoEnsinoMapper;

    @Autowired
    private LocalizacaoInstituicaoEnsinoService localizacaoInstituicaoEnsinoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.LocalizacaoInstituicaoEnsinoSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocalizacaoInstituicaoEnsinoSearchRepository mockLocalizacaoInstituicaoEnsinoSearchRepository;

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

    private MockMvc restLocalizacaoInstituicaoEnsinoMockMvc;

    private LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalizacaoInstituicaoEnsinoResource localizacaoInstituicaoEnsinoResource = new LocalizacaoInstituicaoEnsinoResource(localizacaoInstituicaoEnsinoService);
        this.restLocalizacaoInstituicaoEnsinoMockMvc = MockMvcBuilders.standaloneSetup(localizacaoInstituicaoEnsinoResource)
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
    public static LocalizacaoInstituicaoEnsino createEntity(EntityManager em) {
        LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino = new LocalizacaoInstituicaoEnsino()
            .provincia(DEFAULT_PROVINCIA)
            .municipio(DEFAULT_MUNICIPIO)
            .bairro(DEFAULT_BAIRRO)
            .rua(DEFAULT_RUA)
            .quarteirao(DEFAULT_QUARTEIRAO)
            .numeroPorta(DEFAULT_NUMERO_PORTA)
            .caixaPostal(DEFAULT_CAIXA_POSTAL);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        localizacaoInstituicaoEnsino.setInstituicaoEnsino(instituicaoEnsino);
        return localizacaoInstituicaoEnsino;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocalizacaoInstituicaoEnsino createUpdatedEntity(EntityManager em) {
        LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino = new LocalizacaoInstituicaoEnsino()
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA)
            .caixaPostal(UPDATED_CAIXA_POSTAL);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createUpdatedEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        localizacaoInstituicaoEnsino.setInstituicaoEnsino(instituicaoEnsino);
        return localizacaoInstituicaoEnsino;
    }

    @BeforeEach
    public void initTest() {
        localizacaoInstituicaoEnsino = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalizacaoInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeCreate = localizacaoInstituicaoEnsinoRepository.findAll().size();

        // Create the LocalizacaoInstituicaoEnsino
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(post("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalizacaoInstituicaoEnsino in the database
        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeCreate + 1);
        LocalizacaoInstituicaoEnsino testLocalizacaoInstituicaoEnsino = localizacaoInstituicaoEnsinoList.get(localizacaoInstituicaoEnsinoList.size() - 1);
        assertThat(testLocalizacaoInstituicaoEnsino.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testLocalizacaoInstituicaoEnsino.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testLocalizacaoInstituicaoEnsino.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testLocalizacaoInstituicaoEnsino.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testLocalizacaoInstituicaoEnsino.getQuarteirao()).isEqualTo(DEFAULT_QUARTEIRAO);
        assertThat(testLocalizacaoInstituicaoEnsino.getNumeroPorta()).isEqualTo(DEFAULT_NUMERO_PORTA);
        assertThat(testLocalizacaoInstituicaoEnsino.getCaixaPostal()).isEqualTo(DEFAULT_CAIXA_POSTAL);

        // Validate the LocalizacaoInstituicaoEnsino in Elasticsearch
        verify(mockLocalizacaoInstituicaoEnsinoSearchRepository, times(1)).save(testLocalizacaoInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void createLocalizacaoInstituicaoEnsinoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localizacaoInstituicaoEnsinoRepository.findAll().size();

        // Create the LocalizacaoInstituicaoEnsino with an existing ID
        localizacaoInstituicaoEnsino.setId(1L);
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(post("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalizacaoInstituicaoEnsino in the database
        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeCreate);

        // Validate the LocalizacaoInstituicaoEnsino in Elasticsearch
        verify(mockLocalizacaoInstituicaoEnsinoSearchRepository, times(0)).save(localizacaoInstituicaoEnsino);
    }


    @Test
    @Transactional
    public void checkProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizacaoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        localizacaoInstituicaoEnsino.setProvincia(null);

        // Create the LocalizacaoInstituicaoEnsino, which fails.
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);

        restLocalizacaoInstituicaoEnsinoMockMvc.perform(post("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMunicipioIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizacaoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        localizacaoInstituicaoEnsino.setMunicipio(null);

        // Create the LocalizacaoInstituicaoEnsino, which fails.
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);

        restLocalizacaoInstituicaoEnsinoMockMvc.perform(post("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizacaoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        localizacaoInstituicaoEnsino.setBairro(null);

        // Create the LocalizacaoInstituicaoEnsino, which fails.
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);

        restLocalizacaoInstituicaoEnsinoMockMvc.perform(post("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizacaoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        localizacaoInstituicaoEnsino.setRua(null);

        // Create the LocalizacaoInstituicaoEnsino, which fails.
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);

        restLocalizacaoInstituicaoEnsinoMockMvc.perform(post("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuarteiraoIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizacaoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        localizacaoInstituicaoEnsino.setQuarteirao(null);

        // Create the LocalizacaoInstituicaoEnsino, which fails.
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);

        restLocalizacaoInstituicaoEnsinoMockMvc.perform(post("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalizacaoInstituicaoEnsinos() throws Exception {
        // Initialize the database
        localizacaoInstituicaoEnsinoRepository.saveAndFlush(localizacaoInstituicaoEnsino);

        // Get all the localizacaoInstituicaoEnsinoList
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(get("/api/localizacao-instituicao-ensinos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localizacaoInstituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].quarteirao").value(hasItem(DEFAULT_QUARTEIRAO)))
            .andExpect(jsonPath("$.[*].numeroPorta").value(hasItem(DEFAULT_NUMERO_PORTA)))
            .andExpect(jsonPath("$.[*].caixaPostal").value(hasItem(DEFAULT_CAIXA_POSTAL)));
    }
    
    @Test
    @Transactional
    public void getLocalizacaoInstituicaoEnsino() throws Exception {
        // Initialize the database
        localizacaoInstituicaoEnsinoRepository.saveAndFlush(localizacaoInstituicaoEnsino);

        // Get the localizacaoInstituicaoEnsino
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(get("/api/localizacao-instituicao-ensinos/{id}", localizacaoInstituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localizacaoInstituicaoEnsino.getId().intValue()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA))
            .andExpect(jsonPath("$.quarteirao").value(DEFAULT_QUARTEIRAO))
            .andExpect(jsonPath("$.numeroPorta").value(DEFAULT_NUMERO_PORTA))
            .andExpect(jsonPath("$.caixaPostal").value(DEFAULT_CAIXA_POSTAL));
    }

    @Test
    @Transactional
    public void getNonExistingLocalizacaoInstituicaoEnsino() throws Exception {
        // Get the localizacaoInstituicaoEnsino
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(get("/api/localizacao-instituicao-ensinos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalizacaoInstituicaoEnsino() throws Exception {
        // Initialize the database
        localizacaoInstituicaoEnsinoRepository.saveAndFlush(localizacaoInstituicaoEnsino);

        int databaseSizeBeforeUpdate = localizacaoInstituicaoEnsinoRepository.findAll().size();

        // Update the localizacaoInstituicaoEnsino
        LocalizacaoInstituicaoEnsino updatedLocalizacaoInstituicaoEnsino = localizacaoInstituicaoEnsinoRepository.findById(localizacaoInstituicaoEnsino.getId()).get();
        // Disconnect from session so that the updates on updatedLocalizacaoInstituicaoEnsino are not directly saved in db
        em.detach(updatedLocalizacaoInstituicaoEnsino);
        updatedLocalizacaoInstituicaoEnsino
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA)
            .caixaPostal(UPDATED_CAIXA_POSTAL);
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(updatedLocalizacaoInstituicaoEnsino);

        restLocalizacaoInstituicaoEnsinoMockMvc.perform(put("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isOk());

        // Validate the LocalizacaoInstituicaoEnsino in the database
        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
        LocalizacaoInstituicaoEnsino testLocalizacaoInstituicaoEnsino = localizacaoInstituicaoEnsinoList.get(localizacaoInstituicaoEnsinoList.size() - 1);
        assertThat(testLocalizacaoInstituicaoEnsino.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testLocalizacaoInstituicaoEnsino.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testLocalizacaoInstituicaoEnsino.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testLocalizacaoInstituicaoEnsino.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testLocalizacaoInstituicaoEnsino.getQuarteirao()).isEqualTo(UPDATED_QUARTEIRAO);
        assertThat(testLocalizacaoInstituicaoEnsino.getNumeroPorta()).isEqualTo(UPDATED_NUMERO_PORTA);
        assertThat(testLocalizacaoInstituicaoEnsino.getCaixaPostal()).isEqualTo(UPDATED_CAIXA_POSTAL);

        // Validate the LocalizacaoInstituicaoEnsino in Elasticsearch
        verify(mockLocalizacaoInstituicaoEnsinoSearchRepository, times(1)).save(testLocalizacaoInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalizacaoInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = localizacaoInstituicaoEnsinoRepository.findAll().size();

        // Create the LocalizacaoInstituicaoEnsino
        LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoMapper.toDto(localizacaoInstituicaoEnsino);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(put("/api/localizacao-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalizacaoInstituicaoEnsino in the database
        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LocalizacaoInstituicaoEnsino in Elasticsearch
        verify(mockLocalizacaoInstituicaoEnsinoSearchRepository, times(0)).save(localizacaoInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void deleteLocalizacaoInstituicaoEnsino() throws Exception {
        // Initialize the database
        localizacaoInstituicaoEnsinoRepository.saveAndFlush(localizacaoInstituicaoEnsino);

        int databaseSizeBeforeDelete = localizacaoInstituicaoEnsinoRepository.findAll().size();

        // Delete the localizacaoInstituicaoEnsino
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(delete("/api/localizacao-instituicao-ensinos/{id}", localizacaoInstituicaoEnsino.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocalizacaoInstituicaoEnsino> localizacaoInstituicaoEnsinoList = localizacaoInstituicaoEnsinoRepository.findAll();
        assertThat(localizacaoInstituicaoEnsinoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LocalizacaoInstituicaoEnsino in Elasticsearch
        verify(mockLocalizacaoInstituicaoEnsinoSearchRepository, times(1)).deleteById(localizacaoInstituicaoEnsino.getId());
    }

    @Test
    @Transactional
    public void searchLocalizacaoInstituicaoEnsino() throws Exception {
        // Initialize the database
        localizacaoInstituicaoEnsinoRepository.saveAndFlush(localizacaoInstituicaoEnsino);
        when(mockLocalizacaoInstituicaoEnsinoSearchRepository.search(queryStringQuery("id:" + localizacaoInstituicaoEnsino.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(localizacaoInstituicaoEnsino), PageRequest.of(0, 1), 1));
        // Search the localizacaoInstituicaoEnsino
        restLocalizacaoInstituicaoEnsinoMockMvc.perform(get("/api/_search/localizacao-instituicao-ensinos?query=id:" + localizacaoInstituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localizacaoInstituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].quarteirao").value(hasItem(DEFAULT_QUARTEIRAO)))
            .andExpect(jsonPath("$.[*].numeroPorta").value(hasItem(DEFAULT_NUMERO_PORTA)))
            .andExpect(jsonPath("$.[*].caixaPostal").value(hasItem(DEFAULT_CAIXA_POSTAL)));
    }
}
