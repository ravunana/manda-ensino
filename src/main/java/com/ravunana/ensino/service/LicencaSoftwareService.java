package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.LicencaSoftware;
import com.ravunana.ensino.repository.LicencaSoftwareRepository;
import com.ravunana.ensino.repository.search.LicencaSoftwareSearchRepository;
import com.ravunana.ensino.service.dto.LicencaSoftwareDTO;
import com.ravunana.ensino.service.mapper.LicencaSoftwareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LicencaSoftware}.
 */
@Service
@Transactional
public class LicencaSoftwareService {

    private final Logger log = LoggerFactory.getLogger(LicencaSoftwareService.class);

    private final LicencaSoftwareRepository licencaSoftwareRepository;

    private final LicencaSoftwareMapper licencaSoftwareMapper;

    private final LicencaSoftwareSearchRepository licencaSoftwareSearchRepository;

    public LicencaSoftwareService(LicencaSoftwareRepository licencaSoftwareRepository, LicencaSoftwareMapper licencaSoftwareMapper, LicencaSoftwareSearchRepository licencaSoftwareSearchRepository) {
        this.licencaSoftwareRepository = licencaSoftwareRepository;
        this.licencaSoftwareMapper = licencaSoftwareMapper;
        this.licencaSoftwareSearchRepository = licencaSoftwareSearchRepository;
    }

    /**
     * Save a licencaSoftware.
     *
     * @param licencaSoftwareDTO the entity to save.
     * @return the persisted entity.
     */
    public LicencaSoftwareDTO save(LicencaSoftwareDTO licencaSoftwareDTO) {
        log.debug("Request to save LicencaSoftware : {}", licencaSoftwareDTO);
        LicencaSoftware licencaSoftware = licencaSoftwareMapper.toEntity(licencaSoftwareDTO);
        licencaSoftware = licencaSoftwareRepository.save(licencaSoftware);
        LicencaSoftwareDTO result = licencaSoftwareMapper.toDto(licencaSoftware);
        licencaSoftwareSearchRepository.save(licencaSoftware);
        return result;
    }

    /**
     * Get all the licencaSoftwares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LicencaSoftwareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LicencaSoftwares");
        return licencaSoftwareRepository.findAll(pageable)
            .map(licencaSoftwareMapper::toDto);
    }


    /**
     * Get one licencaSoftware by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LicencaSoftwareDTO> findOne(Long id) {
        log.debug("Request to get LicencaSoftware : {}", id);
        return licencaSoftwareRepository.findById(id)
            .map(licencaSoftwareMapper::toDto);
    }

    /**
     * Delete the licencaSoftware by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LicencaSoftware : {}", id);
        licencaSoftwareRepository.deleteById(id);
        licencaSoftwareSearchRepository.deleteById(id);
    }

    /**
     * Search for the licencaSoftware corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LicencaSoftwareDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LicencaSoftwares for query {}", query);
        return licencaSoftwareSearchRepository.search(queryStringQuery(query), pageable)
            .map(licencaSoftwareMapper::toDto);
    }
}
