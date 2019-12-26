package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Requerimento;
import com.ravunana.ensino.repository.RequerimentoRepository;
import com.ravunana.ensino.repository.search.RequerimentoSearchRepository;
import com.ravunana.ensino.service.RequerimentoService;
import com.ravunana.ensino.service.dto.RequerimentoDTO;
import com.ravunana.ensino.service.mapper.RequerimentoMapper;
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
 * Integration tests for the {@link RequerimentoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class RequerimentoResourceIT {

    private static final byte[] DEFAULT_REQUERIMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REQUERIMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REQUERIMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REQUERIMENTO_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS_REQUERIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_REQUERIMENTO = "BBBBBBBBBB";

    @Autowired
    private RequerimentoRepository requerimentoRepository;

    @Autowired
    private RequerimentoMapper requerimentoMapper;

    @Autowired
    private RequerimentoService requerimentoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.RequerimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequerimentoSearchRepository mockRequerimentoSearchRepository;

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

    private MockMvc restRequerimentoMockMvc;

    private Requerimento requerimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequerimentoResource requerimentoResource = new RequerimentoResource(requerimentoService);
        this.restRequerimentoMockMvc = MockMvcBuilders.standaloneSetup(requerimentoResource)
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
    public static Requerimento createEntity(EntityManager em) {
        Requerimento requerimento = new Requerimento()
            .requerimento(DEFAULT_REQUERIMENTO)
            .requerimentoContentType(DEFAULT_REQUERIMENTO_CONTENT_TYPE)
            .data(DEFAULT_DATA)
            .statusRequerimento(DEFAULT_STATUS_REQUERIMENTO);
        return requerimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requerimento createUpdatedEntity(EntityManager em) {
        Requerimento requerimento = new Requerimento()
            .requerimento(UPDATED_REQUERIMENTO)
            .requerimentoContentType(UPDATED_REQUERIMENTO_CONTENT_TYPE)
            .data(UPDATED_DATA)
            .statusRequerimento(UPDATED_STATUS_REQUERIMENTO);
        return requerimento;
    }

    @BeforeEach
    public void initTest() {
        requerimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequerimento() throws Exception {
        int databaseSizeBeforeCreate = requerimentoRepository.findAll().size();

        // Create the Requerimento
        RequerimentoDTO requerimentoDTO = requerimentoMapper.toDto(requerimento);
        restRequerimentoMockMvc.perform(post("/api/requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Requerimento in the database
        List<Requerimento> requerimentoList = requerimentoRepository.findAll();
        assertThat(requerimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Requerimento testRequerimento = requerimentoList.get(requerimentoList.size() - 1);
        assertThat(testRequerimento.getRequerimento()).isEqualTo(DEFAULT_REQUERIMENTO);
        assertThat(testRequerimento.getRequerimentoContentType()).isEqualTo(DEFAULT_REQUERIMENTO_CONTENT_TYPE);
        assertThat(testRequerimento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testRequerimento.getStatusRequerimento()).isEqualTo(DEFAULT_STATUS_REQUERIMENTO);

        // Validate the Requerimento in Elasticsearch
        verify(mockRequerimentoSearchRepository, times(1)).save(testRequerimento);
    }

    @Test
    @Transactional
    public void createRequerimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requerimentoRepository.findAll().size();

        // Create the Requerimento with an existing ID
        requerimento.setId(1L);
        RequerimentoDTO requerimentoDTO = requerimentoMapper.toDto(requerimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequerimentoMockMvc.perform(post("/api/requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requerimento in the database
        List<Requerimento> requerimentoList = requerimentoRepository.findAll();
        assertThat(requerimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Requerimento in Elasticsearch
        verify(mockRequerimentoSearchRepository, times(0)).save(requerimento);
    }


    @Test
    @Transactional
    public void getAllRequerimentos() throws Exception {
        // Initialize the database
        requerimentoRepository.saveAndFlush(requerimento);

        // Get all the requerimentoList
        restRequerimentoMockMvc.perform(get("/api/requerimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requerimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].requerimentoContentType").value(hasItem(DEFAULT_REQUERIMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requerimento").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUERIMENTO))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].statusRequerimento").value(hasItem(DEFAULT_STATUS_REQUERIMENTO)));
    }
    
    @Test
    @Transactional
    public void getRequerimento() throws Exception {
        // Initialize the database
        requerimentoRepository.saveAndFlush(requerimento);

        // Get the requerimento
        restRequerimentoMockMvc.perform(get("/api/requerimentos/{id}", requerimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requerimento.getId().intValue()))
            .andExpect(jsonPath("$.requerimentoContentType").value(DEFAULT_REQUERIMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.requerimento").value(Base64Utils.encodeToString(DEFAULT_REQUERIMENTO)))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.statusRequerimento").value(DEFAULT_STATUS_REQUERIMENTO));
    }

    @Test
    @Transactional
    public void getNonExistingRequerimento() throws Exception {
        // Get the requerimento
        restRequerimentoMockMvc.perform(get("/api/requerimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequerimento() throws Exception {
        // Initialize the database
        requerimentoRepository.saveAndFlush(requerimento);

        int databaseSizeBeforeUpdate = requerimentoRepository.findAll().size();

        // Update the requerimento
        Requerimento updatedRequerimento = requerimentoRepository.findById(requerimento.getId()).get();
        // Disconnect from session so that the updates on updatedRequerimento are not directly saved in db
        em.detach(updatedRequerimento);
        updatedRequerimento
            .requerimento(UPDATED_REQUERIMENTO)
            .requerimentoContentType(UPDATED_REQUERIMENTO_CONTENT_TYPE)
            .data(UPDATED_DATA)
            .statusRequerimento(UPDATED_STATUS_REQUERIMENTO);
        RequerimentoDTO requerimentoDTO = requerimentoMapper.toDto(updatedRequerimento);

        restRequerimentoMockMvc.perform(put("/api/requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Requerimento in the database
        List<Requerimento> requerimentoList = requerimentoRepository.findAll();
        assertThat(requerimentoList).hasSize(databaseSizeBeforeUpdate);
        Requerimento testRequerimento = requerimentoList.get(requerimentoList.size() - 1);
        assertThat(testRequerimento.getRequerimento()).isEqualTo(UPDATED_REQUERIMENTO);
        assertThat(testRequerimento.getRequerimentoContentType()).isEqualTo(UPDATED_REQUERIMENTO_CONTENT_TYPE);
        assertThat(testRequerimento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testRequerimento.getStatusRequerimento()).isEqualTo(UPDATED_STATUS_REQUERIMENTO);

        // Validate the Requerimento in Elasticsearch
        verify(mockRequerimentoSearchRepository, times(1)).save(testRequerimento);
    }

    @Test
    @Transactional
    public void updateNonExistingRequerimento() throws Exception {
        int databaseSizeBeforeUpdate = requerimentoRepository.findAll().size();

        // Create the Requerimento
        RequerimentoDTO requerimentoDTO = requerimentoMapper.toDto(requerimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequerimentoMockMvc.perform(put("/api/requerimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requerimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requerimento in the database
        List<Requerimento> requerimentoList = requerimentoRepository.findAll();
        assertThat(requerimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Requerimento in Elasticsearch
        verify(mockRequerimentoSearchRepository, times(0)).save(requerimento);
    }

    @Test
    @Transactional
    public void deleteRequerimento() throws Exception {
        // Initialize the database
        requerimentoRepository.saveAndFlush(requerimento);

        int databaseSizeBeforeDelete = requerimentoRepository.findAll().size();

        // Delete the requerimento
        restRequerimentoMockMvc.perform(delete("/api/requerimentos/{id}", requerimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requerimento> requerimentoList = requerimentoRepository.findAll();
        assertThat(requerimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Requerimento in Elasticsearch
        verify(mockRequerimentoSearchRepository, times(1)).deleteById(requerimento.getId());
    }

    @Test
    @Transactional
    public void searchRequerimento() throws Exception {
        // Initialize the database
        requerimentoRepository.saveAndFlush(requerimento);
        when(mockRequerimentoSearchRepository.search(queryStringQuery("id:" + requerimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requerimento), PageRequest.of(0, 1), 1));
        // Search the requerimento
        restRequerimentoMockMvc.perform(get("/api/_search/requerimentos?query=id:" + requerimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requerimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].requerimentoContentType").value(hasItem(DEFAULT_REQUERIMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requerimento").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUERIMENTO))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].statusRequerimento").value(hasItem(DEFAULT_STATUS_REQUERIMENTO)));
    }
}
