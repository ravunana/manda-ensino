package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.DocumentoMatricula;
import com.ravunana.ensino.repository.DocumentoMatriculaRepository;
import com.ravunana.ensino.repository.search.DocumentoMatriculaSearchRepository;
import com.ravunana.ensino.service.DocumentoMatriculaService;
import com.ravunana.ensino.service.dto.DocumentoMatriculaDTO;
import com.ravunana.ensino.service.mapper.DocumentoMatriculaMapper;
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
 * Integration tests for the {@link DocumentoMatriculaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class DocumentoMatriculaResourceIT {

    private static final Boolean DEFAULT_FOTOGRAFIA = false;
    private static final Boolean UPDATED_FOTOGRAFIA = true;

    private static final Boolean DEFAULT_CERTIFICADO = false;
    private static final Boolean UPDATED_CERTIFICADO = true;

    private static final Boolean DEFAULT_BILHETE = false;
    private static final Boolean UPDATED_BILHETE = true;

    private static final Boolean DEFAULT_RESENCIAMENTO_MILITAR = false;
    private static final Boolean UPDATED_RESENCIAMENTO_MILITAR = true;

    private static final Boolean DEFAULT_CARTAO_VACINA = false;
    private static final Boolean UPDATED_CARTAO_VACINA = true;

    private static final Boolean DEFAULT_ATESTADO_MEDICO = false;
    private static final Boolean UPDATED_ATESTADO_MEDICO = true;

    private static final Boolean DEFAULT_FICHA_TRNASFERENCIA = false;
    private static final Boolean UPDATED_FICHA_TRNASFERENCIA = true;

    private static final Boolean DEFAULT_HISTORICO_ESCOLAR = false;
    private static final Boolean UPDATED_HISTORICO_ESCOLAR = true;

    private static final Boolean DEFAULT_CEDULA = false;
    private static final Boolean UPDATED_CEDULA = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO_LECTIVO = 1;
    private static final Integer UPDATED_ANO_LECTIVO = 2;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private DocumentoMatriculaRepository documentoMatriculaRepository;

    @Autowired
    private DocumentoMatriculaMapper documentoMatriculaMapper;

    @Autowired
    private DocumentoMatriculaService documentoMatriculaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.DocumentoMatriculaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentoMatriculaSearchRepository mockDocumentoMatriculaSearchRepository;

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

    private MockMvc restDocumentoMatriculaMockMvc;

    private DocumentoMatricula documentoMatricula;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentoMatriculaResource documentoMatriculaResource = new DocumentoMatriculaResource(documentoMatriculaService);
        this.restDocumentoMatriculaMockMvc = MockMvcBuilders.standaloneSetup(documentoMatriculaResource)
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
    public static DocumentoMatricula createEntity(EntityManager em) {
        DocumentoMatricula documentoMatricula = new DocumentoMatricula()
            .fotografia(DEFAULT_FOTOGRAFIA)
            .certificado(DEFAULT_CERTIFICADO)
            .bilhete(DEFAULT_BILHETE)
            .resenciamentoMilitar(DEFAULT_RESENCIAMENTO_MILITAR)
            .cartaoVacina(DEFAULT_CARTAO_VACINA)
            .atestadoMedico(DEFAULT_ATESTADO_MEDICO)
            .fichaTrnasferencia(DEFAULT_FICHA_TRNASFERENCIA)
            .historicoEscolar(DEFAULT_HISTORICO_ESCOLAR)
            .cedula(DEFAULT_CEDULA)
            .descricao(DEFAULT_DESCRICAO)
            .anoLectivo(DEFAULT_ANO_LECTIVO)
            .data(DEFAULT_DATA);
        return documentoMatricula;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoMatricula createUpdatedEntity(EntityManager em) {
        DocumentoMatricula documentoMatricula = new DocumentoMatricula()
            .fotografia(UPDATED_FOTOGRAFIA)
            .certificado(UPDATED_CERTIFICADO)
            .bilhete(UPDATED_BILHETE)
            .resenciamentoMilitar(UPDATED_RESENCIAMENTO_MILITAR)
            .cartaoVacina(UPDATED_CARTAO_VACINA)
            .atestadoMedico(UPDATED_ATESTADO_MEDICO)
            .fichaTrnasferencia(UPDATED_FICHA_TRNASFERENCIA)
            .historicoEscolar(UPDATED_HISTORICO_ESCOLAR)
            .cedula(UPDATED_CEDULA)
            .descricao(UPDATED_DESCRICAO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .data(UPDATED_DATA);
        return documentoMatricula;
    }

    @BeforeEach
    public void initTest() {
        documentoMatricula = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentoMatricula() throws Exception {
        int databaseSizeBeforeCreate = documentoMatriculaRepository.findAll().size();

        // Create the DocumentoMatricula
        DocumentoMatriculaDTO documentoMatriculaDTO = documentoMatriculaMapper.toDto(documentoMatricula);
        restDocumentoMatriculaMockMvc.perform(post("/api/documento-matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoMatriculaDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentoMatricula in the database
        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentoMatricula testDocumentoMatricula = documentoMatriculaList.get(documentoMatriculaList.size() - 1);
        assertThat(testDocumentoMatricula.isFotografia()).isEqualTo(DEFAULT_FOTOGRAFIA);
        assertThat(testDocumentoMatricula.isCertificado()).isEqualTo(DEFAULT_CERTIFICADO);
        assertThat(testDocumentoMatricula.isBilhete()).isEqualTo(DEFAULT_BILHETE);
        assertThat(testDocumentoMatricula.isResenciamentoMilitar()).isEqualTo(DEFAULT_RESENCIAMENTO_MILITAR);
        assertThat(testDocumentoMatricula.isCartaoVacina()).isEqualTo(DEFAULT_CARTAO_VACINA);
        assertThat(testDocumentoMatricula.isAtestadoMedico()).isEqualTo(DEFAULT_ATESTADO_MEDICO);
        assertThat(testDocumentoMatricula.isFichaTrnasferencia()).isEqualTo(DEFAULT_FICHA_TRNASFERENCIA);
        assertThat(testDocumentoMatricula.isHistoricoEscolar()).isEqualTo(DEFAULT_HISTORICO_ESCOLAR);
        assertThat(testDocumentoMatricula.isCedula()).isEqualTo(DEFAULT_CEDULA);
        assertThat(testDocumentoMatricula.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDocumentoMatricula.getAnoLectivo()).isEqualTo(DEFAULT_ANO_LECTIVO);
        assertThat(testDocumentoMatricula.getData()).isEqualTo(DEFAULT_DATA);

        // Validate the DocumentoMatricula in Elasticsearch
        verify(mockDocumentoMatriculaSearchRepository, times(1)).save(testDocumentoMatricula);
    }

    @Test
    @Transactional
    public void createDocumentoMatriculaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoMatriculaRepository.findAll().size();

        // Create the DocumentoMatricula with an existing ID
        documentoMatricula.setId(1L);
        DocumentoMatriculaDTO documentoMatriculaDTO = documentoMatriculaMapper.toDto(documentoMatricula);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoMatriculaMockMvc.perform(post("/api/documento-matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoMatriculaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentoMatricula in the database
        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeCreate);

        // Validate the DocumentoMatricula in Elasticsearch
        verify(mockDocumentoMatriculaSearchRepository, times(0)).save(documentoMatricula);
    }


    @Test
    @Transactional
    public void checkFotografiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoMatriculaRepository.findAll().size();
        // set the field null
        documentoMatricula.setFotografia(null);

        // Create the DocumentoMatricula, which fails.
        DocumentoMatriculaDTO documentoMatriculaDTO = documentoMatriculaMapper.toDto(documentoMatricula);

        restDocumentoMatriculaMockMvc.perform(post("/api/documento-matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoMatriculaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertificadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoMatriculaRepository.findAll().size();
        // set the field null
        documentoMatricula.setCertificado(null);

        // Create the DocumentoMatricula, which fails.
        DocumentoMatriculaDTO documentoMatriculaDTO = documentoMatriculaMapper.toDto(documentoMatricula);

        restDocumentoMatriculaMockMvc.perform(post("/api/documento-matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoMatriculaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBilheteIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoMatriculaRepository.findAll().size();
        // set the field null
        documentoMatricula.setBilhete(null);

        // Create the DocumentoMatricula, which fails.
        DocumentoMatriculaDTO documentoMatriculaDTO = documentoMatriculaMapper.toDto(documentoMatricula);

        restDocumentoMatriculaMockMvc.perform(post("/api/documento-matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoMatriculaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentoMatriculas() throws Exception {
        // Initialize the database
        documentoMatriculaRepository.saveAndFlush(documentoMatricula);

        // Get all the documentoMatriculaList
        restDocumentoMatriculaMockMvc.perform(get("/api/documento-matriculas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoMatricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(DEFAULT_FOTOGRAFIA.booleanValue())))
            .andExpect(jsonPath("$.[*].certificado").value(hasItem(DEFAULT_CERTIFICADO.booleanValue())))
            .andExpect(jsonPath("$.[*].bilhete").value(hasItem(DEFAULT_BILHETE.booleanValue())))
            .andExpect(jsonPath("$.[*].resenciamentoMilitar").value(hasItem(DEFAULT_RESENCIAMENTO_MILITAR.booleanValue())))
            .andExpect(jsonPath("$.[*].cartaoVacina").value(hasItem(DEFAULT_CARTAO_VACINA.booleanValue())))
            .andExpect(jsonPath("$.[*].atestadoMedico").value(hasItem(DEFAULT_ATESTADO_MEDICO.booleanValue())))
            .andExpect(jsonPath("$.[*].fichaTrnasferencia").value(hasItem(DEFAULT_FICHA_TRNASFERENCIA.booleanValue())))
            .andExpect(jsonPath("$.[*].historicoEscolar").value(hasItem(DEFAULT_HISTORICO_ESCOLAR.booleanValue())))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getDocumentoMatricula() throws Exception {
        // Initialize the database
        documentoMatriculaRepository.saveAndFlush(documentoMatricula);

        // Get the documentoMatricula
        restDocumentoMatriculaMockMvc.perform(get("/api/documento-matriculas/{id}", documentoMatricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentoMatricula.getId().intValue()))
            .andExpect(jsonPath("$.fotografia").value(DEFAULT_FOTOGRAFIA.booleanValue()))
            .andExpect(jsonPath("$.certificado").value(DEFAULT_CERTIFICADO.booleanValue()))
            .andExpect(jsonPath("$.bilhete").value(DEFAULT_BILHETE.booleanValue()))
            .andExpect(jsonPath("$.resenciamentoMilitar").value(DEFAULT_RESENCIAMENTO_MILITAR.booleanValue()))
            .andExpect(jsonPath("$.cartaoVacina").value(DEFAULT_CARTAO_VACINA.booleanValue()))
            .andExpect(jsonPath("$.atestadoMedico").value(DEFAULT_ATESTADO_MEDICO.booleanValue()))
            .andExpect(jsonPath("$.fichaTrnasferencia").value(DEFAULT_FICHA_TRNASFERENCIA.booleanValue()))
            .andExpect(jsonPath("$.historicoEscolar").value(DEFAULT_HISTORICO_ESCOLAR.booleanValue()))
            .andExpect(jsonPath("$.cedula").value(DEFAULT_CEDULA.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.anoLectivo").value(DEFAULT_ANO_LECTIVO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentoMatricula() throws Exception {
        // Get the documentoMatricula
        restDocumentoMatriculaMockMvc.perform(get("/api/documento-matriculas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentoMatricula() throws Exception {
        // Initialize the database
        documentoMatriculaRepository.saveAndFlush(documentoMatricula);

        int databaseSizeBeforeUpdate = documentoMatriculaRepository.findAll().size();

        // Update the documentoMatricula
        DocumentoMatricula updatedDocumentoMatricula = documentoMatriculaRepository.findById(documentoMatricula.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentoMatricula are not directly saved in db
        em.detach(updatedDocumentoMatricula);
        updatedDocumentoMatricula
            .fotografia(UPDATED_FOTOGRAFIA)
            .certificado(UPDATED_CERTIFICADO)
            .bilhete(UPDATED_BILHETE)
            .resenciamentoMilitar(UPDATED_RESENCIAMENTO_MILITAR)
            .cartaoVacina(UPDATED_CARTAO_VACINA)
            .atestadoMedico(UPDATED_ATESTADO_MEDICO)
            .fichaTrnasferencia(UPDATED_FICHA_TRNASFERENCIA)
            .historicoEscolar(UPDATED_HISTORICO_ESCOLAR)
            .cedula(UPDATED_CEDULA)
            .descricao(UPDATED_DESCRICAO)
            .anoLectivo(UPDATED_ANO_LECTIVO)
            .data(UPDATED_DATA);
        DocumentoMatriculaDTO documentoMatriculaDTO = documentoMatriculaMapper.toDto(updatedDocumentoMatricula);

        restDocumentoMatriculaMockMvc.perform(put("/api/documento-matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoMatriculaDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentoMatricula in the database
        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeUpdate);
        DocumentoMatricula testDocumentoMatricula = documentoMatriculaList.get(documentoMatriculaList.size() - 1);
        assertThat(testDocumentoMatricula.isFotografia()).isEqualTo(UPDATED_FOTOGRAFIA);
        assertThat(testDocumentoMatricula.isCertificado()).isEqualTo(UPDATED_CERTIFICADO);
        assertThat(testDocumentoMatricula.isBilhete()).isEqualTo(UPDATED_BILHETE);
        assertThat(testDocumentoMatricula.isResenciamentoMilitar()).isEqualTo(UPDATED_RESENCIAMENTO_MILITAR);
        assertThat(testDocumentoMatricula.isCartaoVacina()).isEqualTo(UPDATED_CARTAO_VACINA);
        assertThat(testDocumentoMatricula.isAtestadoMedico()).isEqualTo(UPDATED_ATESTADO_MEDICO);
        assertThat(testDocumentoMatricula.isFichaTrnasferencia()).isEqualTo(UPDATED_FICHA_TRNASFERENCIA);
        assertThat(testDocumentoMatricula.isHistoricoEscolar()).isEqualTo(UPDATED_HISTORICO_ESCOLAR);
        assertThat(testDocumentoMatricula.isCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testDocumentoMatricula.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDocumentoMatricula.getAnoLectivo()).isEqualTo(UPDATED_ANO_LECTIVO);
        assertThat(testDocumentoMatricula.getData()).isEqualTo(UPDATED_DATA);

        // Validate the DocumentoMatricula in Elasticsearch
        verify(mockDocumentoMatriculaSearchRepository, times(1)).save(testDocumentoMatricula);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentoMatricula() throws Exception {
        int databaseSizeBeforeUpdate = documentoMatriculaRepository.findAll().size();

        // Create the DocumentoMatricula
        DocumentoMatriculaDTO documentoMatriculaDTO = documentoMatriculaMapper.toDto(documentoMatricula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMatriculaMockMvc.perform(put("/api/documento-matriculas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoMatriculaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentoMatricula in the database
        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DocumentoMatricula in Elasticsearch
        verify(mockDocumentoMatriculaSearchRepository, times(0)).save(documentoMatricula);
    }

    @Test
    @Transactional
    public void deleteDocumentoMatricula() throws Exception {
        // Initialize the database
        documentoMatriculaRepository.saveAndFlush(documentoMatricula);

        int databaseSizeBeforeDelete = documentoMatriculaRepository.findAll().size();

        // Delete the documentoMatricula
        restDocumentoMatriculaMockMvc.perform(delete("/api/documento-matriculas/{id}", documentoMatricula.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentoMatricula> documentoMatriculaList = documentoMatriculaRepository.findAll();
        assertThat(documentoMatriculaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DocumentoMatricula in Elasticsearch
        verify(mockDocumentoMatriculaSearchRepository, times(1)).deleteById(documentoMatricula.getId());
    }

    @Test
    @Transactional
    public void searchDocumentoMatricula() throws Exception {
        // Initialize the database
        documentoMatriculaRepository.saveAndFlush(documentoMatricula);
        when(mockDocumentoMatriculaSearchRepository.search(queryStringQuery("id:" + documentoMatricula.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentoMatricula), PageRequest.of(0, 1), 1));
        // Search the documentoMatricula
        restDocumentoMatriculaMockMvc.perform(get("/api/_search/documento-matriculas?query=id:" + documentoMatricula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoMatricula.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotografia").value(hasItem(DEFAULT_FOTOGRAFIA.booleanValue())))
            .andExpect(jsonPath("$.[*].certificado").value(hasItem(DEFAULT_CERTIFICADO.booleanValue())))
            .andExpect(jsonPath("$.[*].bilhete").value(hasItem(DEFAULT_BILHETE.booleanValue())))
            .andExpect(jsonPath("$.[*].resenciamentoMilitar").value(hasItem(DEFAULT_RESENCIAMENTO_MILITAR.booleanValue())))
            .andExpect(jsonPath("$.[*].cartaoVacina").value(hasItem(DEFAULT_CARTAO_VACINA.booleanValue())))
            .andExpect(jsonPath("$.[*].atestadoMedico").value(hasItem(DEFAULT_ATESTADO_MEDICO.booleanValue())))
            .andExpect(jsonPath("$.[*].fichaTrnasferencia").value(hasItem(DEFAULT_FICHA_TRNASFERENCIA.booleanValue())))
            .andExpect(jsonPath("$.[*].historicoEscolar").value(hasItem(DEFAULT_HISTORICO_ESCOLAR.booleanValue())))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].anoLectivo").value(hasItem(DEFAULT_ANO_LECTIVO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
}
