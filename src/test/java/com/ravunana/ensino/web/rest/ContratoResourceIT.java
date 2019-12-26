package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.Contrato;
import com.ravunana.ensino.domain.Aluno;
import com.ravunana.ensino.repository.ContratoRepository;
import com.ravunana.ensino.repository.search.ContratoSearchRepository;
import com.ravunana.ensino.service.ContratoService;
import com.ravunana.ensino.service.dto.ContratoDTO;
import com.ravunana.ensino.service.mapper.ContratoMapper;
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
 * Integration tests for the {@link ContratoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class ContratoResourceIT {

    private static final LocalDate DEFAULT_DE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTRATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTRATO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACEITA_TERMOS = false;
    private static final Boolean UPDATED_ACEITA_TERMOS = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TERMOS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TERMOS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TERMOS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TERMOS_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_EM_VIGOR = false;
    private static final Boolean UPDATED_EM_VIGOR = true;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoMapper contratoMapper;

    @Autowired
    private ContratoService contratoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.ContratoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContratoSearchRepository mockContratoSearchRepository;

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

    private MockMvc restContratoMockMvc;

    private Contrato contrato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContratoResource contratoResource = new ContratoResource(contratoService);
        this.restContratoMockMvc = MockMvcBuilders.standaloneSetup(contratoResource)
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
    public static Contrato createEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .contrato(DEFAULT_CONTRATO)
            .aceitaTermos(DEFAULT_ACEITA_TERMOS)
            .observacao(DEFAULT_OBSERVACAO)
            .termos(DEFAULT_TERMOS)
            .termosContentType(DEFAULT_TERMOS_CONTENT_TYPE)
            .emVigor(DEFAULT_EM_VIGOR);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        contrato.setAluno(aluno);
        return contrato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrato createUpdatedEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .contrato(UPDATED_CONTRATO)
            .aceitaTermos(UPDATED_ACEITA_TERMOS)
            .observacao(UPDATED_OBSERVACAO)
            .termos(UPDATED_TERMOS)
            .termosContentType(UPDATED_TERMOS_CONTENT_TYPE)
            .emVigor(UPDATED_EM_VIGOR);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createUpdatedEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        contrato.setAluno(aluno);
        return contrato;
    }

    @BeforeEach
    public void initTest() {
        contrato = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrato() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isCreated());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate + 1);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testContrato.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testContrato.getContrato()).isEqualTo(DEFAULT_CONTRATO);
        assertThat(testContrato.isAceitaTermos()).isEqualTo(DEFAULT_ACEITA_TERMOS);
        assertThat(testContrato.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testContrato.getTermos()).isEqualTo(DEFAULT_TERMOS);
        assertThat(testContrato.getTermosContentType()).isEqualTo(DEFAULT_TERMOS_CONTENT_TYPE);
        assertThat(testContrato.isEmVigor()).isEqualTo(DEFAULT_EM_VIGOR);

        // Validate the Contrato in Elasticsearch
        verify(mockContratoSearchRepository, times(1)).save(testContrato);
    }

    @Test
    @Transactional
    public void createContratoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato with an existing ID
        contrato.setId(1L);
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Contrato in Elasticsearch
        verify(mockContratoSearchRepository, times(0)).save(contrato);
    }


    @Test
    @Transactional
    public void checkDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setDe(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setAte(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContratoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setContrato(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAceitaTermosIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setAceitaTermos(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmVigorIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setEmVigor(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContratoes() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get all the contratoList
        restContratoMockMvc.perform(get("/api/contratoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrato.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].contrato").value(hasItem(DEFAULT_CONTRATO)))
            .andExpect(jsonPath("$.[*].aceitaTermos").value(hasItem(DEFAULT_ACEITA_TERMOS.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].termosContentType").value(hasItem(DEFAULT_TERMOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].termos").value(hasItem(Base64Utils.encodeToString(DEFAULT_TERMOS))))
            .andExpect(jsonPath("$.[*].emVigor").value(hasItem(DEFAULT_EM_VIGOR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", contrato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contrato.getId().intValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.toString()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.toString()))
            .andExpect(jsonPath("$.contrato").value(DEFAULT_CONTRATO))
            .andExpect(jsonPath("$.aceitaTermos").value(DEFAULT_ACEITA_TERMOS.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.termosContentType").value(DEFAULT_TERMOS_CONTENT_TYPE))
            .andExpect(jsonPath("$.termos").value(Base64Utils.encodeToString(DEFAULT_TERMOS)))
            .andExpect(jsonPath("$.emVigor").value(DEFAULT_EM_VIGOR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContrato() throws Exception {
        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Update the contrato
        Contrato updatedContrato = contratoRepository.findById(contrato.getId()).get();
        // Disconnect from session so that the updates on updatedContrato are not directly saved in db
        em.detach(updatedContrato);
        updatedContrato
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .contrato(UPDATED_CONTRATO)
            .aceitaTermos(UPDATED_ACEITA_TERMOS)
            .observacao(UPDATED_OBSERVACAO)
            .termos(UPDATED_TERMOS)
            .termosContentType(UPDATED_TERMOS_CONTENT_TYPE)
            .emVigor(UPDATED_EM_VIGOR);
        ContratoDTO contratoDTO = contratoMapper.toDto(updatedContrato);

        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isOk());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testContrato.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testContrato.getContrato()).isEqualTo(UPDATED_CONTRATO);
        assertThat(testContrato.isAceitaTermos()).isEqualTo(UPDATED_ACEITA_TERMOS);
        assertThat(testContrato.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testContrato.getTermos()).isEqualTo(UPDATED_TERMOS);
        assertThat(testContrato.getTermosContentType()).isEqualTo(UPDATED_TERMOS_CONTENT_TYPE);
        assertThat(testContrato.isEmVigor()).isEqualTo(UPDATED_EM_VIGOR);

        // Validate the Contrato in Elasticsearch
        verify(mockContratoSearchRepository, times(1)).save(testContrato);
    }

    @Test
    @Transactional
    public void updateNonExistingContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Contrato in Elasticsearch
        verify(mockContratoSearchRepository, times(0)).save(contrato);
    }

    @Test
    @Transactional
    public void deleteContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeDelete = contratoRepository.findAll().size();

        // Delete the contrato
        restContratoMockMvc.perform(delete("/api/contratoes/{id}", contrato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Contrato in Elasticsearch
        verify(mockContratoSearchRepository, times(1)).deleteById(contrato.getId());
    }

    @Test
    @Transactional
    public void searchContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);
        when(mockContratoSearchRepository.search(queryStringQuery("id:" + contrato.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contrato), PageRequest.of(0, 1), 1));
        // Search the contrato
        restContratoMockMvc.perform(get("/api/_search/contratoes?query=id:" + contrato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrato.getId().intValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.toString())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.toString())))
            .andExpect(jsonPath("$.[*].contrato").value(hasItem(DEFAULT_CONTRATO)))
            .andExpect(jsonPath("$.[*].aceitaTermos").value(hasItem(DEFAULT_ACEITA_TERMOS.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].termosContentType").value(hasItem(DEFAULT_TERMOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].termos").value(hasItem(Base64Utils.encodeToString(DEFAULT_TERMOS))))
            .andExpect(jsonPath("$.[*].emVigor").value(hasItem(DEFAULT_EM_VIGOR.booleanValue())));
    }
}
