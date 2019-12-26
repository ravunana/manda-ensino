package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.ContactoPessoa;
import com.ravunana.ensino.repository.ContactoPessoaRepository;
import com.ravunana.ensino.repository.search.ContactoPessoaSearchRepository;
import com.ravunana.ensino.service.dto.ContactoPessoaDTO;
import com.ravunana.ensino.service.mapper.ContactoPessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ContactoPessoa}.
 */
@Service
@Transactional
public class ContactoPessoaService {

    private final Logger log = LoggerFactory.getLogger(ContactoPessoaService.class);

    private final ContactoPessoaRepository contactoPessoaRepository;

    private final ContactoPessoaMapper contactoPessoaMapper;

    private final ContactoPessoaSearchRepository contactoPessoaSearchRepository;

    private List<ContactoPessoaDTO> contactos = new ArrayList<>();

    public ContactoPessoaService(ContactoPessoaRepository contactoPessoaRepository, ContactoPessoaMapper contactoPessoaMapper, ContactoPessoaSearchRepository contactoPessoaSearchRepository) {
        this.contactoPessoaRepository = contactoPessoaRepository;
        this.contactoPessoaMapper = contactoPessoaMapper;
        this.contactoPessoaSearchRepository = contactoPessoaSearchRepository;
    }

    /**
     * Save a contactoPessoa.
     *
     * @param contactoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactoPessoaDTO save(ContactoPessoaDTO contactoPessoaDTO) {
        log.debug("Request to save ContactoPessoa : {}", contactoPessoaDTO);
        ContactoPessoa contactoPessoa = contactoPessoaMapper.toEntity(contactoPessoaDTO);
        contactoPessoa = contactoPessoaRepository.save(contactoPessoa);
        ContactoPessoaDTO result = contactoPessoaMapper.toDto(contactoPessoa);
        contactoPessoaSearchRepository.save(contactoPessoa);
        return result;
    }

    /**
     * Get all the contactoPessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactoPessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactoPessoas");
        return contactoPessoaRepository.findAll(pageable)
            .map(contactoPessoaMapper::toDto);
    }


    /**
     * Get one contactoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactoPessoaDTO> findOne(Long id) {
        log.debug("Request to get ContactoPessoa : {}", id);
        return contactoPessoaRepository.findById(id)
            .map(contactoPessoaMapper::toDto);
    }

    /**
     * Delete the contactoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactoPessoa : {}", id);
        contactoPessoaRepository.deleteById(id);
        contactoPessoaSearchRepository.deleteById(id);
    }

    /**
     * Search for the contactoPessoa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactoPessoaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContactoPessoas for query {}", query);
        return contactoPessoaSearchRepository.search(queryStringQuery(query), pageable)
            .map(contactoPessoaMapper::toDto);
    }

    // Array contacto

    public ContactoPessoaDTO addContacto(ContactoPessoaDTO contacto) {

        Boolean result = contactos.add(contacto);
        if ( result )
            return contacto;
        else
            return new ContactoPessoaDTO();
    }

    public ContactoPessoaDTO deleteContacto(int index) {
        return contactos.remove(index);
    }

    public List<ContactoPessoaDTO> listContactos() {
        return contactos;
    }

    public void limparContactos() {
        contactos.clear();
    }
}
