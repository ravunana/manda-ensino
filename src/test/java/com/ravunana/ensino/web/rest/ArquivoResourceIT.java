package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Arquivo;
import com.ravunana.ensino.repository.ArquivoRepository;
import com.ravunana.ensino.repository.search.ArquivoSearchRepository;
import com.ravunana.ensino.service.ArquivoService;
import com.ravunana.ensino.service.dto.ArquivoDTO;
import com.ravunana.ensino.service.mapper.ArquivoMapper;
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
 * Integration tests for the {@link ArquivoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class ArquivoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_ENTIDADE = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDADE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ANEXO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANEXO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ANEXO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANEXO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CODIGO_ENTIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ENTIDADE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private ArquivoMapper arquivoMapper;

    @Autowired
    private ArquivoService arquivoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.ArquivoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArquivoSearchRepository mockArquivoSearchRepository;

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

    private MockMvc restArquivoMockMvc;

    private Arquivo arquivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArquivoResource arquivoResource = new ArquivoResource(arquivoService);
        this.restArquivoMockMvc = MockMvcBuilders.standaloneSetup(arquivoResource)
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
    public static Arquivo createEntity(EntityManager em) {
        Arquivo arquivo = new Arquivo()
            .descricao(DEFAULT_DESCRICAO)
            .entidade(DEFAULT_ENTIDADE)
            .anexo(DEFAULT_ANEXO)
            .anexoContentType(DEFAULT_ANEXO_CONTENT_TYPE)
            .codigoEntidade(DEFAULT_CODIGO_ENTIDADE)
            .data(DEFAULT_DATA);
        return arquivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arquivo createUpdatedEntity(EntityManager em) {
        Arquivo arquivo = new Arquivo()
            .descricao(UPDATED_DESCRICAO)
            .entidade(UPDATED_ENTIDADE)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .codigoEntidade(UPDATED_CODIGO_ENTIDADE)
            .data(UPDATED_DATA);
        return arquivo;
    }

    @BeforeEach
    public void initTest() {
        arquivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArquivo() throws Exception {
        int databaseSizeBeforeCreate = arquivoRepository.findAll().size();

        // Create the Arquivo
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);
        restArquivoMockMvc.perform(post("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isCreated());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeCreate + 1);
        Arquivo testArquivo = arquivoList.get(arquivoList.size() - 1);
        assertThat(testArquivo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testArquivo.getEntidade()).isEqualTo(DEFAULT_ENTIDADE);
        assertThat(testArquivo.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testArquivo.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
        assertThat(testArquivo.getCodigoEntidade()).isEqualTo(DEFAULT_CODIGO_ENTIDADE);
        assertThat(testArquivo.getData()).isEqualTo(DEFAULT_DATA);

        // Validate the Arquivo in Elasticsearch
        verify(mockArquivoSearchRepository, times(1)).save(testArquivo);
    }

    @Test
    @Transactional
    public void createArquivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arquivoRepository.findAll().size();

        // Create the Arquivo with an existing ID
        arquivo.setId(1L);
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArquivoMockMvc.perform(post("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Arquivo in Elasticsearch
        verify(mockArquivoSearchRepository, times(0)).save(arquivo);
    }


    @Test
    @Transactional
    public void getAllArquivos() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList
        restArquivoMockMvc.perform(get("/api/arquivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE)))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].codigoEntidade").value(hasItem(DEFAULT_CODIGO_ENTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get the arquivo
        restArquivoMockMvc.perform(get("/api/arquivos/{id}", arquivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arquivo.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.entidade").value(DEFAULT_ENTIDADE))
            .andExpect(jsonPath("$.anexoContentType").value(DEFAULT_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.anexo").value(Base64Utils.encodeToString(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.codigoEntidade").value(DEFAULT_CODIGO_ENTIDADE))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingArquivo() throws Exception {
        // Get the arquivo
        restArquivoMockMvc.perform(get("/api/arquivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        int databaseSizeBeforeUpdate = arquivoRepository.findAll().size();

        // Update the arquivo
        Arquivo updatedArquivo = arquivoRepository.findById(arquivo.getId()).get();
        // Disconnect from session so that the updates on updatedArquivo are not directly saved in db
        em.detach(updatedArquivo);
        updatedArquivo
            .descricao(UPDATED_DESCRICAO)
            .entidade(UPDATED_ENTIDADE)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .codigoEntidade(UPDATED_CODIGO_ENTIDADE)
            .data(UPDATED_DATA);
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(updatedArquivo);

        restArquivoMockMvc.perform(put("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isOk());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeUpdate);
        Arquivo testArquivo = arquivoList.get(arquivoList.size() - 1);
        assertThat(testArquivo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testArquivo.getEntidade()).isEqualTo(UPDATED_ENTIDADE);
        assertThat(testArquivo.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testArquivo.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testArquivo.getCodigoEntidade()).isEqualTo(UPDATED_CODIGO_ENTIDADE);
        assertThat(testArquivo.getData()).isEqualTo(UPDATED_DATA);

        // Validate the Arquivo in Elasticsearch
        verify(mockArquivoSearchRepository, times(1)).save(testArquivo);
    }

    @Test
    @Transactional
    public void updateNonExistingArquivo() throws Exception {
        int databaseSizeBeforeUpdate = arquivoRepository.findAll().size();

        // Create the Arquivo
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArquivoMockMvc.perform(put("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arquivo in Elasticsearch
        verify(mockArquivoSearchRepository, times(0)).save(arquivo);
    }

    @Test
    @Transactional
    public void deleteArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        int databaseSizeBeforeDelete = arquivoRepository.findAll().size();

        // Delete the arquivo
        restArquivoMockMvc.perform(delete("/api/arquivos/{id}", arquivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Arquivo in Elasticsearch
        verify(mockArquivoSearchRepository, times(1)).deleteById(arquivo.getId());
    }

    @Test
    @Transactional
    public void searchArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);
        when(mockArquivoSearchRepository.search(queryStringQuery("id:" + arquivo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(arquivo), PageRequest.of(0, 1), 1));
        // Search the arquivo
        restArquivoMockMvc.perform(get("/api/_search/arquivos?query=id:" + arquivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE)))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].codigoEntidade").value(hasItem(DEFAULT_CODIGO_ENTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
}
