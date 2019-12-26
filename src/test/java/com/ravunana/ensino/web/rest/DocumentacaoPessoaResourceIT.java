package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.DocumentacaoPessoa;
import com.ravunana.ensino.domain.Pessoa;
import com.ravunana.ensino.repository.DocumentacaoPessoaRepository;
import com.ravunana.ensino.repository.search.DocumentacaoPessoaSearchRepository;
import com.ravunana.ensino.service.DocumentacaoPessoaService;
import com.ravunana.ensino.service.dto.DocumentacaoPessoaDTO;
import com.ravunana.ensino.service.mapper.DocumentacaoPessoaMapper;
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
 * Integration tests for the {@link DocumentacaoPessoaResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class DocumentacaoPessoaResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDADE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NATURALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NATURALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_NACIONALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_EMISSAO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_EMISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    @Autowired
    private DocumentacaoPessoaRepository documentacaoPessoaRepository;

    @Autowired
    private DocumentacaoPessoaMapper documentacaoPessoaMapper;

    @Autowired
    private DocumentacaoPessoaService documentacaoPessoaService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.DocumentacaoPessoaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentacaoPessoaSearchRepository mockDocumentacaoPessoaSearchRepository;

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

    private MockMvc restDocumentacaoPessoaMockMvc;

    private DocumentacaoPessoa documentacaoPessoa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentacaoPessoaResource documentacaoPessoaResource = new DocumentacaoPessoaResource(documentacaoPessoaService);
        this.restDocumentacaoPessoaMockMvc = MockMvcBuilders.standaloneSetup(documentacaoPessoaResource)
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
    public static DocumentacaoPessoa createEntity(EntityManager em) {
        DocumentacaoPessoa documentacaoPessoa = new DocumentacaoPessoa()
            .tipo(DEFAULT_TIPO)
            .numero(DEFAULT_NUMERO)
            .emissao(DEFAULT_EMISSAO)
            .validade(DEFAULT_VALIDADE)
            .naturalidade(DEFAULT_NATURALIDADE)
            .nacionalidade(DEFAULT_NACIONALIDADE)
            .localEmissao(DEFAULT_LOCAL_EMISSAO)
            .nif(DEFAULT_NIF);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        documentacaoPessoa.setPessoa(pessoa);
        return documentacaoPessoa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentacaoPessoa createUpdatedEntity(EntityManager em) {
        DocumentacaoPessoa documentacaoPessoa = new DocumentacaoPessoa()
            .tipo(UPDATED_TIPO)
            .numero(UPDATED_NUMERO)
            .emissao(UPDATED_EMISSAO)
            .validade(UPDATED_VALIDADE)
            .naturalidade(UPDATED_NATURALIDADE)
            .nacionalidade(UPDATED_NACIONALIDADE)
            .localEmissao(UPDATED_LOCAL_EMISSAO)
            .nif(UPDATED_NIF);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        documentacaoPessoa.setPessoa(pessoa);
        return documentacaoPessoa;
    }

    @BeforeEach
    public void initTest() {
        documentacaoPessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentacaoPessoa() throws Exception {
        int databaseSizeBeforeCreate = documentacaoPessoaRepository.findAll().size();

        // Create the DocumentacaoPessoa
        DocumentacaoPessoaDTO documentacaoPessoaDTO = documentacaoPessoaMapper.toDto(documentacaoPessoa);
        restDocumentacaoPessoaMockMvc.perform(post("/api/documentacao-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentacaoPessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentacaoPessoa in the database
        List<DocumentacaoPessoa> documentacaoPessoaList = documentacaoPessoaRepository.findAll();
        assertThat(documentacaoPessoaList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentacaoPessoa testDocumentacaoPessoa = documentacaoPessoaList.get(documentacaoPessoaList.size() - 1);
        assertThat(testDocumentacaoPessoa.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testDocumentacaoPessoa.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDocumentacaoPessoa.getEmissao()).isEqualTo(DEFAULT_EMISSAO);
        assertThat(testDocumentacaoPessoa.getValidade()).isEqualTo(DEFAULT_VALIDADE);
        assertThat(testDocumentacaoPessoa.getNaturalidade()).isEqualTo(DEFAULT_NATURALIDADE);
        assertThat(testDocumentacaoPessoa.getNacionalidade()).isEqualTo(DEFAULT_NACIONALIDADE);
        assertThat(testDocumentacaoPessoa.getLocalEmissao()).isEqualTo(DEFAULT_LOCAL_EMISSAO);
        assertThat(testDocumentacaoPessoa.getNif()).isEqualTo(DEFAULT_NIF);

        // Validate the DocumentacaoPessoa in Elasticsearch
        verify(mockDocumentacaoPessoaSearchRepository, times(1)).save(testDocumentacaoPessoa);
    }

    @Test
    @Transactional
    public void createDocumentacaoPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentacaoPessoaRepository.findAll().size();

        // Create the DocumentacaoPessoa with an existing ID
        documentacaoPessoa.setId(1L);
        DocumentacaoPessoaDTO documentacaoPessoaDTO = documentacaoPessoaMapper.toDto(documentacaoPessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentacaoPessoaMockMvc.perform(post("/api/documentacao-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentacaoPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentacaoPessoa in the database
        List<DocumentacaoPessoa> documentacaoPessoaList = documentacaoPessoaRepository.findAll();
        assertThat(documentacaoPessoaList).hasSize(databaseSizeBeforeCreate);

        // Validate the DocumentacaoPessoa in Elasticsearch
        verify(mockDocumentacaoPessoaSearchRepository, times(0)).save(documentacaoPessoa);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentacaoPessoaRepository.findAll().size();
        // set the field null
        documentacaoPessoa.setTipo(null);

        // Create the DocumentacaoPessoa, which fails.
        DocumentacaoPessoaDTO documentacaoPessoaDTO = documentacaoPessoaMapper.toDto(documentacaoPessoa);

        restDocumentacaoPessoaMockMvc.perform(post("/api/documentacao-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentacaoPessoaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentacaoPessoa> documentacaoPessoaList = documentacaoPessoaRepository.findAll();
        assertThat(documentacaoPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentacaoPessoaRepository.findAll().size();
        // set the field null
        documentacaoPessoa.setNumero(null);

        // Create the DocumentacaoPessoa, which fails.
        DocumentacaoPessoaDTO documentacaoPessoaDTO = documentacaoPessoaMapper.toDto(documentacaoPessoa);

        restDocumentacaoPessoaMockMvc.perform(post("/api/documentacao-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentacaoPessoaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentacaoPessoa> documentacaoPessoaList = documentacaoPessoaRepository.findAll();
        assertThat(documentacaoPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentacaoPessoas() throws Exception {
        // Initialize the database
        documentacaoPessoaRepository.saveAndFlush(documentacaoPessoa);

        // Get all the documentacaoPessoaList
        restDocumentacaoPessoaMockMvc.perform(get("/api/documentacao-pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentacaoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].emissao").value(hasItem(DEFAULT_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE)))
            .andExpect(jsonPath("$.[*].localEmissao").value(hasItem(DEFAULT_LOCAL_EMISSAO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)));
    }
    
    @Test
    @Transactional
    public void getDocumentacaoPessoa() throws Exception {
        // Initialize the database
        documentacaoPessoaRepository.saveAndFlush(documentacaoPessoa);

        // Get the documentacaoPessoa
        restDocumentacaoPessoaMockMvc.perform(get("/api/documentacao-pessoas/{id}", documentacaoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentacaoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.emissao").value(DEFAULT_EMISSAO.toString()))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE.toString()))
            .andExpect(jsonPath("$.naturalidade").value(DEFAULT_NATURALIDADE))
            .andExpect(jsonPath("$.nacionalidade").value(DEFAULT_NACIONALIDADE))
            .andExpect(jsonPath("$.localEmissao").value(DEFAULT_LOCAL_EMISSAO))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentacaoPessoa() throws Exception {
        // Get the documentacaoPessoa
        restDocumentacaoPessoaMockMvc.perform(get("/api/documentacao-pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentacaoPessoa() throws Exception {
        // Initialize the database
        documentacaoPessoaRepository.saveAndFlush(documentacaoPessoa);

        int databaseSizeBeforeUpdate = documentacaoPessoaRepository.findAll().size();

        // Update the documentacaoPessoa
        DocumentacaoPessoa updatedDocumentacaoPessoa = documentacaoPessoaRepository.findById(documentacaoPessoa.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentacaoPessoa are not directly saved in db
        em.detach(updatedDocumentacaoPessoa);
        updatedDocumentacaoPessoa
            .tipo(UPDATED_TIPO)
            .numero(UPDATED_NUMERO)
            .emissao(UPDATED_EMISSAO)
            .validade(UPDATED_VALIDADE)
            .naturalidade(UPDATED_NATURALIDADE)
            .nacionalidade(UPDATED_NACIONALIDADE)
            .localEmissao(UPDATED_LOCAL_EMISSAO)
            .nif(UPDATED_NIF);
        DocumentacaoPessoaDTO documentacaoPessoaDTO = documentacaoPessoaMapper.toDto(updatedDocumentacaoPessoa);

        restDocumentacaoPessoaMockMvc.perform(put("/api/documentacao-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentacaoPessoaDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentacaoPessoa in the database
        List<DocumentacaoPessoa> documentacaoPessoaList = documentacaoPessoaRepository.findAll();
        assertThat(documentacaoPessoaList).hasSize(databaseSizeBeforeUpdate);
        DocumentacaoPessoa testDocumentacaoPessoa = documentacaoPessoaList.get(documentacaoPessoaList.size() - 1);
        assertThat(testDocumentacaoPessoa.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testDocumentacaoPessoa.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDocumentacaoPessoa.getEmissao()).isEqualTo(UPDATED_EMISSAO);
        assertThat(testDocumentacaoPessoa.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testDocumentacaoPessoa.getNaturalidade()).isEqualTo(UPDATED_NATURALIDADE);
        assertThat(testDocumentacaoPessoa.getNacionalidade()).isEqualTo(UPDATED_NACIONALIDADE);
        assertThat(testDocumentacaoPessoa.getLocalEmissao()).isEqualTo(UPDATED_LOCAL_EMISSAO);
        assertThat(testDocumentacaoPessoa.getNif()).isEqualTo(UPDATED_NIF);

        // Validate the DocumentacaoPessoa in Elasticsearch
        verify(mockDocumentacaoPessoaSearchRepository, times(1)).save(testDocumentacaoPessoa);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentacaoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = documentacaoPessoaRepository.findAll().size();

        // Create the DocumentacaoPessoa
        DocumentacaoPessoaDTO documentacaoPessoaDTO = documentacaoPessoaMapper.toDto(documentacaoPessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentacaoPessoaMockMvc.perform(put("/api/documentacao-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentacaoPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentacaoPessoa in the database
        List<DocumentacaoPessoa> documentacaoPessoaList = documentacaoPessoaRepository.findAll();
        assertThat(documentacaoPessoaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DocumentacaoPessoa in Elasticsearch
        verify(mockDocumentacaoPessoaSearchRepository, times(0)).save(documentacaoPessoa);
    }

    @Test
    @Transactional
    public void deleteDocumentacaoPessoa() throws Exception {
        // Initialize the database
        documentacaoPessoaRepository.saveAndFlush(documentacaoPessoa);

        int databaseSizeBeforeDelete = documentacaoPessoaRepository.findAll().size();

        // Delete the documentacaoPessoa
        restDocumentacaoPessoaMockMvc.perform(delete("/api/documentacao-pessoas/{id}", documentacaoPessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentacaoPessoa> documentacaoPessoaList = documentacaoPessoaRepository.findAll();
        assertThat(documentacaoPessoaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DocumentacaoPessoa in Elasticsearch
        verify(mockDocumentacaoPessoaSearchRepository, times(1)).deleteById(documentacaoPessoa.getId());
    }

    @Test
    @Transactional
    public void searchDocumentacaoPessoa() throws Exception {
        // Initialize the database
        documentacaoPessoaRepository.saveAndFlush(documentacaoPessoa);
        when(mockDocumentacaoPessoaSearchRepository.search(queryStringQuery("id:" + documentacaoPessoa.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentacaoPessoa), PageRequest.of(0, 1), 1));
        // Search the documentacaoPessoa
        restDocumentacaoPessoaMockMvc.perform(get("/api/_search/documentacao-pessoas?query=id:" + documentacaoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentacaoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].emissao").value(hasItem(DEFAULT_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE)))
            .andExpect(jsonPath("$.[*].localEmissao").value(hasItem(DEFAULT_LOCAL_EMISSAO)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)));
    }
}
