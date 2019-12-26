package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.CoordenadaBancaria;
import com.ravunana.ensino.repository.CoordenadaBancariaRepository;
import com.ravunana.ensino.repository.search.CoordenadaBancariaSearchRepository;
import com.ravunana.ensino.service.dto.CoordenadaBancariaDTO;
import com.ravunana.ensino.service.mapper.CoordenadaBancariaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CoordenadaBancaria}.
 */
@Service
@Transactional
public class CoordenadaBancariaService {

    private final Logger log = LoggerFactory.getLogger(CoordenadaBancariaService.class);

    private final CoordenadaBancariaRepository coordenadaBancariaRepository;

    private final CoordenadaBancariaMapper coordenadaBancariaMapper;

    private final CoordenadaBancariaSearchRepository coordenadaBancariaSearchRepository;

    public CoordenadaBancariaService(CoordenadaBancariaRepository coordenadaBancariaRepository, CoordenadaBancariaMapper coordenadaBancariaMapper, CoordenadaBancariaSearchRepository coordenadaBancariaSearchRepository) {
        this.coordenadaBancariaRepository = coordenadaBancariaRepository;
        this.coordenadaBancariaMapper = coordenadaBancariaMapper;
        this.coordenadaBancariaSearchRepository = coordenadaBancariaSearchRepository;
    }

    /**
     * Save a coordenadaBancaria.
     *
     * @param coordenadaBancariaDTO the entity to save.
     * @return the persisted entity.
     */
    public CoordenadaBancariaDTO save(CoordenadaBancariaDTO coordenadaBancariaDTO) {
        log.debug("Request to save CoordenadaBancaria : {}", coordenadaBancariaDTO);
        CoordenadaBancaria coordenadaBancaria = coordenadaBancariaMapper.toEntity(coordenadaBancariaDTO);
        coordenadaBancaria = coordenadaBancariaRepository.save(coordenadaBancaria);
        CoordenadaBancariaDTO result = coordenadaBancariaMapper.toDto(coordenadaBancaria);
        coordenadaBancariaSearchRepository.save(coordenadaBancaria);
        return result;
    }

    /**
     * Get all the coordenadaBancarias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CoordenadaBancariaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoordenadaBancarias");
        return coordenadaBancariaRepository.findAll(pageable)
            .map(coordenadaBancariaMapper::toDto);
    }

    /**
     * Get all the coordenadaBancarias with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CoordenadaBancariaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return coordenadaBancariaRepository.findAllWithEagerRelationships(pageable).map(coordenadaBancariaMapper::toDto);
    }
    

    /**
     * Get one coordenadaBancaria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CoordenadaBancariaDTO> findOne(Long id) {
        log.debug("Request to get CoordenadaBancaria : {}", id);
        return coordenadaBancariaRepository.findOneWithEagerRelationships(id)
            .map(coordenadaBancariaMapper::toDto);
    }

    /**
     * Delete the coordenadaBancaria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CoordenadaBancaria : {}", id);
        coordenadaBancariaRepository.deleteById(id);
        coordenadaBancariaSearchRepository.deleteById(id);
    }

    /**
     * Search for the coordenadaBancaria corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CoordenadaBancariaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CoordenadaBancarias for query {}", query);
        return coordenadaBancariaSearchRepository.search(queryStringQuery(query), pageable)
            .map(coordenadaBancariaMapper::toDto);
    }
}
