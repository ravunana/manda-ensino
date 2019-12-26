package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.CategoriaAluno;
import com.ravunana.ensino.repository.CategoriaAlunoRepository;
import com.ravunana.ensino.repository.search.CategoriaAlunoSearchRepository;
import com.ravunana.ensino.service.CategoriaAlunoService;
import com.ravunana.ensino.service.dto.CategoriaAlunoDTO;
import com.ravunana.ensino.service.mapper.CategoriaAlunoMapper;
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
 * Integration tests for the {@link CategoriaAlunoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class CategoriaAlunoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_DESCONTO = 1D;
    private static final Double UPDATED_DESCONTO = 2D;

    private static final Boolean DEFAULT_PAGA_PROPINA = false;
    private static final Boolean UPDATED_PAGA_PROPINA = true;

    private static final Boolean DEFAULT_PAGA_MULTA = false;
    private static final Boolean UPDATED_PAGA_MULTA = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIA_PAGAMENTO = 1;
    private static final Integer UPDATED_DIA_PAGAMENTO = 2;

    private static final Boolean DEFAULT_MES_ATUAL = false;
    private static final Boolean UPDATED_MES_ATUAL = true;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private CategoriaAlunoRepository categoriaAlunoRepository;

    @Autowired
    private CategoriaAlunoMapper categoriaAlunoMapper;

    @Autowired
    private CategoriaAlunoService categoriaAlunoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.CategoriaAlunoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoriaAlunoSearchRepository mockCategoriaAlunoSearchRepository;

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

    private MockMvc restCategoriaAlunoMockMvc;

    private CategoriaAluno categoriaAluno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaAlunoResource categoriaAlunoResource = new CategoriaAlunoResource(categoriaAlunoService);
        this.restCategoriaAlunoMockMvc = MockMvcBuilders.standaloneSetup(categoriaAlunoResource)
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
    public static CategoriaAluno createEntity(EntityManager em) {
        CategoriaAluno categoriaAluno = new CategoriaAluno()
            .nome(DEFAULT_NOME)
            .desconto(DEFAULT_DESCONTO)
            .pagaPropina(DEFAULT_PAGA_PROPINA)
            .pagaMulta(DEFAULT_PAGA_MULTA)
            .descricao(DEFAULT_DESCRICAO)
            .diaPagamento(DEFAULT_DIA_PAGAMENTO)
            .mesAtual(DEFAULT_MES_ATUAL)
            .ativo(DEFAULT_ATIVO);
        return categoriaAluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaAluno createUpdatedEntity(EntityManager em) {
        CategoriaAluno categoriaAluno = new CategoriaAluno()
            .nome(UPDATED_NOME)
            .desconto(UPDATED_DESCONTO)
            .pagaPropina(UPDATED_PAGA_PROPINA)
            .pagaMulta(UPDATED_PAGA_MULTA)
            .descricao(UPDATED_DESCRICAO)
            .diaPagamento(UPDATED_DIA_PAGAMENTO)
            .mesAtual(UPDATED_MES_ATUAL)
            .ativo(UPDATED_ATIVO);
        return categoriaAluno;
    }

    @BeforeEach
    public void initTest() {
        categoriaAluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaAluno() throws Exception {
        int databaseSizeBeforeCreate = categoriaAlunoRepository.findAll().size();

        // Create the CategoriaAluno
        CategoriaAlunoDTO categoriaAlunoDTO = categoriaAlunoMapper.toDto(categoriaAluno);
        restCategoriaAlunoMockMvc.perform(post("/api/categoria-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaAlunoDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoriaAluno in the database
        List<CategoriaAluno> categoriaAlunoList = categoriaAlunoRepository.findAll();
        assertThat(categoriaAlunoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaAluno testCategoriaAluno = categoriaAlunoList.get(categoriaAlunoList.size() - 1);
        assertThat(testCategoriaAluno.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCategoriaAluno.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testCategoriaAluno.isPagaPropina()).isEqualTo(DEFAULT_PAGA_PROPINA);
        assertThat(testCategoriaAluno.isPagaMulta()).isEqualTo(DEFAULT_PAGA_MULTA);
        assertThat(testCategoriaAluno.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCategoriaAluno.getDiaPagamento()).isEqualTo(DEFAULT_DIA_PAGAMENTO);
        assertThat(testCategoriaAluno.isMesAtual()).isEqualTo(DEFAULT_MES_ATUAL);
        assertThat(testCategoriaAluno.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the CategoriaAluno in Elasticsearch
        verify(mockCategoriaAlunoSearchRepository, times(1)).save(testCategoriaAluno);
    }

    @Test
    @Transactional
    public void createCategoriaAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaAlunoRepository.findAll().size();

        // Create the CategoriaAluno with an existing ID
        categoriaAluno.setId(1L);
        CategoriaAlunoDTO categoriaAlunoDTO = categoriaAlunoMapper.toDto(categoriaAluno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaAlunoMockMvc.perform(post("/api/categoria-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaAlunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaAluno in the database
        List<CategoriaAluno> categoriaAlunoList = categoriaAlunoRepository.findAll();
        assertThat(categoriaAlunoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CategoriaAluno in Elasticsearch
        verify(mockCategoriaAlunoSearchRepository, times(0)).save(categoriaAluno);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaAlunoRepository.findAll().size();
        // set the field null
        categoriaAluno.setNome(null);

        // Create the CategoriaAluno, which fails.
        CategoriaAlunoDTO categoriaAlunoDTO = categoriaAlunoMapper.toDto(categoriaAluno);

        restCategoriaAlunoMockMvc.perform(post("/api/categoria-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaAlunoDTO)))
            .andExpect(status().isBadRequest());

        List<CategoriaAluno> categoriaAlunoList = categoriaAlunoRepository.findAll();
        assertThat(categoriaAlunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaAlunoRepository.findAll().size();
        // set the field null
        categoriaAluno.setDesconto(null);

        // Create the CategoriaAluno, which fails.
        CategoriaAlunoDTO categoriaAlunoDTO = categoriaAlunoMapper.toDto(categoriaAluno);

        restCategoriaAlunoMockMvc.perform(post("/api/categoria-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaAlunoDTO)))
            .andExpect(status().isBadRequest());

        List<CategoriaAluno> categoriaAlunoList = categoriaAlunoRepository.findAll();
        assertThat(categoriaAlunoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriaAlunos() throws Exception {
        // Initialize the database
        categoriaAlunoRepository.saveAndFlush(categoriaAluno);

        // Get all the categoriaAlunoList
        restCategoriaAlunoMockMvc.perform(get("/api/categoria-alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaAluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].pagaPropina").value(hasItem(DEFAULT_PAGA_PROPINA.booleanValue())))
            .andExpect(jsonPath("$.[*].pagaMulta").value(hasItem(DEFAULT_PAGA_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].diaPagamento").value(hasItem(DEFAULT_DIA_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].mesAtual").value(hasItem(DEFAULT_MES_ATUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCategoriaAluno() throws Exception {
        // Initialize the database
        categoriaAlunoRepository.saveAndFlush(categoriaAluno);

        // Get the categoriaAluno
        restCategoriaAlunoMockMvc.perform(get("/api/categoria-alunos/{id}", categoriaAluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaAluno.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.pagaPropina").value(DEFAULT_PAGA_PROPINA.booleanValue()))
            .andExpect(jsonPath("$.pagaMulta").value(DEFAULT_PAGA_MULTA.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.diaPagamento").value(DEFAULT_DIA_PAGAMENTO))
            .andExpect(jsonPath("$.mesAtual").value(DEFAULT_MES_ATUAL.booleanValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoriaAluno() throws Exception {
        // Get the categoriaAluno
        restCategoriaAlunoMockMvc.perform(get("/api/categoria-alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaAluno() throws Exception {
        // Initialize the database
        categoriaAlunoRepository.saveAndFlush(categoriaAluno);

        int databaseSizeBeforeUpdate = categoriaAlunoRepository.findAll().size();

        // Update the categoriaAluno
        CategoriaAluno updatedCategoriaAluno = categoriaAlunoRepository.findById(categoriaAluno.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaAluno are not directly saved in db
        em.detach(updatedCategoriaAluno);
        updatedCategoriaAluno
            .nome(UPDATED_NOME)
            .desconto(UPDATED_DESCONTO)
            .pagaPropina(UPDATED_PAGA_PROPINA)
            .pagaMulta(UPDATED_PAGA_MULTA)
            .descricao(UPDATED_DESCRICAO)
            .diaPagamento(UPDATED_DIA_PAGAMENTO)
            .mesAtual(UPDATED_MES_ATUAL)
            .ativo(UPDATED_ATIVO);
        CategoriaAlunoDTO categoriaAlunoDTO = categoriaAlunoMapper.toDto(updatedCategoriaAluno);

        restCategoriaAlunoMockMvc.perform(put("/api/categoria-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaAlunoDTO)))
            .andExpect(status().isOk());

        // Validate the CategoriaAluno in the database
        List<CategoriaAluno> categoriaAlunoList = categoriaAlunoRepository.findAll();
        assertThat(categoriaAlunoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaAluno testCategoriaAluno = categoriaAlunoList.get(categoriaAlunoList.size() - 1);
        assertThat(testCategoriaAluno.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaAluno.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testCategoriaAluno.isPagaPropina()).isEqualTo(UPDATED_PAGA_PROPINA);
        assertThat(testCategoriaAluno.isPagaMulta()).isEqualTo(UPDATED_PAGA_MULTA);
        assertThat(testCategoriaAluno.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCategoriaAluno.getDiaPagamento()).isEqualTo(UPDATED_DIA_PAGAMENTO);
        assertThat(testCategoriaAluno.isMesAtual()).isEqualTo(UPDATED_MES_ATUAL);
        assertThat(testCategoriaAluno.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the CategoriaAluno in Elasticsearch
        verify(mockCategoriaAlunoSearchRepository, times(1)).save(testCategoriaAluno);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaAluno() throws Exception {
        int databaseSizeBeforeUpdate = categoriaAlunoRepository.findAll().size();

        // Create the CategoriaAluno
        CategoriaAlunoDTO categoriaAlunoDTO = categoriaAlunoMapper.toDto(categoriaAluno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaAlunoMockMvc.perform(put("/api/categoria-alunos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaAlunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaAluno in the database
        List<CategoriaAluno> categoriaAlunoList = categoriaAlunoRepository.findAll();
        assertThat(categoriaAlunoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CategoriaAluno in Elasticsearch
        verify(mockCategoriaAlunoSearchRepository, times(0)).save(categoriaAluno);
    }

    @Test
    @Transactional
    public void deleteCategoriaAluno() throws Exception {
        // Initialize the database
        categoriaAlunoRepository.saveAndFlush(categoriaAluno);

        int databaseSizeBeforeDelete = categoriaAlunoRepository.findAll().size();

        // Delete the categoriaAluno
        restCategoriaAlunoMockMvc.perform(delete("/api/categoria-alunos/{id}", categoriaAluno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaAluno> categoriaAlunoList = categoriaAlunoRepository.findAll();
        assertThat(categoriaAlunoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CategoriaAluno in Elasticsearch
        verify(mockCategoriaAlunoSearchRepository, times(1)).deleteById(categoriaAluno.getId());
    }

    @Test
    @Transactional
    public void searchCategoriaAluno() throws Exception {
        // Initialize the database
        categoriaAlunoRepository.saveAndFlush(categoriaAluno);
        when(mockCategoriaAlunoSearchRepository.search(queryStringQuery("id:" + categoriaAluno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(categoriaAluno), PageRequest.of(0, 1), 1));
        // Search the categoriaAluno
        restCategoriaAlunoMockMvc.perform(get("/api/_search/categoria-alunos?query=id:" + categoriaAluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaAluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].pagaPropina").value(hasItem(DEFAULT_PAGA_PROPINA.booleanValue())))
            .andExpect(jsonPath("$.[*].pagaMulta").value(hasItem(DEFAULT_PAGA_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].diaPagamento").value(hasItem(DEFAULT_DIA_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].mesAtual").value(hasItem(DEFAULT_MES_ATUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
}
