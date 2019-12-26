package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.MatrizCurricular;
import com.ravunana.ensino.repository.MatrizCurricularRepository;
import com.ravunana.ensino.repository.search.MatrizCurricularSearchRepository;
import com.ravunana.ensino.service.dto.MatrizCurricularDTO;
import com.ravunana.ensino.service.mapper.MatrizCurricularMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MatrizCurricular}.
 */
@Service
@Transactional
public class MatrizCurricularService {

    private final Logger log = LoggerFactory.getLogger(MatrizCurricularService.class);

    private final MatrizCurricularRepository matrizCurricularRepository;

    private final MatrizCurricularMapper matrizCurricularMapper;

    private final MatrizCurricularSearchRepository matrizCurricularSearchRepository;

    public MatrizCurricularService(MatrizCurricularRepository matrizCurricularRepository, MatrizCurricularMapper matrizCurricularMapper, MatrizCurricularSearchRepository matrizCurricularSearchRepository) {
        this.matrizCurricularRepository = matrizCurricularRepository;
        this.matrizCurricularMapper = matrizCurricularMapper;
        this.matrizCurricularSearchRepository = matrizCurricularSearchRepository;
    }

    /**
     * Save a matrizCurricular.
     *
     * @param matrizCurricularDTO the entity to save.
     * @return the persisted entity.
     */
    public MatrizCurricularDTO save(MatrizCurricularDTO matrizCurricularDTO) {
        log.debug("Request to save MatrizCurricular : {}", matrizCurricularDTO);
        MatrizCurricular matrizCurricular = matrizCurricularMapper.toEntity(matrizCurricularDTO);
        matrizCurricular = matrizCurricularRepository.save(matrizCurricular);
        MatrizCurricularDTO result = matrizCurricularMapper.toDto(matrizCurricular);
        matrizCurricularSearchRepository.save(matrizCurricular);
        return result;
    }

    /**
     * Get all the matrizCurriculars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatrizCurricularDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MatrizCurriculars");
        return matrizCurricularRepository.findAll(pageable)
            .map(matrizCurricularMapper::toDto);
    }


    /**
     * Get one matrizCurricular by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MatrizCurricularDTO> findOne(Long id) {
        log.debug("Request to get MatrizCurricular : {}", id);
        return matrizCurricularRepository.findById(id)
            .map(matrizCurricularMapper::toDto);
    }

    /**
     * Delete the matrizCurricular by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MatrizCurricular : {}", id);
        matrizCurricularRepository.deleteById(id);
        matrizCurricularSearchRepository.deleteById(id);
    }

    /**
     * Search for the matrizCurricular corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatrizCurricularDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MatrizCurriculars for query {}", query);
        return matrizCurricularSearchRepository.search(queryStringQuery(query), pageable)
            .map(matrizCurricularMapper::toDto);
    }
}
