package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.EncarregadoEducacao;
import com.ravunana.ensino.domain.Pessoa;
import com.ravunana.ensino.repository.EncarregadoEducacaoRepository;
import com.ravunana.ensino.repository.search.EncarregadoEducacaoSearchRepository;
import com.ravunana.ensino.service.EncarregadoEducacaoService;
import com.ravunana.ensino.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.ensino.service.mapper.EncarregadoEducacaoMapper;
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
 * Integration tests for the {@link EncarregadoEducacaoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class EncarregadoEducacaoResourceIT {

    private static final String DEFAULT_PROFISSAO = "AAAAAAAAAA";
    private static final String UPDATED_PROFISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FAIXA_SALARIAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_FAIXA_SALARIAL = new BigDecimal(2);

    private static final String DEFAULT_NOME_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_EMPRESA = "BBBBBBBBBB";

    @Autowired
    private EncarregadoEducacaoRepository encarregadoEducacaoRepository;

    @Autowired
    private EncarregadoEducacaoMapper encarregadoEducacaoMapper;

    @Autowired
    private EncarregadoEducacaoService encarregadoEducacaoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.EncarregadoEducacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EncarregadoEducacaoSearchRepository mockEncarregadoEducacaoSearchRepository;

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

    private MockMvc restEncarregadoEducacaoMockMvc;

    private EncarregadoEducacao encarregadoEducacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EncarregadoEducacaoResource encarregadoEducacaoResource = new EncarregadoEducacaoResource(encarregadoEducacaoService);
        this.restEncarregadoEducacaoMockMvc = MockMvcBuilders.standaloneSetup(encarregadoEducacaoResource)
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
    public static EncarregadoEducacao createEntity(EntityManager em) {
        EncarregadoEducacao encarregadoEducacao = new EncarregadoEducacao()
            .profissao(DEFAULT_PROFISSAO)
            .cargo(DEFAULT_CARGO)
            .faixaSalarial(DEFAULT_FAIXA_SALARIAL)
            .nomeEmpresa(DEFAULT_NOME_EMPRESA)
            .contactoEmpresa(DEFAULT_CONTACTO_EMPRESA);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        encarregadoEducacao.setPessoa(pessoa);
        return encarregadoEducacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncarregadoEducacao createUpdatedEntity(EntityManager em) {
        EncarregadoEducacao encarregadoEducacao = new EncarregadoEducacao()
            .profissao(UPDATED_PROFISSAO)
            .cargo(UPDATED_CARGO)
            .faixaSalarial(UPDATED_FAIXA_SALARIAL)
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .contactoEmpresa(UPDATED_CONTACTO_EMPRESA);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        encarregadoEducacao.setPessoa(pessoa);
        return encarregadoEducacao;
    }

    @BeforeEach
    public void initTest() {
        encarregadoEducacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeCreate = encarregadoEducacaoRepository.findAll().size();

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);
        restEncarregadoEducacaoMockMvc.perform(post("/api/encarregado-educacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeCreate + 1);
        EncarregadoEducacao testEncarregadoEducacao = encarregadoEducacaoList.get(encarregadoEducacaoList.size() - 1);
        assertThat(testEncarregadoEducacao.getProfissao()).isEqualTo(DEFAULT_PROFISSAO);
        assertThat(testEncarregadoEducacao.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testEncarregadoEducacao.getFaixaSalarial()).isEqualTo(DEFAULT_FAIXA_SALARIAL);
        assertThat(testEncarregadoEducacao.getNomeEmpresa()).isEqualTo(DEFAULT_NOME_EMPRESA);
        assertThat(testEncarregadoEducacao.getContactoEmpresa()).isEqualTo(DEFAULT_CONTACTO_EMPRESA);

        // Validate the EncarregadoEducacao in Elasticsearch
        verify(mockEncarregadoEducacaoSearchRepository, times(1)).save(testEncarregadoEducacao);
    }

    @Test
    @Transactional
    public void createEncarregadoEducacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encarregadoEducacaoRepository.findAll().size();

        // Create the EncarregadoEducacao with an existing ID
        encarregadoEducacao.setId(1L);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncarregadoEducacaoMockMvc.perform(post("/api/encarregado-educacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the EncarregadoEducacao in Elasticsearch
        verify(mockEncarregadoEducacaoSearchRepository, times(0)).save(encarregadoEducacao);
    }


    @Test
    @Transactional
    public void getAllEncarregadoEducacaos() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get all the encarregadoEducacaoList
        restEncarregadoEducacaoMockMvc.perform(get("/api/encarregado-educacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encarregadoEducacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].profissao").value(hasItem(DEFAULT_PROFISSAO)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].faixaSalarial").value(hasItem(DEFAULT_FAIXA_SALARIAL.intValue())))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA)))
            .andExpect(jsonPath("$.[*].contactoEmpresa").value(hasItem(DEFAULT_CONTACTO_EMPRESA)));
    }
    
    @Test
    @Transactional
    public void getEncarregadoEducacao() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        // Get the encarregadoEducacao
        restEncarregadoEducacaoMockMvc.perform(get("/api/encarregado-educacaos/{id}", encarregadoEducacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encarregadoEducacao.getId().intValue()))
            .andExpect(jsonPath("$.profissao").value(DEFAULT_PROFISSAO))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.faixaSalarial").value(DEFAULT_FAIXA_SALARIAL.intValue()))
            .andExpect(jsonPath("$.nomeEmpresa").value(DEFAULT_NOME_EMPRESA))
            .andExpect(jsonPath("$.contactoEmpresa").value(DEFAULT_CONTACTO_EMPRESA));
    }

    @Test
    @Transactional
    public void getNonExistingEncarregadoEducacao() throws Exception {
        // Get the encarregadoEducacao
        restEncarregadoEducacaoMockMvc.perform(get("/api/encarregado-educacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncarregadoEducacao() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();

        // Update the encarregadoEducacao
        EncarregadoEducacao updatedEncarregadoEducacao = encarregadoEducacaoRepository.findById(encarregadoEducacao.getId()).get();
        // Disconnect from session so that the updates on updatedEncarregadoEducacao are not directly saved in db
        em.detach(updatedEncarregadoEducacao);
        updatedEncarregadoEducacao
            .profissao(UPDATED_PROFISSAO)
            .cargo(UPDATED_CARGO)
            .faixaSalarial(UPDATED_FAIXA_SALARIAL)
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .contactoEmpresa(UPDATED_CONTACTO_EMPRESA);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(updatedEncarregadoEducacao);

        restEncarregadoEducacaoMockMvc.perform(put("/api/encarregado-educacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO)))
            .andExpect(status().isOk());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);
        EncarregadoEducacao testEncarregadoEducacao = encarregadoEducacaoList.get(encarregadoEducacaoList.size() - 1);
        assertThat(testEncarregadoEducacao.getProfissao()).isEqualTo(UPDATED_PROFISSAO);
        assertThat(testEncarregadoEducacao.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testEncarregadoEducacao.getFaixaSalarial()).isEqualTo(UPDATED_FAIXA_SALARIAL);
        assertThat(testEncarregadoEducacao.getNomeEmpresa()).isEqualTo(UPDATED_NOME_EMPRESA);
        assertThat(testEncarregadoEducacao.getContactoEmpresa()).isEqualTo(UPDATED_CONTACTO_EMPRESA);

        // Validate the EncarregadoEducacao in Elasticsearch
        verify(mockEncarregadoEducacaoSearchRepository, times(1)).save(testEncarregadoEducacao);
    }

    @Test
    @Transactional
    public void updateNonExistingEncarregadoEducacao() throws Exception {
        int databaseSizeBeforeUpdate = encarregadoEducacaoRepository.findAll().size();

        // Create the EncarregadoEducacao
        EncarregadoEducacaoDTO encarregadoEducacaoDTO = encarregadoEducacaoMapper.toDto(encarregadoEducacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncarregadoEducacaoMockMvc.perform(put("/api/encarregado-educacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encarregadoEducacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EncarregadoEducacao in the database
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EncarregadoEducacao in Elasticsearch
        verify(mockEncarregadoEducacaoSearchRepository, times(0)).save(encarregadoEducacao);
    }

    @Test
    @Transactional
    public void deleteEncarregadoEducacao() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);

        int databaseSizeBeforeDelete = encarregadoEducacaoRepository.findAll().size();

        // Delete the encarregadoEducacao
        restEncarregadoEducacaoMockMvc.perform(delete("/api/encarregado-educacaos/{id}", encarregadoEducacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EncarregadoEducacao> encarregadoEducacaoList = encarregadoEducacaoRepository.findAll();
        assertThat(encarregadoEducacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EncarregadoEducacao in Elasticsearch
        verify(mockEncarregadoEducacaoSearchRepository, times(1)).deleteById(encarregadoEducacao.getId());
    }

    @Test
    @Transactional
    public void searchEncarregadoEducacao() throws Exception {
        // Initialize the database
        encarregadoEducacaoRepository.saveAndFlush(encarregadoEducacao);
        when(mockEncarregadoEducacaoSearchRepository.search(queryStringQuery("id:" + encarregadoEducacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(encarregadoEducacao), PageRequest.of(0, 1), 1));
        // Search the encarregadoEducacao
        restEncarregadoEducacaoMockMvc.perform(get("/api/_search/encarregado-educacaos?query=id:" + encarregadoEducacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encarregadoEducacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].profissao").value(hasItem(DEFAULT_PROFISSAO)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].faixaSalarial").value(hasItem(DEFAULT_FAIXA_SALARIAL.intValue())))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA)))
            .andExpect(jsonPath("$.[*].contactoEmpresa").value(hasItem(DEFAULT_CONTACTO_EMPRESA)));
    }
}
