package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.DetalheOcorrencia;
import com.ravunana.ensino.domain.Ocorrencia;
import com.ravunana.ensino.repository.DetalheOcorrenciaRepository;
import com.ravunana.ensino.repository.search.DetalheOcorrenciaSearchRepository;
import com.ravunana.ensino.service.DetalheOcorrenciaService;
import com.ravunana.ensino.service.dto.DetalheOcorrenciaDTO;
import com.ravunana.ensino.service.mapper.DetalheOcorrenciaMapper;
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
 * Integration tests for the {@link DetalheOcorrenciaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class DetalheOcorrenciaResourceIT {

    private static final LocalDate DEFAULT_DE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    @Autowired
    private DetalheOcorrenciaRepository detalheOcorrenciaRepository;

    @Autowired
    private DetalheOcorrenciaMapper detalheOcorrenciaMapper;

    @Autowired
    private DetalheOcorrenciaService detalheOcorrenciaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.DetalheOcorrenciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DetalheOcorrenciaSearchRepository mockDetalheOcorrenciaSearchRepository;

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

    private MockMvc restDetalheOcorrenciaMockMvc;

    private DetalheOcorrencia detalheOcorrencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalheOcorrenciaResource detalheOcorrenciaResource = new DetalheOcorrenciaResource(detalheOcorrenciaService);
        this.restDetalheOcorrenciaMockMvc = MockMvcBuilders.standaloneSetup(detalheOcorrenciaResource)
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
    public static DetalheOcorrencia createEntity(EntityManager em) {
        DetalheOcorrencia detalheOcorrencia = new DetalheOcorrencia()
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .motivo(DEFAULT_MOTIVO);
        // Add required entity
        Ocorrencia ocorrencia;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            ocorrencia = OcorrenciaResourceIT.createEntity(em);
            em.persist(ocorrencia);
            em.flush();
        } else {
            ocorrencia = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        detalheOcorrencia.setOcorrencia(ocorrencia);
        return detalheOcorrencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalheOcorrencia createUpdatedEntity(EntityManager em) {
        DetalheOcorrencia detalheOcorrencia = new DetalheOcorrencia()
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .motivo(UPDATED_MOTIVO);
        // Add required entity
        Ocorrencia ocorrencia;
        if (TestUtil.findAll(em, Ocorrencia.class).isEmpty()) {
            ocorrencia = OcorrenciaResourceIT.createUpdatedEntity(em);
            em.persist(ocorrencia);
            em.flush();
        } else {
            ocorrencia = TestUtil.findAll(em, Ocorrencia.class).get(0);
        }
        detalheOcorrencia.setOcorrencia(ocorrencia);
        return detalheOcorrencia;
    }

    @BeforeEach
    public void initTest() {
        detalheOcorrencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalheOcorrencia() throws Exception {
        int databaseSizeBeforeCreate = detalheOcorrenciaRepository.findAll().size();

        // Create the DetalheOcorrencia
        DetalheOcorrenciaDTO detalheOcorrenciaDTO = detalheOcorrenciaMapper.toDto(detalheOcorrencia);
        restDetalheOcorrenciaMockMvc.perform(post("/api/detalhe-ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheOcorrenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalheOcorrencia in the database
        List<DetalheOcorrencia> detalheOcorrenciaList = detalheOcorrenciaRepository.findAll();
        assertThat(detalheOcorrenciaList).hasSize(databaseSizeBeforeCreate + 1);
        DetalheOcorrencia testDetalheOcorrencia = detalheOcorrenciaList.get(detalheOcorrenciaList.size() - 1);
        assertThat(testDetalheOcorrencia.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testDetalheOcorrencia.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testDetalheOcorrencia.getMotivo()).isEqualTo(DEFAULT_MOTIVO);

        // Validate the DetalheOcorrencia in Elasticsearch
        verify(mockDetalheOcorrenciaSearchRepository, times(1)).save(testDetalheOcorrencia);
    }

    @Test
    @Transactional
    public void createDetalheOcorrenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalheOcorrenciaRepository.findAll().size();

        // Create the DetalheOcorrencia with an existing ID
        detalheOcorrencia.setId(1L);
        DetalheOcorrenciaDTO detalheOcorrenciaDTO = detalheOcorrenciaMapper.toDto(detalheOcorrencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalheOcorrenciaMockMvc.perform(post("/api/detalhe-ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheOcorrenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalheOcorrencia in the database
        List<DetalheOcorrencia> detalheOcorrenciaList = detalheOcorrenciaRepository.findAll();
        assertThat(detalheOcorrenciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the DetalheOcorrencia in Elasticsearch
        verify(mockDetalheOcorrenciaSearchRepository, times(0)).save(detalheOcorrencia);
    }


    @Test
    @Transactional
    public void getAllDetalheOcorrencias() throws Exception {
        // Initialize the database
        detalheOcorrenciaRepository.saveAndFlush(detalheOcorrencia);

        // Get all the detalheOcorrenciaList
        restDetalheOcorrenciaMockMvc.perform(get("/api/detalhe-ocorrencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalheOcorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }
    
    @Test
    @Transactional
    public void getDetalheOcorrencia() throws Exception {
        // Initialize the database
        detalheOcorrenciaRepository.saveAndFlush(detalheOcorrencia);

        // Get the detalheOcorrencia
        restDetalheOcorrenciaMockMvc.perform(get("/api/detalhe-ocorrencias/{id}", detalheOcorrencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalheOcorrencia.getId().intValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalheOcorrencia() throws Exception {
        // Get the detalheOcorrencia
        restDetalheOcorrenciaMockMvc.perform(get("/api/detalhe-ocorrencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalheOcorrencia() throws Exception {
        // Initialize the database
        detalheOcorrenciaRepository.saveAndFlush(detalheOcorrencia);

        int databaseSizeBeforeUpdate = detalheOcorrenciaRepository.findAll().size();

        // Update the detalheOcorrencia
        DetalheOcorrencia updatedDetalheOcorrencia = detalheOcorrenciaRepository.findById(detalheOcorrencia.getId()).get();
        // Disconnect from session so that the updates on updatedDetalheOcorrencia are not directly saved in db
        em.detach(updatedDetalheOcorrencia);
        updatedDetalheOcorrencia
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .motivo(UPDATED_MOTIVO);
        DetalheOcorrenciaDTO detalheOcorrenciaDTO = detalheOcorrenciaMapper.toDto(updatedDetalheOcorrencia);

        restDetalheOcorrenciaMockMvc.perform(put("/api/detalhe-ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheOcorrenciaDTO)))
            .andExpect(status().isOk());

        // Validate the DetalheOcorrencia in the database
        List<DetalheOcorrencia> detalheOcorrenciaList = detalheOcorrenciaRepository.findAll();
        assertThat(detalheOcorrenciaList).hasSize(databaseSizeBeforeUpdate);
        DetalheOcorrencia testDetalheOcorrencia = detalheOcorrenciaList.get(detalheOcorrenciaList.size() - 1);
        assertThat(testDetalheOcorrencia.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testDetalheOcorrencia.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testDetalheOcorrencia.getMotivo()).isEqualTo(UPDATED_MOTIVO);

        // Validate the DetalheOcorrencia in Elasticsearch
        verify(mockDetalheOcorrenciaSearchRepository, times(1)).save(testDetalheOcorrencia);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalheOcorrencia() throws Exception {
        int databaseSizeBeforeUpdate = detalheOcorrenciaRepository.findAll().size();

        // Create the DetalheOcorrencia
        DetalheOcorrenciaDTO detalheOcorrenciaDTO = detalheOcorrenciaMapper.toDto(detalheOcorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalheOcorrenciaMockMvc.perform(put("/api/detalhe-ocorrencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheOcorrenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalheOcorrencia in the database
        List<DetalheOcorrencia> detalheOcorrenciaList = detalheOcorrenciaRepository.findAll();
        assertThat(detalheOcorrenciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DetalheOcorrencia in Elasticsearch
        verify(mockDetalheOcorrenciaSearchRepository, times(0)).save(detalheOcorrencia);
    }

    @Test
    @Transactional
    public void deleteDetalheOcorrencia() throws Exception {
        // Initialize the database
        detalheOcorrenciaRepository.saveAndFlush(detalheOcorrencia);

        int databaseSizeBeforeDelete = detalheOcorrenciaRepository.findAll().size();

        // Delete the detalheOcorrencia
        restDetalheOcorrenciaMockMvc.perform(delete("/api/detalhe-ocorrencias/{id}", detalheOcorrencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalheOcorrencia> detalheOcorrenciaList = detalheOcorrenciaRepository.findAll();
        assertThat(detalheOcorrenciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DetalheOcorrencia in Elasticsearch
        verify(mockDetalheOcorrenciaSearchRepository, times(1)).deleteById(detalheOcorrencia.getId());
    }

    @Test
    @Transactional
    public void searchDetalheOcorrencia() throws Exception {
        // Initialize the database
        detalheOcorrenciaRepository.saveAndFlush(detalheOcorrencia);
        when(mockDetalheOcorrenciaSearchRepository.search(queryStringQuery("id:" + detalheOcorrencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(detalheOcorrencia), PageRequest.of(0, 1), 1));
        // Search the detalheOcorrencia
        restDetalheOcorrenciaMockMvc.perform(get("/api/_search/detalhe-ocorrencias?query=id:" + detalheOcorrencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalheOcorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }
}
