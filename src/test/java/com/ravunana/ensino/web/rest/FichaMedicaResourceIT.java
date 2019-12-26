package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.FichaMedica;
import com.ravunana.ensino.domain.Aluno;
import com.ravunana.ensino.repository.FichaMedicaRepository;
import com.ravunana.ensino.repository.search.FichaMedicaSearchRepository;
import com.ravunana.ensino.service.FichaMedicaService;
import com.ravunana.ensino.service.dto.FichaMedicaDTO;
import com.ravunana.ensino.service.mapper.FichaMedicaMapper;
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
 * Integration tests for the {@link FichaMedicaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class FichaMedicaResourceIT {

    private static final Boolean DEFAULT_FAZ_EDUCACAO_FISICA = false;
    private static final Boolean UPDATED_FAZ_EDUCACAO_FISICA = true;

    private static final String DEFAULT_GRUPO_SANGUINIO = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_SANGUINIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALTURA = 1;
    private static final Integer UPDATED_ALTURA = 2;

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final Boolean DEFAULT_AUTORIZA_MEDICAMENTO = false;
    private static final Boolean UPDATED_AUTORIZA_MEDICAMENTO = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_MEDICO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESMAIO_CONSTANTE = false;
    private static final Boolean UPDATED_DESMAIO_CONSTANTE = true;

    private static final String DEFAULT_COMPLICACOES_SAUDE = "AAAAAAAAAA";
    private static final String UPDATED_COMPLICACOES_SAUDE = "BBBBBBBBBB";

    @Autowired
    private FichaMedicaRepository fichaMedicaRepository;

    @Autowired
    private FichaMedicaMapper fichaMedicaMapper;

    @Autowired
    private FichaMedicaService fichaMedicaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.FichaMedicaSearchRepositoryMockConfiguration
     */
    @Autowired
    private FichaMedicaSearchRepository mockFichaMedicaSearchRepository;

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

    private MockMvc restFichaMedicaMockMvc;

    private FichaMedica fichaMedica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichaMedicaResource fichaMedicaResource = new FichaMedicaResource(fichaMedicaService);
        this.restFichaMedicaMockMvc = MockMvcBuilders.standaloneSetup(fichaMedicaResource)
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
    public static FichaMedica createEntity(EntityManager em) {
        FichaMedica fichaMedica = new FichaMedica()
            .fazEducacaoFisica(DEFAULT_FAZ_EDUCACAO_FISICA)
            .grupoSanguinio(DEFAULT_GRUPO_SANGUINIO)
            .altura(DEFAULT_ALTURA)
            .peso(DEFAULT_PESO)
            .autorizaMedicamento(DEFAULT_AUTORIZA_MEDICAMENTO)
            .observacao(DEFAULT_OBSERVACAO)
            .nomeMedico(DEFAULT_NOME_MEDICO)
            .contactoMedico(DEFAULT_CONTACTO_MEDICO)
            .desmaioConstante(DEFAULT_DESMAIO_CONSTANTE)
            .complicacoesSaude(DEFAULT_COMPLICACOES_SAUDE);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        fichaMedica.setAluno(aluno);
        return fichaMedica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FichaMedica createUpdatedEntity(EntityManager em) {
        FichaMedica fichaMedica = new FichaMedica()
            .fazEducacaoFisica(UPDATED_FAZ_EDUCACAO_FISICA)
            .grupoSanguinio(UPDATED_GRUPO_SANGUINIO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .autorizaMedicamento(UPDATED_AUTORIZA_MEDICAMENTO)
            .observacao(UPDATED_OBSERVACAO)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .contactoMedico(UPDATED_CONTACTO_MEDICO)
            .desmaioConstante(UPDATED_DESMAIO_CONSTANTE)
            .complicacoesSaude(UPDATED_COMPLICACOES_SAUDE);
        // Add required entity
        Aluno aluno;
        if (TestUtil.findAll(em, Aluno.class).isEmpty()) {
            aluno = AlunoResourceIT.createUpdatedEntity(em);
            em.persist(aluno);
            em.flush();
        } else {
            aluno = TestUtil.findAll(em, Aluno.class).get(0);
        }
        fichaMedica.setAluno(aluno);
        return fichaMedica;
    }

    @BeforeEach
    public void initTest() {
        fichaMedica = createEntity(em);
    }

    @Test
    @Transactional
    public void createFichaMedica() throws Exception {
        int databaseSizeBeforeCreate = fichaMedicaRepository.findAll().size();

        // Create the FichaMedica
        FichaMedicaDTO fichaMedicaDTO = fichaMedicaMapper.toDto(fichaMedica);
        restFichaMedicaMockMvc.perform(post("/api/ficha-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaMedicaDTO)))
            .andExpect(status().isCreated());

        // Validate the FichaMedica in the database
        List<FichaMedica> fichaMedicaList = fichaMedicaRepository.findAll();
        assertThat(fichaMedicaList).hasSize(databaseSizeBeforeCreate + 1);
        FichaMedica testFichaMedica = fichaMedicaList.get(fichaMedicaList.size() - 1);
        assertThat(testFichaMedica.isFazEducacaoFisica()).isEqualTo(DEFAULT_FAZ_EDUCACAO_FISICA);
        assertThat(testFichaMedica.getGrupoSanguinio()).isEqualTo(DEFAULT_GRUPO_SANGUINIO);
        assertThat(testFichaMedica.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testFichaMedica.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testFichaMedica.isAutorizaMedicamento()).isEqualTo(DEFAULT_AUTORIZA_MEDICAMENTO);
        assertThat(testFichaMedica.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testFichaMedica.getNomeMedico()).isEqualTo(DEFAULT_NOME_MEDICO);
        assertThat(testFichaMedica.getContactoMedico()).isEqualTo(DEFAULT_CONTACTO_MEDICO);
        assertThat(testFichaMedica.isDesmaioConstante()).isEqualTo(DEFAULT_DESMAIO_CONSTANTE);
        assertThat(testFichaMedica.getComplicacoesSaude()).isEqualTo(DEFAULT_COMPLICACOES_SAUDE);

        // Validate the FichaMedica in Elasticsearch
        verify(mockFichaMedicaSearchRepository, times(1)).save(testFichaMedica);
    }

    @Test
    @Transactional
    public void createFichaMedicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichaMedicaRepository.findAll().size();

        // Create the FichaMedica with an existing ID
        fichaMedica.setId(1L);
        FichaMedicaDTO fichaMedicaDTO = fichaMedicaMapper.toDto(fichaMedica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichaMedicaMockMvc.perform(post("/api/ficha-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaMedicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FichaMedica in the database
        List<FichaMedica> fichaMedicaList = fichaMedicaRepository.findAll();
        assertThat(fichaMedicaList).hasSize(databaseSizeBeforeCreate);

        // Validate the FichaMedica in Elasticsearch
        verify(mockFichaMedicaSearchRepository, times(0)).save(fichaMedica);
    }


    @Test
    @Transactional
    public void checkFazEducacaoFisicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaMedicaRepository.findAll().size();
        // set the field null
        fichaMedica.setFazEducacaoFisica(null);

        // Create the FichaMedica, which fails.
        FichaMedicaDTO fichaMedicaDTO = fichaMedicaMapper.toDto(fichaMedica);

        restFichaMedicaMockMvc.perform(post("/api/ficha-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaMedicaDTO)))
            .andExpect(status().isBadRequest());

        List<FichaMedica> fichaMedicaList = fichaMedicaRepository.findAll();
        assertThat(fichaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutorizaMedicamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaMedicaRepository.findAll().size();
        // set the field null
        fichaMedica.setAutorizaMedicamento(null);

        // Create the FichaMedica, which fails.
        FichaMedicaDTO fichaMedicaDTO = fichaMedicaMapper.toDto(fichaMedica);

        restFichaMedicaMockMvc.perform(post("/api/ficha-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaMedicaDTO)))
            .andExpect(status().isBadRequest());

        List<FichaMedica> fichaMedicaList = fichaMedicaRepository.findAll();
        assertThat(fichaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFichaMedicas() throws Exception {
        // Initialize the database
        fichaMedicaRepository.saveAndFlush(fichaMedica);

        // Get all the fichaMedicaList
        restFichaMedicaMockMvc.perform(get("/api/ficha-medicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichaMedica.getId().intValue())))
            .andExpect(jsonPath("$.[*].fazEducacaoFisica").value(hasItem(DEFAULT_FAZ_EDUCACAO_FISICA.booleanValue())))
            .andExpect(jsonPath("$.[*].grupoSanguinio").value(hasItem(DEFAULT_GRUPO_SANGUINIO)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].autorizaMedicamento").value(hasItem(DEFAULT_AUTORIZA_MEDICAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].nomeMedico").value(hasItem(DEFAULT_NOME_MEDICO)))
            .andExpect(jsonPath("$.[*].contactoMedico").value(hasItem(DEFAULT_CONTACTO_MEDICO)))
            .andExpect(jsonPath("$.[*].desmaioConstante").value(hasItem(DEFAULT_DESMAIO_CONSTANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].complicacoesSaude").value(hasItem(DEFAULT_COMPLICACOES_SAUDE.toString())));
    }
    
    @Test
    @Transactional
    public void getFichaMedica() throws Exception {
        // Initialize the database
        fichaMedicaRepository.saveAndFlush(fichaMedica);

        // Get the fichaMedica
        restFichaMedicaMockMvc.perform(get("/api/ficha-medicas/{id}", fichaMedica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichaMedica.getId().intValue()))
            .andExpect(jsonPath("$.fazEducacaoFisica").value(DEFAULT_FAZ_EDUCACAO_FISICA.booleanValue()))
            .andExpect(jsonPath("$.grupoSanguinio").value(DEFAULT_GRUPO_SANGUINIO))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.autorizaMedicamento").value(DEFAULT_AUTORIZA_MEDICAMENTO.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.nomeMedico").value(DEFAULT_NOME_MEDICO))
            .andExpect(jsonPath("$.contactoMedico").value(DEFAULT_CONTACTO_MEDICO))
            .andExpect(jsonPath("$.desmaioConstante").value(DEFAULT_DESMAIO_CONSTANTE.booleanValue()))
            .andExpect(jsonPath("$.complicacoesSaude").value(DEFAULT_COMPLICACOES_SAUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFichaMedica() throws Exception {
        // Get the fichaMedica
        restFichaMedicaMockMvc.perform(get("/api/ficha-medicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichaMedica() throws Exception {
        // Initialize the database
        fichaMedicaRepository.saveAndFlush(fichaMedica);

        int databaseSizeBeforeUpdate = fichaMedicaRepository.findAll().size();

        // Update the fichaMedica
        FichaMedica updatedFichaMedica = fichaMedicaRepository.findById(fichaMedica.getId()).get();
        // Disconnect from session so that the updates on updatedFichaMedica are not directly saved in db
        em.detach(updatedFichaMedica);
        updatedFichaMedica
            .fazEducacaoFisica(UPDATED_FAZ_EDUCACAO_FISICA)
            .grupoSanguinio(UPDATED_GRUPO_SANGUINIO)
            .altura(UPDATED_ALTURA)
            .peso(UPDATED_PESO)
            .autorizaMedicamento(UPDATED_AUTORIZA_MEDICAMENTO)
            .observacao(UPDATED_OBSERVACAO)
            .nomeMedico(UPDATED_NOME_MEDICO)
            .contactoMedico(UPDATED_CONTACTO_MEDICO)
            .desmaioConstante(UPDATED_DESMAIO_CONSTANTE)
            .complicacoesSaude(UPDATED_COMPLICACOES_SAUDE);
        FichaMedicaDTO fichaMedicaDTO = fichaMedicaMapper.toDto(updatedFichaMedica);

        restFichaMedicaMockMvc.perform(put("/api/ficha-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaMedicaDTO)))
            .andExpect(status().isOk());

        // Validate the FichaMedica in the database
        List<FichaMedica> fichaMedicaList = fichaMedicaRepository.findAll();
        assertThat(fichaMedicaList).hasSize(databaseSizeBeforeUpdate);
        FichaMedica testFichaMedica = fichaMedicaList.get(fichaMedicaList.size() - 1);
        assertThat(testFichaMedica.isFazEducacaoFisica()).isEqualTo(UPDATED_FAZ_EDUCACAO_FISICA);
        assertThat(testFichaMedica.getGrupoSanguinio()).isEqualTo(UPDATED_GRUPO_SANGUINIO);
        assertThat(testFichaMedica.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testFichaMedica.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testFichaMedica.isAutorizaMedicamento()).isEqualTo(UPDATED_AUTORIZA_MEDICAMENTO);
        assertThat(testFichaMedica.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testFichaMedica.getNomeMedico()).isEqualTo(UPDATED_NOME_MEDICO);
        assertThat(testFichaMedica.getContactoMedico()).isEqualTo(UPDATED_CONTACTO_MEDICO);
        assertThat(testFichaMedica.isDesmaioConstante()).isEqualTo(UPDATED_DESMAIO_CONSTANTE);
        assertThat(testFichaMedica.getComplicacoesSaude()).isEqualTo(UPDATED_COMPLICACOES_SAUDE);

        // Validate the FichaMedica in Elasticsearch
        verify(mockFichaMedicaSearchRepository, times(1)).save(testFichaMedica);
    }

    @Test
    @Transactional
    public void updateNonExistingFichaMedica() throws Exception {
        int databaseSizeBeforeUpdate = fichaMedicaRepository.findAll().size();

        // Create the FichaMedica
        FichaMedicaDTO fichaMedicaDTO = fichaMedicaMapper.toDto(fichaMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaMedicaMockMvc.perform(put("/api/ficha-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaMedicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FichaMedica in the database
        List<FichaMedica> fichaMedicaList = fichaMedicaRepository.findAll();
        assertThat(fichaMedicaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FichaMedica in Elasticsearch
        verify(mockFichaMedicaSearchRepository, times(0)).save(fichaMedica);
    }

    @Test
    @Transactional
    public void deleteFichaMedica() throws Exception {
        // Initialize the database
        fichaMedicaRepository.saveAndFlush(fichaMedica);

        int databaseSizeBeforeDelete = fichaMedicaRepository.findAll().size();

        // Delete the fichaMedica
        restFichaMedicaMockMvc.perform(delete("/api/ficha-medicas/{id}", fichaMedica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FichaMedica> fichaMedicaList = fichaMedicaRepository.findAll();
        assertThat(fichaMedicaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FichaMedica in Elasticsearch
        verify(mockFichaMedicaSearchRepository, times(1)).deleteById(fichaMedica.getId());
    }

    @Test
    @Transactional
    public void searchFichaMedica() throws Exception {
        // Initialize the database
        fichaMedicaRepository.saveAndFlush(fichaMedica);
        when(mockFichaMedicaSearchRepository.search(queryStringQuery("id:" + fichaMedica.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fichaMedica), PageRequest.of(0, 1), 1));
        // Search the fichaMedica
        restFichaMedicaMockMvc.perform(get("/api/_search/ficha-medicas?query=id:" + fichaMedica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichaMedica.getId().intValue())))
            .andExpect(jsonPath("$.[*].fazEducacaoFisica").value(hasItem(DEFAULT_FAZ_EDUCACAO_FISICA.booleanValue())))
            .andExpect(jsonPath("$.[*].grupoSanguinio").value(hasItem(DEFAULT_GRUPO_SANGUINIO)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].autorizaMedicamento").value(hasItem(DEFAULT_AUTORIZA_MEDICAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].nomeMedico").value(hasItem(DEFAULT_NOME_MEDICO)))
            .andExpect(jsonPath("$.[*].contactoMedico").value(hasItem(DEFAULT_CONTACTO_MEDICO)))
            .andExpect(jsonPath("$.[*].desmaioConstante").value(hasItem(DEFAULT_DESMAIO_CONSTANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].complicacoesSaude").value(hasItem(DEFAULT_COMPLICACOES_SAUDE.toString())));
    }
}
