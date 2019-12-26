package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Contrato;
import com.ravunana.ensino.repository.ContratoRepository;
import com.ravunana.ensino.repository.search.ContratoSearchRepository;
import com.ravunana.ensino.service.dto.ContratoDTO;
import com.ravunana.ensino.service.mapper.ContratoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Contrato}.
 */
@Service
@Transactional
public class ContratoService {

    private final Logger log = LoggerFactory.getLogger(ContratoService.class);

    private final ContratoRepository contratoRepository;

    private final ContratoMapper contratoMapper;

    private final ContratoSearchRepository contratoSearchRepository;

    public ContratoService(ContratoRepository contratoRepository, ContratoMapper contratoMapper, ContratoSearchRepository contratoSearchRepository) {
        this.contratoRepository = contratoRepository;
        this.contratoMapper = contratoMapper;
        this.contratoSearchRepository = contratoSearchRepository;
    }

    /**
     * Save a contrato.
     *
     * @param contratoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContratoDTO save(ContratoDTO contratoDTO) {
        log.debug("Request to save Contrato : {}", contratoDTO);
        Contrato contrato = contratoMapper.toEntity(contratoDTO);
        contrato = contratoRepository.save(contrato);
        ContratoDTO result = contratoMapper.toDto(contrato);
        contratoSearchRepository.save(contrato);
        return result;
    }

    /**
     * Get all the contratoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContratoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contratoes");
        return contratoRepository.findAll(pageable)
            .map(contratoMapper::toDto);
    }


    /**
     * Get one contrato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContratoDTO> findOne(Long id) {
        log.debug("Request to get Contrato : {}", id);
        return contratoRepository.findById(id)
            .map(contratoMapper::toDto);
    }

    /**
     * Delete the contrato by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contrato : {}", id);
        contratoRepository.deleteById(id);
        contratoSearchRepository.deleteById(id);
    }

    /**
     * Search for the contrato corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContratoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Contratoes for query {}", query);
        return contratoSearchRepository.search(queryStringQuery(query), pageable)
            .map(contratoMapper::toDto);
    }
}
