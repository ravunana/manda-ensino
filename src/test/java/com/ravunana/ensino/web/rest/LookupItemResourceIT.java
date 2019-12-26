package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.LookupItem;
import com.ravunana.ensino.repository.LookupItemRepository;
import com.ravunana.ensino.repository.search.LookupItemSearchRepository;
import com.ravunana.ensino.service.LookupItemService;
import com.ravunana.ensino.service.dto.LookupItemDTO;
import com.ravunana.ensino.service.mapper.LookupItemMapper;
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

import com.ravunana.ensino.domain.enumeration.LookupType;
/**
 * Integration tests for the {@link LookupItemResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class LookupItemResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LookupType DEFAULT_TYPE = LookupType.USUARIO;
    private static final LookupType UPDATED_TYPE = LookupType.SISTEMA;

    @Autowired
    private LookupItemRepository lookupItemRepository;

    @Autowired
    private LookupItemMapper lookupItemMapper;

    @Autowired
    private LookupItemService lookupItemService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.LookupItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private LookupItemSearchRepository mockLookupItemSearchRepository;

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

    private MockMvc restLookupItemMockMvc;

    private LookupItem lookupItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LookupItemResource lookupItemResource = new LookupItemResource(lookupItemService);
        this.restLookupItemMockMvc = MockMvcBuilders.standaloneSetup(lookupItemResource)
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
    public static LookupItem createEntity(EntityManager em) {
        LookupItem lookupItem = new LookupItem()
            .valor(DEFAULT_VALOR)
            .nome(DEFAULT_NOME)
            .type(DEFAULT_TYPE);
        return lookupItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LookupItem createUpdatedEntity(EntityManager em) {
        LookupItem lookupItem = new LookupItem()
            .valor(UPDATED_VALOR)
            .nome(UPDATED_NOME)
            .type(UPDATED_TYPE);
        return lookupItem;
    }

    @BeforeEach
    public void initTest() {
        lookupItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLookupItem() throws Exception {
        int databaseSizeBeforeCreate = lookupItemRepository.findAll().size();

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);
        restLookupItemMockMvc.perform(post("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeCreate + 1);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testLookupItem.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLookupItem.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the LookupItem in Elasticsearch
        verify(mockLookupItemSearchRepository, times(1)).save(testLookupItem);
    }

    @Test
    @Transactional
    public void createLookupItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lookupItemRepository.findAll().size();

        // Create the LookupItem with an existing ID
        lookupItem.setId(1L);
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupItemMockMvc.perform(post("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the LookupItem in Elasticsearch
        verify(mockLookupItemSearchRepository, times(0)).save(lookupItem);
    }


    @Test
    @Transactional
    public void getAllLookupItems() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList
        restLookupItemMockMvc.perform(get("/api/lookup-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get the lookupItem
        restLookupItemMockMvc.perform(get("/api/lookup-items/{id}", lookupItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lookupItem.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLookupItem() throws Exception {
        // Get the lookupItem
        restLookupItemMockMvc.perform(get("/api/lookup-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();

        // Update the lookupItem
        LookupItem updatedLookupItem = lookupItemRepository.findById(lookupItem.getId()).get();
        // Disconnect from session so that the updates on updatedLookupItem are not directly saved in db
        em.detach(updatedLookupItem);
        updatedLookupItem
            .valor(UPDATED_VALOR)
            .nome(UPDATED_NOME)
            .type(UPDATED_TYPE);
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(updatedLookupItem);

        restLookupItemMockMvc.perform(put("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isOk());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testLookupItem.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLookupItem.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the LookupItem in Elasticsearch
        verify(mockLookupItemSearchRepository, times(1)).save(testLookupItem);
    }

    @Test
    @Transactional
    public void updateNonExistingLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupItemMockMvc.perform(put("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LookupItem in Elasticsearch
        verify(mockLookupItemSearchRepository, times(0)).save(lookupItem);
    }

    @Test
    @Transactional
    public void deleteLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeDelete = lookupItemRepository.findAll().size();

        // Delete the lookupItem
        restLookupItemMockMvc.perform(delete("/api/lookup-items/{id}", lookupItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LookupItem in Elasticsearch
        verify(mockLookupItemSearchRepository, times(1)).deleteById(lookupItem.getId());
    }

    @Test
    @Transactional
    public void searchLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);
        when(mockLookupItemSearchRepository.search(queryStringQuery("id:" + lookupItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lookupItem), PageRequest.of(0, 1), 1));
        // Search the lookupItem
        restLookupItemMockMvc.perform(get("/api/_search/lookup-items?query=id:" + lookupItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
}
