package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.UnidadeCurricular;
import com.ravunana.ensino.repository.UnidadeCurricularRepository;
import com.ravunana.ensino.repository.search.UnidadeCurricularSearchRepository;
import com.ravunana.ensino.service.dto.UnidadeCurricularDTO;
import com.ravunana.ensino.service.mapper.UnidadeCurricularMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UnidadeCurricular}.
 */
@Service
@Transactional
public class UnidadeCurricularService {

    private final Logger log = LoggerFactory.getLogger(UnidadeCurricularService.class);

    private final UnidadeCurricularRepository unidadeCurricularRepository;

    private final UnidadeCurricularMapper unidadeCurricularMapper;

    private final UnidadeCurricularSearchRepository unidadeCurricularSearchRepository;

    public UnidadeCurricularService(UnidadeCurricularRepository unidadeCurricularRepository, UnidadeCurricularMapper unidadeCurricularMapper, UnidadeCurricularSearchRepository unidadeCurricularSearchRepository) {
        this.unidadeCurricularRepository = unidadeCurricularRepository;
        this.unidadeCurricularMapper = unidadeCurricularMapper;
        this.unidadeCurricularSearchRepository = unidadeCurricularSearchRepository;
    }

    /**
     * Save a unidadeCurricular.
     *
     * @param unidadeCurricularDTO the entity to save.
     * @return the persisted entity.
     */
    public UnidadeCurricularDTO save(UnidadeCurricularDTO unidadeCurricularDTO) {
        log.debug("Request to save UnidadeCurricular : {}", unidadeCurricularDTO);
        UnidadeCurricular unidadeCurricular = unidadeCurricularMapper.toEntity(unidadeCurricularDTO);
        unidadeCurricular = unidadeCurricularRepository.save(unidadeCurricular);
        UnidadeCurricularDTO result = unidadeCurricularMapper.toDto(unidadeCurricular);
        unidadeCurricularSearchRepository.save(unidadeCurricular);
        return result;
    }

    /**
     * Get all the unidadeCurriculars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeCurricularDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeCurriculars");
        return unidadeCurricularRepository.findAll(pageable)
            .map(unidadeCurricularMapper::toDto);
    }


    /**
     * Get one unidadeCurricular by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeCurricularDTO> findOne(Long id) {
        log.debug("Request to get UnidadeCurricular : {}", id);
        return unidadeCurricularRepository.findById(id)
            .map(unidadeCurricularMapper::toDto);
    }

    /**
     * Delete the unidadeCurricular by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeCurricular : {}", id);
        unidadeCurricularRepository.deleteById(id);
        unidadeCurricularSearchRepository.deleteById(id);
    }

    /**
     * Search for the unidadeCurricular corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeCurricularDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeCurriculars for query {}", query);
        return unidadeCurricularSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadeCurricularMapper::toDto);
    }
}
