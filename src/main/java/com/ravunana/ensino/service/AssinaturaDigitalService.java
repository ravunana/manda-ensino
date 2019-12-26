package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.AssinaturaDigital;
import com.ravunana.ensino.repository.AssinaturaDigitalRepository;
import com.ravunana.ensino.repository.search.AssinaturaDigitalSearchRepository;
import com.ravunana.ensino.service.dto.AssinaturaDigitalDTO;
import com.ravunana.ensino.service.mapper.AssinaturaDigitalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AssinaturaDigital}.
 */
@Service
@Transactional
public class AssinaturaDigitalService {

    private final Logger log = LoggerFactory.getLogger(AssinaturaDigitalService.class);

    private final AssinaturaDigitalRepository assinaturaDigitalRepository;

    private final AssinaturaDigitalMapper assinaturaDigitalMapper;

    private final AssinaturaDigitalSearchRepository assinaturaDigitalSearchRepository;

    public AssinaturaDigitalService(AssinaturaDigitalRepository assinaturaDigitalRepository, AssinaturaDigitalMapper assinaturaDigitalMapper, AssinaturaDigitalSearchRepository assinaturaDigitalSearchRepository) {
        this.assinaturaDigitalRepository = assinaturaDigitalRepository;
        this.assinaturaDigitalMapper = assinaturaDigitalMapper;
        this.assinaturaDigitalSearchRepository = assinaturaDigitalSearchRepository;
    }

    /**
     * Save a assinaturaDigital.
     *
     * @param assinaturaDigitalDTO the entity to save.
     * @return the persisted entity.
     */
    public AssinaturaDigitalDTO save(AssinaturaDigitalDTO assinaturaDigitalDTO) {
        log.debug("Request to save AssinaturaDigital : {}", assinaturaDigitalDTO);
        AssinaturaDigital assinaturaDigital = assinaturaDigitalMapper.toEntity(assinaturaDigitalDTO);
        assinaturaDigital = assinaturaDigitalRepository.save(assinaturaDigital);
        AssinaturaDigitalDTO result = assinaturaDigitalMapper.toDto(assinaturaDigital);
        assinaturaDigitalSearchRepository.save(assinaturaDigital);
        return result;
    }

    /**
     * Get all the assinaturaDigitals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssinaturaDigitalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssinaturaDigitals");
        return assinaturaDigitalRepository.findAll(pageable)
            .map(assinaturaDigitalMapper::toDto);
    }


    /**
     * Get one assinaturaDigital by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssinaturaDigitalDTO> findOne(Long id) {
        log.debug("Request to get AssinaturaDigital : {}", id);
        return assinaturaDigitalRepository.findById(id)
            .map(assinaturaDigitalMapper::toDto);
    }

    /**
     * Delete the assinaturaDigital by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssinaturaDigital : {}", id);
        assinaturaDigitalRepository.deleteById(id);
        assinaturaDigitalSearchRepository.deleteById(id);
    }

    /**
     * Search for the assinaturaDigital corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssinaturaDigitalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AssinaturaDigitals for query {}", query);
        return assinaturaDigitalSearchRepository.search(queryStringQuery(query), pageable)
            .map(assinaturaDigitalMapper::toDto);
    }
}
