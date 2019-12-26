package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.EnsinoApp;
import com.ravunana.ensino.domain.ContactoInstituicaoEnsino;
import com.ravunana.ensino.domain.InstituicaoEnsino;
import com.ravunana.ensino.repository.ContactoInstituicaoEnsinoRepository;
import com.ravunana.ensino.repository.search.ContactoInstituicaoEnsinoSearchRepository;
import com.ravunana.ensino.service.ContactoInstituicaoEnsinoService;
import com.ravunana.ensino.service.dto.ContactoInstituicaoEnsinoDTO;
import com.ravunana.ensino.service.mapper.ContactoInstituicaoEnsinoMapper;
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

/**
 * Integration tests for the {@link ContactoInstituicaoEnsinoResource} REST controller.
 */
@SpringBootTest(classes = EnsinoApp.class)
public class ContactoInstituicaoEnsinoResourceIT {

    private static final String DEFAULT_TIPO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO = "BBBBBBBBBB";

    @Autowired
    private ContactoInstituicaoEnsinoRepository contactoInstituicaoEnsinoRepository;

    @Autowired
    private ContactoInstituicaoEnsinoMapper contactoInstituicaoEnsinoMapper;

    @Autowired
    private ContactoInstituicaoEnsinoService contactoInstituicaoEnsinoService;

    /**
     * This repository is mocked in the com.ravunana.ensino.repository.search test package.
     *
     * @see com.ravunana.ensino.repository.search.ContactoInstituicaoEnsinoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContactoInstituicaoEnsinoSearchRepository mockContactoInstituicaoEnsinoSearchRepository;

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

    private MockMvc restContactoInstituicaoEnsinoMockMvc;

    private ContactoInstituicaoEnsino contactoInstituicaoEnsino;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoInstituicaoEnsinoResource contactoInstituicaoEnsinoResource = new ContactoInstituicaoEnsinoResource(contactoInstituicaoEnsinoService);
        this.restContactoInstituicaoEnsinoMockMvc = MockMvcBuilders.standaloneSetup(contactoInstituicaoEnsinoResource)
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
    public static ContactoInstituicaoEnsino createEntity(EntityManager em) {
        ContactoInstituicaoEnsino contactoInstituicaoEnsino = new ContactoInstituicaoEnsino()
            .tipoContacto(DEFAULT_TIPO_CONTACTO)
            .descricao(DEFAULT_DESCRICAO)
            .contacto(DEFAULT_CONTACTO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        contactoInstituicaoEnsino.setInstituicaoEnsino(instituicaoEnsino);
        return contactoInstituicaoEnsino;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactoInstituicaoEnsino createUpdatedEntity(EntityManager em) {
        ContactoInstituicaoEnsino contactoInstituicaoEnsino = new ContactoInstituicaoEnsino()
            .tipoContacto(UPDATED_TIPO_CONTACTO)
            .descricao(UPDATED_DESCRICAO)
            .contacto(UPDATED_CONTACTO);
        // Add required entity
        InstituicaoEnsino instituicaoEnsino;
        if (TestUtil.findAll(em, InstituicaoEnsino.class).isEmpty()) {
            instituicaoEnsino = InstituicaoEnsinoResourceIT.createUpdatedEntity(em);
            em.persist(instituicaoEnsino);
            em.flush();
        } else {
            instituicaoEnsino = TestUtil.findAll(em, InstituicaoEnsino.class).get(0);
        }
        contactoInstituicaoEnsino.setInstituicaoEnsino(instituicaoEnsino);
        return contactoInstituicaoEnsino;
    }

    @BeforeEach
    public void initTest() {
        contactoInstituicaoEnsino = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactoInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeCreate = contactoInstituicaoEnsinoRepository.findAll().size();

        // Create the ContactoInstituicaoEnsino
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoMapper.toDto(contactoInstituicaoEnsino);
        restContactoInstituicaoEnsinoMockMvc.perform(post("/api/contacto-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoInstituicaoEnsinoDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactoInstituicaoEnsino in the database
        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoInstituicaoEnsino testContactoInstituicaoEnsino = contactoInstituicaoEnsinoList.get(contactoInstituicaoEnsinoList.size() - 1);
        assertThat(testContactoInstituicaoEnsino.getTipoContacto()).isEqualTo(DEFAULT_TIPO_CONTACTO);
        assertThat(testContactoInstituicaoEnsino.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContactoInstituicaoEnsino.getContacto()).isEqualTo(DEFAULT_CONTACTO);

        // Validate the ContactoInstituicaoEnsino in Elasticsearch
        verify(mockContactoInstituicaoEnsinoSearchRepository, times(1)).save(testContactoInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void createContactoInstituicaoEnsinoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoInstituicaoEnsinoRepository.findAll().size();

        // Create the ContactoInstituicaoEnsino with an existing ID
        contactoInstituicaoEnsino.setId(1L);
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoMapper.toDto(contactoInstituicaoEnsino);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoInstituicaoEnsinoMockMvc.perform(post("/api/contacto-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoInstituicaoEnsino in the database
        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContactoInstituicaoEnsino in Elasticsearch
        verify(mockContactoInstituicaoEnsinoSearchRepository, times(0)).save(contactoInstituicaoEnsino);
    }


    @Test
    @Transactional
    public void checkTipoContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        contactoInstituicaoEnsino.setTipoContacto(null);

        // Create the ContactoInstituicaoEnsino, which fails.
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoMapper.toDto(contactoInstituicaoEnsino);

        restContactoInstituicaoEnsinoMockMvc.perform(post("/api/contacto-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        contactoInstituicaoEnsino.setDescricao(null);

        // Create the ContactoInstituicaoEnsino, which fails.
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoMapper.toDto(contactoInstituicaoEnsino);

        restContactoInstituicaoEnsinoMockMvc.perform(post("/api/contacto-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoInstituicaoEnsinoRepository.findAll().size();
        // set the field null
        contactoInstituicaoEnsino.setContacto(null);

        // Create the ContactoInstituicaoEnsino, which fails.
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoMapper.toDto(contactoInstituicaoEnsino);

        restContactoInstituicaoEnsinoMockMvc.perform(post("/api/contacto-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactoInstituicaoEnsinos() throws Exception {
        // Initialize the database
        contactoInstituicaoEnsinoRepository.saveAndFlush(contactoInstituicaoEnsino);

        // Get all the contactoInstituicaoEnsinoList
        restContactoInstituicaoEnsinoMockMvc.perform(get("/api/contacto-instituicao-ensinos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoInstituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoContacto").value(hasItem(DEFAULT_TIPO_CONTACTO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)));
    }
    
    @Test
    @Transactional
    public void getContactoInstituicaoEnsino() throws Exception {
        // Initialize the database
        contactoInstituicaoEnsinoRepository.saveAndFlush(contactoInstituicaoEnsino);

        // Get the contactoInstituicaoEnsino
        restContactoInstituicaoEnsinoMockMvc.perform(get("/api/contacto-instituicao-ensinos/{id}", contactoInstituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactoInstituicaoEnsino.getId().intValue()))
            .andExpect(jsonPath("$.tipoContacto").value(DEFAULT_TIPO_CONTACTO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO));
    }

    @Test
    @Transactional
    public void getNonExistingContactoInstituicaoEnsino() throws Exception {
        // Get the contactoInstituicaoEnsino
        restContactoInstituicaoEnsinoMockMvc.perform(get("/api/contacto-instituicao-ensinos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactoInstituicaoEnsino() throws Exception {
        // Initialize the database
        contactoInstituicaoEnsinoRepository.saveAndFlush(contactoInstituicaoEnsino);

        int databaseSizeBeforeUpdate = contactoInstituicaoEnsinoRepository.findAll().size();

        // Update the contactoInstituicaoEnsino
        ContactoInstituicaoEnsino updatedContactoInstituicaoEnsino = contactoInstituicaoEnsinoRepository.findById(contactoInstituicaoEnsino.getId()).get();
        // Disconnect from session so that the updates on updatedContactoInstituicaoEnsino are not directly saved in db
        em.detach(updatedContactoInstituicaoEnsino);
        updatedContactoInstituicaoEnsino
            .tipoContacto(UPDATED_TIPO_CONTACTO)
            .descricao(UPDATED_DESCRICAO)
            .contacto(UPDATED_CONTACTO);
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoMapper.toDto(updatedContactoInstituicaoEnsino);

        restContactoInstituicaoEnsinoMockMvc.perform(put("/api/contacto-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoInstituicaoEnsinoDTO)))
            .andExpect(status().isOk());

        // Validate the ContactoInstituicaoEnsino in the database
        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);
        ContactoInstituicaoEnsino testContactoInstituicaoEnsino = contactoInstituicaoEnsinoList.get(contactoInstituicaoEnsinoList.size() - 1);
        assertThat(testContactoInstituicaoEnsino.getTipoContacto()).isEqualTo(UPDATED_TIPO_CONTACTO);
        assertThat(testContactoInstituicaoEnsino.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContactoInstituicaoEnsino.getContacto()).isEqualTo(UPDATED_CONTACTO);

        // Validate the ContactoInstituicaoEnsino in Elasticsearch
        verify(mockContactoInstituicaoEnsinoSearchRepository, times(1)).save(testContactoInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void updateNonExistingContactoInstituicaoEnsino() throws Exception {
        int databaseSizeBeforeUpdate = contactoInstituicaoEnsinoRepository.findAll().size();

        // Create the ContactoInstituicaoEnsino
        ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoMapper.toDto(contactoInstituicaoEnsino);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoInstituicaoEnsinoMockMvc.perform(put("/api/contacto-instituicao-ensinos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoInstituicaoEnsinoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoInstituicaoEnsino in the database
        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContactoInstituicaoEnsino in Elasticsearch
        verify(mockContactoInstituicaoEnsinoSearchRepository, times(0)).save(contactoInstituicaoEnsino);
    }

    @Test
    @Transactional
    public void deleteContactoInstituicaoEnsino() throws Exception {
        // Initialize the database
        contactoInstituicaoEnsinoRepository.saveAndFlush(contactoInstituicaoEnsino);

        int databaseSizeBeforeDelete = contactoInstituicaoEnsinoRepository.findAll().size();

        // Delete the contactoInstituicaoEnsino
        restContactoInstituicaoEnsinoMockMvc.perform(delete("/api/contacto-instituicao-ensinos/{id}", contactoInstituicaoEnsino.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactoInstituicaoEnsino> contactoInstituicaoEnsinoList = contactoInstituicaoEnsinoRepository.findAll();
        assertThat(contactoInstituicaoEnsinoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContactoInstituicaoEnsino in Elasticsearch
        verify(mockContactoInstituicaoEnsinoSearchRepository, times(1)).deleteById(contactoInstituicaoEnsino.getId());
    }

    @Test
    @Transactional
    public void searchContactoInstituicaoEnsino() throws Exception {
        // Initialize the database
        contactoInstituicaoEnsinoRepository.saveAndFlush(contactoInstituicaoEnsino);
        when(mockContactoInstituicaoEnsinoSearchRepository.search(queryStringQuery("id:" + contactoInstituicaoEnsino.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contactoInstituicaoEnsino), PageRequest.of(0, 1), 1));
        // Search the contactoInstituicaoEnsino
        restContactoInstituicaoEnsinoMockMvc.perform(get("/api/_search/contacto-instituicao-ensinos?query=id:" + contactoInstituicaoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoInstituicaoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoContacto").value(hasItem(DEFAULT_TIPO_CONTACTO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)));
    }
}
