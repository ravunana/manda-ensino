package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.ContactoInstituicaoEnsino;
import com.ravunana.ensino.repository.ContactoInstituicaoEnsinoRepository;
import com.ravunana.ensino.repository.search.ContactoInstituicaoEnsinoSearchRepository;
import com.ravunana.ensino.service.dto.ContactoInstituicaoEnsinoDTO;
import com.ravunana.ensino.service.mapper.ContactoInstituicaoEnsinoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ContactoInstituicaoEnsino}.
 */
@Service
@Transactional
public class ContactoInstituicaoEnsinoService {

    private final Logger log = LoggerFactory.getLogger(ContactoInstituicaoEnsinoService.class);

    private final ContactoInstituicaoEnsinoRepository contactoInstituicaoEnsinoRepository;

    private final ContactoInstituicaoEnsinoMapper contactoInstituicaoEnsinoMapper;

    private final ContactoInstituicaoEnsinoSearchRepository contactoInstituicaoEnsinoSearchRepository;

    public ContactoInstituicaoEnsinoService(ContactoInstituicaoEnsinoRepository contactoInstituicaoEnsinoRepository, ContactoInstituicaoEnsinoMapper contactoInstituicaoEnsinoMapper, ContactoInstituicaoEnsinoSearchRepository contactoInstituicaoEnsinoSearchRepository) {
        this.contactoInstituicaoEnsinoRepository = contactoInstituicaoEnsinoRepository;
        this.contactoInstituicaoEnsinoMapper = contactoInstituicaoEnsinoMapper;
        this.contactoInstituicaoEnsinoSearchRepository = contactoInstituicaoEnsinoSearchRepository;
    }

    /**
     * Save a contactoInstituicaoEnsino.
     *
     * @param contactoInstituicaoEnsinoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactoInstituicaoEnsinoDTO save(ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO) {
        log.debug("Request to save ContactoInstituicaoEnsino : {}", contactoInstituicaoEnsinoDTO);
        ContactoInstituicaoEnsino contactoInstituicaoEnsino = contactoInstituicaoEnsinoMapper.toEntity(contactoInstituicaoEnsinoDTO);
        contactoInstituicaoEnsino = contactoInstituicaoEnsinoRepository.save(contactoInstituicaoEnsino);
        ContactoInstituicaoEnsinoDTO result = contactoInstituicaoEnsinoMapper.toDto(contactoInstituicaoEnsino);
        contactoInstituicaoEnsinoSearchRepository.save(contactoInstituicaoEnsino);
        return result;
    }

    /**
     * Get all the contactoInstituicaoEnsinos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactoInstituicaoEnsinoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactoInstituicaoEnsinos");
        return contactoInstituicaoEnsinoRepository.findAll(pageable)
            .map(contactoInstituicaoEnsinoMapper::toDto);
    }


    /**
     * Get one contactoInstituicaoEnsino by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactoInstituicaoEnsinoDTO> findOne(Long id) {
        log.debug("Request to get ContactoInstituicaoEnsino : {}", id);
        return contactoInstituicaoEnsinoRepository.findById(id)
            .map(contactoInstituicaoEnsinoMapper::toDto);
    }

    /**
     * Delete the contactoInstituicaoEnsino by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactoInstituicaoEnsino : {}", id);
        contactoInstituicaoEnsinoRepository.deleteById(id);
        contactoInstituicaoEnsinoSearchRepository.deleteById(id);
    }

    /**
     * Search for the contactoInstituicaoEnsino corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactoInstituicaoEnsinoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContactoInstituicaoEnsinos for query {}", query);
        return contactoInstituicaoEnsinoSearchRepository.search(queryStringQuery(query), pageable)
            .map(contactoInstituicaoEnsinoMapper::toDto);
    }
}
