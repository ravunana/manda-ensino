package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Lookup;
import com.ravunana.ensino.repository.LookupRepository;
import com.ravunana.ensino.repository.search.LookupSearchRepository;
import com.ravunana.ensino.service.dto.LookupDTO;
import com.ravunana.ensino.service.mapper.LookupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Lookup}.
 */
@Service
@Transactional
public class LookupService {

    private final Logger log = LoggerFactory.getLogger(LookupService.class);

    private final LookupRepository lookupRepository;

    private final LookupMapper lookupMapper;

    private final LookupSearchRepository lookupSearchRepository;

    public LookupService(LookupRepository lookupRepository, LookupMapper lookupMapper, LookupSearchRepository lookupSearchRepository) {
        this.lookupRepository = lookupRepository;
        this.lookupMapper = lookupMapper;
        this.lookupSearchRepository = lookupSearchRepository;
    }

    /**
     * Save a lookup.
     *
     * @param lookupDTO the entity to save.
     * @return the persisted entity.
     */
    public LookupDTO save(LookupDTO lookupDTO) {
        log.debug("Request to save Lookup : {}", lookupDTO);
        Lookup lookup = lookupMapper.toEntity(lookupDTO);
        lookup = lookupRepository.save(lookup);
        LookupDTO result = lookupMapper.toDto(lookup);
        lookupSearchRepository.save(lookup);
        return result;
    }

    /**
     * Get all the lookups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LookupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lookups");
        return lookupRepository.findAll(pageable)
            .map(lookupMapper::toDto);
    }


    /**
     * Get one lookup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LookupDTO> findOne(Long id) {
        log.debug("Request to get Lookup : {}", id);
        return lookupRepository.findById(id)
            .map(lookupMapper::toDto);
    }

    /**
     * Delete the lookup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lookup : {}", id);
        lookupRepository.deleteById(id);
        lookupSearchRepository.deleteById(id);
    }

    /**
     * Search for the lookup corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LookupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lookups for query {}", query);
        return lookupSearchRepository.search(queryStringQuery(query), pageable)
            .map(lookupMapper::toDto);
    }
}
