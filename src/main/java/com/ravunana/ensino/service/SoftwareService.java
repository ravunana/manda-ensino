package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Software;
import com.ravunana.ensino.repository.SoftwareRepository;
import com.ravunana.ensino.repository.search.SoftwareSearchRepository;
import com.ravunana.ensino.service.dto.SoftwareDTO;
import com.ravunana.ensino.service.mapper.SoftwareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Software}.
 */
@Service
@Transactional
public class SoftwareService {

    private final Logger log = LoggerFactory.getLogger(SoftwareService.class);

    private final SoftwareRepository softwareRepository;

    private final SoftwareMapper softwareMapper;

    private final SoftwareSearchRepository softwareSearchRepository;

    public SoftwareService(SoftwareRepository softwareRepository, SoftwareMapper softwareMapper, SoftwareSearchRepository softwareSearchRepository) {
        this.softwareRepository = softwareRepository;
        this.softwareMapper = softwareMapper;
        this.softwareSearchRepository = softwareSearchRepository;
    }

    /**
     * Save a software.
     *
     * @param softwareDTO the entity to save.
     * @return the persisted entity.
     */
    public SoftwareDTO save(SoftwareDTO softwareDTO) {
        log.debug("Request to save Software : {}", softwareDTO);
        Software software = softwareMapper.toEntity(softwareDTO);
        software = softwareRepository.save(software);
        SoftwareDTO result = softwareMapper.toDto(software);
        softwareSearchRepository.save(software);
        return result;
    }

    /**
     * Get all the softwares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Softwares");
        return softwareRepository.findAll(pageable)
            .map(softwareMapper::toDto);
    }


    /**
     * Get one software by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SoftwareDTO> findOne(Long id) {
        log.debug("Request to get Software : {}", id);
        return softwareRepository.findById(id)
            .map(softwareMapper::toDto);
    }

    /**
     * Delete the software by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Software : {}", id);
        softwareRepository.deleteById(id);
        softwareSearchRepository.deleteById(id);
    }

    /**
     * Search for the software corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Softwares for query {}", query);
        return softwareSearchRepository.search(queryStringQuery(query), pageable)
            .map(softwareMapper::toDto);
    }
}
