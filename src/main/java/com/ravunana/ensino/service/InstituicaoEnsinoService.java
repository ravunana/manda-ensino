package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.InstituicaoEnsino;
import com.ravunana.ensino.repository.InstituicaoEnsinoRepository;
import com.ravunana.ensino.repository.search.InstituicaoEnsinoSearchRepository;
import com.ravunana.ensino.service.dto.InstituicaoEnsinoDTO;
import com.ravunana.ensino.service.mapper.InstituicaoEnsinoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link InstituicaoEnsino}.
 */
@Service
@Transactional
public class InstituicaoEnsinoService {

    private final Logger log = LoggerFactory.getLogger(InstituicaoEnsinoService.class);

    private final InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    private final InstituicaoEnsinoMapper instituicaoEnsinoMapper;

    private final InstituicaoEnsinoSearchRepository instituicaoEnsinoSearchRepository;

    public InstituicaoEnsinoService(InstituicaoEnsinoRepository instituicaoEnsinoRepository, InstituicaoEnsinoMapper instituicaoEnsinoMapper, InstituicaoEnsinoSearchRepository instituicaoEnsinoSearchRepository) {
        this.instituicaoEnsinoRepository = instituicaoEnsinoRepository;
        this.instituicaoEnsinoMapper = instituicaoEnsinoMapper;
        this.instituicaoEnsinoSearchRepository = instituicaoEnsinoSearchRepository;
    }

    /**
     * Save a instituicaoEnsino.
     *
     * @param instituicaoEnsinoDTO the entity to save.
     * @return the persisted entity.
     */
    public InstituicaoEnsinoDTO save(InstituicaoEnsinoDTO instituicaoEnsinoDTO) {
        log.debug("Request to save InstituicaoEnsino : {}", instituicaoEnsinoDTO);
        InstituicaoEnsino instituicaoEnsino = instituicaoEnsinoMapper.toEntity(instituicaoEnsinoDTO);
        instituicaoEnsino = instituicaoEnsinoRepository.save(instituicaoEnsino);
        InstituicaoEnsinoDTO result = instituicaoEnsinoMapper.toDto(instituicaoEnsino);
        instituicaoEnsinoSearchRepository.save(instituicaoEnsino);
        return result;
    }

    /**
     * Get all the instituicaoEnsinos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InstituicaoEnsinoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InstituicaoEnsinos");
        return instituicaoEnsinoRepository.findAll(pageable)
            .map(instituicaoEnsinoMapper::toDto);
    }


    /**
     * Get one instituicaoEnsino by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InstituicaoEnsinoDTO> findOne(Long id) {
        log.debug("Request to get InstituicaoEnsino : {}", id);
        return instituicaoEnsinoRepository.findById(id)
            .map(instituicaoEnsinoMapper::toDto);
    }

    /**
     * Delete the instituicaoEnsino by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InstituicaoEnsino : {}", id);
        instituicaoEnsinoRepository.deleteById(id);
        instituicaoEnsinoSearchRepository.deleteById(id);
    }

    /**
     * Search for the instituicaoEnsino corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InstituicaoEnsinoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InstituicaoEnsinos for query {}", query);
        return instituicaoEnsinoSearchRepository.search(queryStringQuery(query), pageable)
            .map(instituicaoEnsinoMapper::toDto);
    }
}
